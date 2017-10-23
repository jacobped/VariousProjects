using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RabbitMQ.Client;

namespace Serialization
{
    class Program
    {
        static void Main(string[] args)
        {
            var factory = new ConnectionFactory() { HostName = "localhost" };
            using (var connection = factory.CreateConnection())
            using (var channel = connection.CreateModel())
            {
                channel.QueueDeclare(queue: "serial", durable: true, exclusive: false, autoDelete: false,
                    arguments: null);
                string message = "hello world";
                var body = Encoding.UTF8.GetBytes(message);

                for (int i = 0; i < 10; i++)
                {
                    channel.BasicPublish(exchange: "", routingKey: "serial", mandatory: false, basicProperties: null, body: body);
                }
                Console.WriteLine("I have done stuff!");
            }
        }
    }
}
