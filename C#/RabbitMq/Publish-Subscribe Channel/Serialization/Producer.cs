using System;
using System.Collections;
using System.Collections.Generic;
using System.Net;
using System.Text;
using Newtonsoft.Json;
using RabbitMQ.Client;
using Utilities;

namespace JokeProducer
{
    class Producer
    {
        private static readonly int amountToSpam = 1000000000;

        static void Main(string[] args)
        {
            var factory = new ConnectionFactory() { HostName = "localhost" };
            using (var connection = factory.CreateConnection())
            using (var channel = connection.CreateModel())
            {
                channel.ExchangeDeclare( exchange: "jokesExhange", type: "fanout", durable: false, autoDelete: false, arguments: null);
                //channel.QueueDeclare(queue: "jokes", durable: false, exclusive: false, autoDelete: false, arguments: null);
                for (int i = 0; i < amountToSpam; i++)
                {
                    //string message = "hello world";
                    Tuple<int, string> joke =GetJoke();
                    Message message = new Message { JokeId = joke.Item1, Joke = joke.Item2 };
                    //var body = Encoding.UTF8.GetBytes(message);
                    var body = Encoding.UTF8.GetBytes(message.XmlSerializeToString());

                    channel.BasicPublish(exchange: "jokesExhange", routingKey: "", mandatory: false, basicProperties: null, body: body);
                    Console.WriteLine("Published message: {0}.", i+1);
                }
                Console.WriteLine("I have done stuff!");
            }
        }

        private static Tuple<int, string> GetJoke()
        {
            if(amountToSpam > 100) return new Tuple<int, string>(0, RandomJoke()); //Quick and dirty way to disable the webrequests when requesting a lot of jokes.
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
            int random = new Random().Next(0, jokes.Count-1);
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
