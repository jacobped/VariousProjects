using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using Utilities;

namespace Deserialization
{
    class Program
    {
        static void Main(string[] args)
        {
            var factory = new ConnectionFactory() { HostName = "localhost" };
            using (var connection = factory.CreateConnection())
            {
                using (var channel = connection.CreateModel())
                {
                    channel.QueueDeclare(queue: "serial", durable: true, exclusive: false, autoDelete: false,
                        arguments: null);
                    var consumer = new EventingBasicConsumer(channel);
                    consumer.Received += (model, ea) =>
                    {
                        var body = ea.Body;
                        var messageString = Encoding.UTF8.GetString(body);
                        MMMessage message = messageString.XmlDeserializeFromString<MMMessage>();

                        Console.WriteLine("Received: {0}", message.Test);
                    };
                    channel.BasicConsume(queue: "serial", autoAck: true, consumer: consumer);
                    Console.WriteLine("Done processing.");
                    Console.ReadLine();
                }
            }
        }
    }

    [XmlType(TypeName = "MQMessage")]
    public class MMMessage : Message
    {
        public new string Test { get; set; }
    }
}
