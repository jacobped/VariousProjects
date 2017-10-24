using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml.Serialization;

namespace Utilities
{
    public static class SerializeHelper
    {
        public static string XmlSerializeToString(this object objectInstance)
        {
            return XmlSerializeToString(objectInstance, objectInstance.GetType());
        }

        public static string XmlSerializeToString(this object objectInstance, Type type)
        {
            #region Xml.Serialization
            Type[] extras = {objectInstance.GetType()}; // From the actual class type, for which the derived type is what the Xml will be based upon.
            //var serializer = new XmlSerializer(type, extras);
            var serializer = new XmlSerializer(type);
            var sb = new StringBuilder();

            using (TextWriter writer = new StringWriter(sb))
            {
                serializer.Serialize(writer, objectInstance);
            }
            return sb.ToString();
            #endregion

            #region Runtime.Serialization
            //using (MemoryStream memoryStream = new MemoryStream())
            //using (StreamReader streamReader = new StreamReader(memoryStream))
            //{
            //    DataContractSerializer serializer = new DataContractSerializer(objectInstance.GetType());
            //    serializer.WriteObject(memoryStream, objectInstance);
            //    memoryStream.Position = 0;
            //    return streamReader.ReadToEnd();
            //}
            #endregion
        }

        public static T XmlDeserializeFromString<T>(this string objectData)
        {
            return (T)XmlDeserializeFromString(objectData, typeof(T));
        }

        public static object XmlDeserializeFromString(this string objectData, Type type)
        {
            #region Xml.Serialization
            var serializer = new XmlSerializer(type);
            object result;

            using (TextReader reader = new StringReader(objectData))
            {
                result = serializer.Deserialize(reader);
            }

            return result;
            #endregion
            #region Runtime.Serialization
            //using (Stream stream = new MemoryStream())
            //{
            //    byte[] data = System.Text.Encoding.UTF8.GetBytes(objectData);
            //    stream.Write(data, 0, data.Length);
            //    stream.Position = 0;
            //    DataContractSerializer deserializer = new DataContractSerializer(type);
            //    return deserializer.ReadObject(stream);
            //}
            #endregion
        }
    }

}
