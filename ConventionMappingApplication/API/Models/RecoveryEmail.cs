using System.Net.Mail;

namespace API.Models
{
    public class RecoveryEmail : MailMessage
    {
        public RecoveryEmail(string Username, string RecoveryCode)
        {
            Subject = "ConventionMappingApplication Password Recovery";
            From = new MailAddress("ConventionMappingApplication@gmail.com");
            To.Add(new MailAddress(Username));
            
            IsBodyHtml = true;
            string bodyTemplate = "Please go to the link below to reset your ConventionMappingApplication account password:"
                + "<br />"
                + "<a href='https://www.google.com/webhp#q={0}'>https://www.google.com/webhp#q={1}</a>";

            Body = string.Format(bodyTemplate, RecoveryCode, RecoveryCode);
        }
    }
}