using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using Utilities;

namespace JokeRequester
{
    class JokeRequester
    {
        private static readonly string QueueRequestName = "getsomejoke";

        static void Main(string[] args)
        {
            string responseQueue = "someresponseQueue" + RandomInt();
            Console.WriteLine("Let's get a good joke..");
            Console.WriteLine(GetJoke(responseQueue).Result);
            Console.WriteLine("HHAHAHAHAHAHHA\n");
            for (int i = 0; i < 10; i++)
            {
                Console.WriteLine("Let's get another good joke..");
                Console.WriteLine(GetJoke(responseQueue).Result);
                Console.WriteLine("HHAHAHAHAHAHHA\n");
            }
        }

        private static async Task<string> GetJoke(string responseQueue)
        {
            string joke = null;
            string correlationId = "somerandomJokeId" + RandomInt();
            RequestJoke(correlationId, responseQueue);
            joke = await RetrieveJoke(correlationId, responseQueue);

            return joke;
        }

        private static void RequestJoke(string correlationId, string responseQueue)
        {
            //string responseQueue;
            var factory = new ConnectionFactory { HostName = "localhost" };
            using (var connection = factory.CreateConnection())
            using (var channel = connection.CreateModel())
            {
                //responseQueue = "someresponseQueue" + RandomInt(); // channel.QueueDeclare().QueueName;
                channel.QueueDeclare(queue: QueueRequestName, durable: false, exclusive: false, autoDelete: false, arguments: null);
                channel.QueueDeclare(queue: responseQueue, durable: false, exclusive: false, autoDelete: true, arguments: null);

                RequestMessage reqMessage = new RequestMessage
                {
                    ResponseQueue = responseQueue,
                    CorrelationId = correlationId,
                    Type = RequestType.Joke
                };
                var body = Encoding.UTF8.GetBytes(reqMessage.XmlSerializeToString());

                channel.BasicPublish(exchange: "", routingKey: QueueRequestName, mandatory: false, basicProperties: null, body: body);
            }
            //return responseQueue;
        }

        private static async Task<string> RetrieveJoke(string correlationId, string responseQueue)
        {
            string returnJoke;

            var thread = RetrieveJokeThread(correlationId, responseQueue);
            returnJoke = await thread;

            return returnJoke;
        }

        private static Task<string> RetrieveJokeThread(string correlationId, string responseQueue)
        {
            return Task.Run<string>(() =>
            {
                string returnJoke = null;
                var factory = new ConnectionFactory { HostName = "localhost" };
                using (var connection = factory.CreateConnection())
                using (var channel = connection.CreateModel())
                {
                    var consumer = new EventingBasicConsumer(channel);
                    consumer.Received += (model, ea) =>
                    {
                        var body = ea.Body;
                        var messageString = Encoding.UTF8.GetString(body);
                        MessageJoke joke = messageString.XmlDeserializeFromString<MessageJoke>();
                        //Thread.Sleep(TimeSpan.FromSeconds(15));
                        if (joke.CorrelationId == correlationId)
                        {
                            returnJoke = joke.Joke;
                            channel.BasicAck(ea.DeliveryTag, false); //TODO: BAD!! Message is being confirmed before all processing with it is done!
                            //connection.Close();
                        }
                        else
                        {
                            channel.BasicReject(ea.DeliveryTag, true);
                            //Thread.Sleep(TimeSpan.FromSeconds(30));
                        }
                    };
                    channel.BasicConsume(queue: responseQueue, autoAck: false, consumer: consumer);
                    
                    while (string.IsNullOrEmpty(returnJoke))
                    {
                        Thread.Sleep(50);
                    }
                    return returnJoke;
                }
            });
        }

        private static int RandomInt()
        {
            Random random = new Random(DateTime.Now.Millisecond);
            return random.Next();
        }

    }
}
