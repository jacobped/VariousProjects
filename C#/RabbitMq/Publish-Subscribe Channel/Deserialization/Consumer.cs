using System;
using System.Text;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using Utilities;

namespace JokeConsumer
{
    class Consumer
    {
        static void Main(string[] args)
        {
            var factory = new ConnectionFactory() { HostName = "localhost" };
            using (var connection = factory.CreateConnection())
            {
                using (var channel = connection.CreateModel())
                {
                    channel.ExchangeDeclare(exchange: "jokesExhange", type: "fanout", durable: false, autoDelete: false, arguments: null);
                    string queueName = channel.QueueDeclare().QueueName;
                    //channel.QueueDeclare(queue: "jokes", durable: false, exclusive: false, autoDelete: false, arguments: null);
                    channel.QueueBind(queue: queueName, exchange: "jokesExhange", routingKey: "", arguments: null);
                    Console.WriteLine("Waiting..");
                    var consumer = new EventingBasicConsumer(channel);
                    consumer.Received += (model, ea) =>
                    {
                        var body = ea.Body;
                        var messageString = Encoding.UTF8.GetString(body);
                        Message message = messageString.XmlDeserializeFromString<Message>();

                        Console.WriteLine("Received: {0}", message.Joke);
                    };
                    channel.BasicConsume(queue: queueName, autoAck: true, consumer: consumer);
                    Console.WriteLine("Enter to exit.");
                    Console.ReadLine();
                }
            }
        }
    }
}
