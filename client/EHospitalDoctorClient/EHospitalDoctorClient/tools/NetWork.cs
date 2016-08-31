using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.IO;

namespace EHospitalDoctorClient
{
    class NetWork
    {
        public String doGet(Dictionary<string,string> map,String url)
        {
            StringBuilder builder = new StringBuilder("?");
            foreach (var item in map)
            {
                byte[] a = Encoding.UTF8.GetBytes(item.Key);
                byte[] b = Encoding.UTF8.GetBytes(item.Value);
                builder.Append(Encoding.UTF8.GetString(a) + "=" + Encoding.UTF8.GetString(b) + "&");
            }
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url+builder);
            request.Method = "GET";
            request.ContentType = "text/html;charset=UTF-8";
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            Stream myResponseStream = response.GetResponseStream();
            StreamReader myStreamReader = new StreamReader(myResponseStream, Encoding.GetEncoding("utf-8"));
            string retString = myStreamReader.ReadToEnd();
            myStreamReader.Close();
            myResponseStream.Close();
            return retString;
        }
        public string doPost(Dictionary<String, string> map, string url)
        {
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
            request.Method = "POST";
            request.ContentType = "application/x-www-form-urlencoded;charset=utf-8";
            StringBuilder build = new StringBuilder();
            foreach (var item in map)
            {
                byte[] a = Encoding.UTF8.GetBytes(item.Key);
                byte[] b = Encoding.UTF8.GetBytes(item.Value);
                
                build.Append("&"+ Encoding.UTF8.GetString(a)+"="+ Encoding.UTF8.GetString(b));
            }
            Stream myRequestStream = request.GetRequestStream();
            StreamWriter myStreamWriter = new StreamWriter(myRequestStream, Encoding.GetEncoding("utf-8"));
            myStreamWriter.Write(build.ToString());
            myStreamWriter.Close();

            HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            Stream myResponseStream = response.GetResponseStream();
            StreamReader myStreamReader = new StreamReader(myResponseStream, Encoding.GetEncoding("utf-8"));
            string retString = myStreamReader.ReadToEnd();
            myStreamReader.Close();
            myResponseStream.Close();
            return retString;
           
        }
    }
}
