using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using Utilities;

namespace JokeService
{
    class Program
    {
        private static readonly string QueueRequestName = "getsomejoke";

        static void Main(string[] args)
        {
            var factory = new ConnectionFactory() { HostName = "localhost" };
            using (var connection = factory.CreateConnection())
            using (var channel = connection.CreateModel())
            {
                Console.WriteLine("Processing..");
                //channel.ExchangeDeclare( exchange: "jokesExhange", type: "fanout", durable: false, autoDelete: false, arguments: null);
                channel.QueueDeclare(queue: QueueRequestName, durable: false, exclusive: false, autoDelete: false, arguments: null);
                var consumer = new EventingBasicConsumer(channel);
                consumer.Received += (model, ea) =>
                {
                    var requestbody = ea.Body;
                    var messageString = Encoding.UTF8.GetString(requestbody);
                    RequestMessage request = messageString.XmlDeserializeFromString<RequestMessage>();
                    if(request.Type == RequestType.Joke)
                    { 
                        RespondWithjoke(channel, request.ResponseQueue, request.CorrelationId);
                        channel.BasicAck(ea.DeliveryTag, false);
                    }
                    else
                    {
                        channel.BasicReject(ea.DeliveryTag, true); //Alternatively new method which set it as dead later if it's also too old.
                    }
                };
                channel.BasicConsume(queue: QueueRequestName, autoAck: false, consumer: consumer);
                Console.WriteLine("Enter to exit.");
                Console.ReadLine();
            }
            Console.WriteLine("Bye!");
        }

        private static void RespondWithjoke(IModel channel, string ResponseQueue, string correlationId)
        {
            //string message = "hello world";
            Tuple<int, string> joke = GetJoke();
            MessageJoke message = new MessageJoke
            {
                JokeId = joke.Item1,
                Joke = joke.Item2,
                ResponseQueue = ResponseQueue,
                Type = RequestType.Joke,
                CorrelationId = correlationId
            };
            //var body = Encoding.UTF8.GetBytes(message);
            var body = Encoding.UTF8.GetBytes(message.XmlSerializeToString());

            channel.BasicPublish(exchange: "", routingKey: ResponseQueue, mandatory: false, basicProperties: null, body: body);
        }

        private static Tuple<int, string> GetJoke()
        {
            //if (amountToSpam > 100) return new Tuple<int, string>(0, RandomJoke()); //Quick and dirty way to disable the webrequests when requesting a lot of jokes.
            string webjoke = "";
            using (WebClient client = new WebClient())
            {
                Uri uri = new Uri("http://api.icndb.com/jokes/random");
                webjoke = client.DownloadString(uri);
            }

            ChuckNorrisJoke chuckNorrisJoke = JsonConvert.DeserializeObject<ChuckNorrisJoke>(webjoke);

            return new Tuple<int, string>(chuckNorrisJoke.value.id, chuckNorrisJoke.value.joke);
        }

        private static string RandomJoke()
        {
            IList<string> jokes = new List<string>
            {
                "How many roundhouse kicks does it take to get to the center of a tootsie pop? Just one. From Chuck Norris.",
                "Someone once videotaped Chuck Norris getting pissed off. It was called Walker: Texas Chain Saw Masacre.",
                "Chuck Norris can speak Braille.",
                "Chuck Norris can taste lies.",
                "Chuck Norris doesnt shave; he kicks himself in the face. The only thing that can cut Chuck Norris is Chuck Norris.",
                "Chuck Norris can touch MC Hammer.",
                "Think of a hot woman. Chuck Norris did her.",
                "When Chuck Norris makes a burrito, its main ingredient is real toes."
            };
            int random = new Random().Next(0, jokes.Count - 1);
            return jokes[random];
        }
    }

    public class Value
    {
        public int id { get; set; }
        public string joke { get; set; }
        public List<object> categories { get; set; }
    }

    public class ChuckNorrisJoke
    {
        public string type { get; set; }
        public Value value { get; set; }
    }
}