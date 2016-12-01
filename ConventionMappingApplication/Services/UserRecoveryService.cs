using BusinessLogic.UserRecovery;

namespace Services
{
    public class UserRecoveryService : IUserRecoveryService
    {
        private readonly IResetPasswordComponent _resetPasswordComponent;

        public UserRecoveryService(IResetPasswordComponent resetPasswordComponent)
        {
            _resetPasswordComponent = resetPasswordComponent;
        }

        public bool ResetPassword(string enteredUsername, string newPassword, string recoveryCode)
        {
            return _resetPasswordComponent.Execute(enteredUsername, newPassword, recoveryCode);
        }
    }
}
