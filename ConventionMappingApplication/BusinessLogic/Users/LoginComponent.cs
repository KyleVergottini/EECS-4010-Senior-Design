using System;
using System.Linq;
using System.Text;
using System.Security.Cryptography;
using System.Data.Entity.Validation;
using DataAccess;
using User = BusinessObjects.User;

namespace BusinessLogic.Users
{
    public class LoginComponent : ILoginComponent
    {
        public bool Execute(string enteredUsername, string enteredPassword)
        {
            using (var context = new Entities())
            {
                User user = context.Users.Where(x => x.Username == enteredUsername)
                    .Select(x => new User { Username = x.Username, HashedPassword = x.HashedPassword, PasswordSalt = x.PasswordSalt }).FirstOrDefault();
                if (user != null)
                {
                    string hashedPassword = BCrypt.Net.BCrypt.HashPassword(enteredPassword, user.PasswordSalt);

                    if (hashedPassword == user.HashedPassword)
                    {
                        return true;
                    }
                }
                return false;
            }
        }
    }
}
