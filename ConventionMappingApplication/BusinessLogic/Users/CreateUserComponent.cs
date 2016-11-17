using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Data.Entity.Validation;
using DataAccess;
using BusinessObjects;

namespace BusinessLogic.Users
{
    public class CreateUserComponent : ICreateUserComponent
    {
        public bool Execute(string enteredUsername, string enteredPassword)
        {
            using (var context = new Entities())
            {
                BusinessObjects.User userCheck = context.Users.Where(x => x.Username == enteredUsername)
                    .Select(x => new BusinessObjects.User { Username = x.Username }).FirstOrDefault();
                if (userCheck == null)
                {
                    byte[] saltBytes = new byte[128];
                    (new RNGCryptoServiceProvider()).GetNonZeroBytes(saltBytes);
                    string salt = Convert.ToBase64String(saltBytes);

                    string saltedPassword = enteredPassword + salt;
                    byte[] saltedPasswordBytes = Encoding.UTF8.GetBytes(saltedPassword);

                    byte[] hashedPasswordBytes = (new SHA256CryptoServiceProvider()).ComputeHash(saltedPasswordBytes);
                    string hashedPassword = Convert.ToBase64String(hashedPasswordBytes);

                    DataAccess.User newUser = new DataAccess.User
                    {
                        Username = enteredUsername,
                        HashedPassword = hashedPassword,
                        PasswordSalt = salt
                    };

                    context.Users.Add(newUser);
                    context.SaveChanges();

                    return true;
                }
                return false;
            }
        }
    }
}
