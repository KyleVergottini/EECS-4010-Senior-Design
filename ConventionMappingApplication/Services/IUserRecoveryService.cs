namespace Services
{
    public interface IUserRecoveryService
    {
        bool ResetPassword(string enteredUsername, string newPassword, string recoveryCode);
    }
}