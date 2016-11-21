namespace BusinessLogic.Users
{
    public interface IGetRecoveryCodeComponent
    {
        string Execute(string enteredUsername);
    }
}
