namespace API.Models
{
    public class UserRecoveryModel
    {
        public string userRecoveryCode { get; set; }

        public string email { get; set; }

        public string password { get; set; }

        public string returnMessage { get; set; }
    }
}