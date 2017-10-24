using System;
using System.Collections.Generic;
using System.Linq;
using System.Xml.Serialization;
using System.Text;
using System.Threading.Tasks;
using RabbitMQ.Client;
using Utilities;

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
                //string message = "hello world";
                MQMessage message = new MQMessage { Test = "Hello World" };
                //var body = Encoding.UTF8.GetBytes(message);
                var body = Encoding.UTF8.GetBytes(message.XmlSerializeToString());

                for (int i = 0; i < 10; i++)
                {
                    channel.BasicPublish(exchange: "", routingKey: "serial", mandatory: false, basicProperties: null,
                        body: body);
                }
                Console.WriteLine("I have done stuff!");
            }
        }
    }
    
    [XmlType(TypeName = "MQMessage")]
    public class MQMessage : Message
    {
        public new string Test { get; set; }
    }
}
