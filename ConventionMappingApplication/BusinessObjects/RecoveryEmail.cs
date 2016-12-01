using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using System.IO;
using System.Collections.Generic;

namespace BusinessObjects
{
    [DataContract]
    public class RecoveryEmail
    {
        [DataMember]
        string api_key = "api-AEEEBE28AF5711E684ABF23C91C88F4E";

        [DataMember]
        List<string> to = new List<string>();

        [DataMember]
        string sender = "kyle.vergottini@gmail.com";

        [DataMember]
        string subject = "ConventionMappingApplication Password Recovery";

        [DataMember]
        string text_body;

        [DataMember]
        string html_body;

        static readonly string text = "Please go to the link below to reset your ConventionMappingApplication account password:";

        static readonly string recoveryURL = "http://lowcost-env.uffurjxps4.us-west-2.elasticbeanstalk.com/UserRecovery/RecoveryForm";

        public RecoveryEmail(string Username, string RecoveryCode)
        {
            to.Add(Username);

            string recoveryLink = string.Format(recoveryURL + "?RecoveryCode={0}", RecoveryCode);

            text_body = text + " " + recoveryLink;
            html_body = text + "<br /><a href='" + recoveryLink + "'>" + recoveryLink + "</a>";
        }

        public string ToJSON()
        {
            MemoryStream ms = new MemoryStream();
            DataContractJsonSerializer json = new DataContractJsonSerializer(typeof(RecoveryEmail));
            json.WriteObject(ms, this);
            ms.Position = 0;
            StreamReader sr = new StreamReader(ms);
            return sr.ReadToEnd();
        }
    }
}