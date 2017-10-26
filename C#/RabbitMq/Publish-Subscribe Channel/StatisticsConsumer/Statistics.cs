using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using Utilities;

namespace StatisticsConsumer
{
    class Statistics
    {
        static void Main(string[] args)
        {
            var factory = new ConnectionFactory() { HostName = "localhost" };
            using (var connection = factory.CreateConnection())
            {
                int amount = 0;
                Console.WriteLine("");
                Console.WriteLine("Processed the following amount of jokes:  {0}", amount);
                using (var channel = connection.CreateModel())
                {
                    channel.ExchangeDeclare(exchange: "jokesExhange", type: "fanout", durable: false, autoDelete: false, arguments: null);
                    string queueName = channel.QueueDeclare().QueueName;
                    //channel.QueueDeclare(queue: "jokes", durable: false, exclusive: false, autoDelete: false, arguments: null);
                    channel.QueueBind(queue: queueName, exchange: "jokesExhange", routingKey: "", arguments: null);
                    //Console.WriteLine("Waiting..");
                    var consumer = new EventingBasicConsumer(channel);
                    consumer.Received += (model, ea) =>
                    {
                        var body = ea.Body;
                        var messageString = Encoding.UTF8.GetString(body);
                        Message message = messageString.XmlDeserializeFromString<Message>();

                        amount++;
                        //Console.Write("\r{0]  ", amount);
                        Console.WriteLine("Processed the following amount of jokes:  {0}", amount);
                    };
                    channel.BasicConsume(queue: queueName, autoAck: true, consumer: consumer);
                    //Console.WriteLine("Enter to exit.");
                    Console.ReadLine();
                }
            }
        }
    }
}
