namespace Services
{
    public interface IUserService
    {
        bool RegisterUser(string email, string password);
		
        bool Login(string Username, string Password);
        
		string GetRecoveryCode(string Username);
    }
}