namespace BusinessLogic.Users
{
    public interface ISaveUserComponent
    {
        bool Execute(string email, string password);
    }
}
