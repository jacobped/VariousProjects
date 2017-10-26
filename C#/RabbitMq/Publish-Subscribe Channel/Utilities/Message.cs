using System;

namespace Utilities
{
    [Serializable]
    public class Message
    {
        public int JokeId { get; set; }
        public string Joke { get; set; }
    }
}