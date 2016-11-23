using DataAccess;

namespace BusinessLogic.Users
{
    public class SaveUserComponent : ISaveUserComponent
    {
        public bool Execute(string email, string password)
        {
            var salt = BCrypt.Net.BCrypt.GenerateSalt();
            var hashedPassword = BCrypt.Net.BCrypt.HashPassword(password, salt);

            using (var context = new Entities())
            {
                var newUser = new User
                {
                    Username = email,
                    PasswordSalt = salt,
                    HashedPassword = hashedPassword
                };
                context.Users.Add(newUser);
                if (context.SaveChanges() != 0)
                {
                    return true;
                }
            }
            return false;
        }
    }
}
