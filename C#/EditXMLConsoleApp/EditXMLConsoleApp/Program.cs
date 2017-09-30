using System;
using System.Collections.Generic;
using System.Xml;

namespace EditXMLConsoleApp
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Beginning!");
            ProcessXML();
        }

        public static void ProcessXML()
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load(@"E:\filter.xml");

            XmlNode parentNode = xmlDoc.FirstChild;

            Console.WriteLine("Procecssing!");

            ProcessNode(parentNode);

            Console.WriteLine("Preparing to save!");

            xmlDoc.Save(@"E:\filterNEW.xml");

            Console.WriteLine("Done!");
            Console.ReadLine();
        }

        private static void ProcessNode(XmlNode node)
        {
            string nodeValue = node.Value as string;
            if (!string.IsNullOrWhiteSpace(nodeValue))
            {

                if (nodeValue.Substring(0, 3) == "opt")
                {
                    int test = nodeValue.Length;
                    string optrest = nodeValue.Substring(3, nodeValue.Length - 3);
                    int optValue = 0;
                    int.TryParse(optrest, out optValue);
                    if (optValue != 0)
                    {
                        optValue++;
                        node.Value = "opt" + optValue;
                    }
                }
            }

            if (node.ChildNodes.Count > 0)
            {
                foreach (XmlNode innerNode in node.ChildNodes)
                {
                    ProcessNode(innerNode);
                }
            }
        }
    }

}
