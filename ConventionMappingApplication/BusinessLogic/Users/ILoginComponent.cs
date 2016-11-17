namespace BusinessLogic.Users
{
    public interface ILoginComponent
    {
        bool Execute(string Username, string Password);
    }
}
