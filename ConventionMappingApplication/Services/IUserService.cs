namespace Services
{
    public interface IUserService
    {
        bool CreateUser(string Username, string Password);
        string GetRecoveryCode(string Username);
    }
}
