namespace BusinessObjects
{
    public class User
    {
        public int Id { get; set; }
        public string Username { get; set; }
        public string HashedPassword { get; set; }
        public string PasswordSalt { get; set; }
    }
}