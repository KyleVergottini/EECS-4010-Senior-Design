using BusinessObjects;

namespace BusinessLogic.Users
{
    public interface ICreateUserComponent
    {
        bool Execute(string Username, string Password);
    }
}
