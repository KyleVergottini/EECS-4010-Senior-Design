namespace BusinessLogic.Users
{
    public interface ISendRecoveryCodeComponent
    {
        bool Execute(string enteredUsername);
    }
}
