using System;
using System.Linq;
using DataAccess;

namespace BusinessLogic.UserRecovery
{
    public class ResetPasswordComponent : IResetPasswordComponent
    {
        public bool Execute(string enteredUsername, string newPassword, string recoveryCode)
        {
            using (var context = new Entities())
            {
                DataAccess.User user = context.Users
                    .Where(x => x.Username == enteredUsername)
                    .FirstOrDefault();

                if (user == null)
                {
                    return false; //User does not exist
                }

                DataAccess.UserRecovery userRecovery = context.UserRecovery
                    .Where(x => x.UserID == user.ID)
                    .FirstOrDefault();

                if (   userRecovery == null //No recovery record exists
                    || userRecovery.ExpirationDate < new DateTime() //Recovery record found is expired
                    || userRecovery.HashedRecoveryCode != BCrypt.Net.BCrypt.HashPassword(recoveryCode, user.PasswordSalt) //Recovery code does not match
                    )
                {
                    return false;
                }

                user.HashedPassword = BCrypt.Net.BCrypt.HashPassword(newPassword, user.PasswordSalt); //Set new password
                context.UserRecovery.Remove(userRecovery); //Delete user recovery record
                context.SaveChanges();

                return true;
            }
        }
    }
}
