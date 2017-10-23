﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;

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
                        var message = Encoding.UTF8.GetString(body);
                        Console.WriteLine("Received: {0}", message);
                    };
                    channel.BasicConsume(queue: "serial", autoAck: true, consumer: consumer);
                    Console.WriteLine("Done processing.");
                    Console.ReadLine();
                }
            }
            var s = hej(juelmand: "Santa", nej: true);
        }

        private static bool hej(string juelmand, bool nej)
        {
            return false;
        }
    }
}
