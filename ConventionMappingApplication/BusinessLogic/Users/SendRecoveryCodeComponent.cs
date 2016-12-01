using System;
using System.Text;
using System.Linq;
using System.Security.Cryptography;
using System.Net;
using DataAccess;
using RecoveryEmail = BusinessObjects.RecoveryEmail;

namespace BusinessLogic.Users
{
    public class SendRecoveryCodeComponent : ISendRecoveryCodeComponent
    {
        public bool Execute(string enteredUsername)
        {
            string recoveryCode = Guid.NewGuid().ToString();

            using (var context = new Entities())
            {
                DataAccess.User user = context.Users
                    .Where(x => x.Username == enteredUsername)
                    .FirstOrDefault();

                if (user == null)
                {
                    return false;
                }

                string hashedCode = BCrypt.Net.BCrypt.HashPassword(recoveryCode, user.PasswordSalt);

                DataAccess.UserRecovery userRecoveryRecord = context.UserRecovery
                    .Where(x => x.UserID == user.ID)
                    .FirstOrDefault();

                if (userRecoveryRecord != null)
                {
                    userRecoveryRecord.HashedRecoveryCode = hashedCode;
                    userRecoveryRecord.ExpirationDate = DateTime.Now.AddHours(1);
                }
                else
                {
                    DataAccess.UserRecovery newRecoveryRecord = new DataAccess.UserRecovery
                    {
                        UserID = user.ID,
                        HashedRecoveryCode = hashedCode,
                        ExpirationDate = DateTime.Now.AddHours(1)
                    };
                    context.UserRecovery.Add(newRecoveryRecord);
                }
                context.SaveChanges();
            }

            var json = (new RecoveryEmail(enteredUsername, recoveryCode)).ToJSON();
            string result = "";
            using (var client = new WebClient())
            {
                client.Headers[HttpRequestHeader.ContentType] = "application/json";
                result = client.UploadString("https://api.smtp2go.com/v3/email/send", "POST", json);
            }
            return true;
        }
    }
}