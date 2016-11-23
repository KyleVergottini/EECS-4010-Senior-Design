namespace Services
{
    public interface IUserService
    {
        bool RegisterUser(string email, string password);
    }
}
