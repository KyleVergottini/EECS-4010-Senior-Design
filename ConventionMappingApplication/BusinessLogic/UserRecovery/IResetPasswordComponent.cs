namespace BusinessLogic.UserRecovery
{
    public interface IResetPasswordComponent
    {
        bool Execute(string enteredUsername, string newPassword, string recoveryCode);
    }
}
