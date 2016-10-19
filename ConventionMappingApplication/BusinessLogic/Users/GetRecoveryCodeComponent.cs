using System;
using System.Text;
using System.Linq;
using System.Security.Cryptography;
using DataAccess;

namespace BusinessLogic.Users
{
    public class GetRecoveryCodeComponent : IGetRecoveryCodeComponent
    {
        public string Execute(string enteredUsername)
        {
            using (var context = new Entities())
            {
                int userID = context.Users
                    .Where(x => x.Username == enteredUsername)
                    .Select(x => x.ID)
                    .FirstOrDefault();

                if (userID == 0)
                {
                    throw new Exception("No user exists for this email address");
                }

                string recoveryCode = Guid.NewGuid().ToString();

                byte[] recoveryCodeBytes = Encoding.UTF8.GetBytes(recoveryCode);
                byte[] hashedCodeBytes = (new SHA256CryptoServiceProvider()).ComputeHash(recoveryCodeBytes);
                string hashedCode = Convert.ToBase64String(hashedCodeBytes);

                DataAccess.UserRecovery userRecoveryRecord = context.UserRecovery
                    .Where(x => x.UserID == userID)
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
                        UserID = userID,
                        HashedRecoveryCode = hashedCode,
                        ExpirationDate = DateTime.Now.AddHours(1)
                    };
                    context.SaveChanges();
                }

                return recoveryCode;
            }
        }
    }
}