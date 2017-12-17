using System;

namespace Utilities
{
    [Serializable]
    public class MessageJoke : RequestMessage
    {
        public int JokeId { get; set; }
        public string Joke { get; set; }
    }

    [Serializable]
    public class RequestMessage
    {
        public string ResponseQueue { get; set; }
        public string CorrelationId { get; set; }
        public RequestType Type { get; set; }
    }

    public enum RequestType
    {
        Joke
    }
}
