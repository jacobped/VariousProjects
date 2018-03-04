using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {
            var s = new stupid();
            s.ProcessXML();
        }
    }

    class stupid
    {
        public void ProcessXML()
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load(@"E:\dhcp.xml");

            XmlNode parentNode = xmlDoc.FirstChild;

            Console.WriteLine("Beginning!");

            processNode(xmlDoc, parentNode);

            Console.WriteLine("Preparing to save!");

            xmlDoc.Save(@"E:\dhcpNEW.xml");

            Console.WriteLine("Done!");
            Console.ReadLine();
        }

        void processNode(XmlDocument doc, XmlNode node)
        {
            foreach (XmlNode innerNode in node.ChildNodes)
            {
                string nodeName = innerNode.Name as string;
                var s = nodeName.Substring(0, 3);
                if (s == "opt")
                {
                    int test = nodeName.Length;
                    string optrest = nodeName.Substring(3, nodeName.Length - 3);
                    int optValue = 0;
                    int.TryParse(optrest, out optValue);
                    if (optValue != 0)
                    {
                        optValue++;
                        String newName = "opt" + optValue;
                        var newNode = doc.CreateNode(XmlNodeType.Element, newName, innerNode.NamespaceURI);
                        newNode.InnerXml = innerNode.InnerXml/*.Replace(innerNode.Name + ">", newName + ">")*/;

                        //node.ReplaceChild(newNode, innerNode);
                        node.InsertBefore(newNode, innerNode);
                        node.RemoveChild(innerNode);
                    }
                }

            }

            //if (node.ChildNodes.Count > 0)
            //{
            //    foreach (XmlNode innerNode in node.ChildNodes)
            //    {
            //        processNode(innerNode);
            //    }
            //}

        }
    }
}
