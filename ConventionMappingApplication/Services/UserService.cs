using BusinessLogic.Users;

namespace Services
{
    public class UserService : IUserService
    {
        private readonly ISaveUserComponent _saveUserComponent;
        private readonly ILoginComponent _loginComponent;
        private readonly ISendRecoveryCodeComponent _sendRecoveryCodeComponent;

        public UserService(ISaveUserComponent saveUserComponent)
        {
            _saveUserComponent = saveUserComponent;
        }

        public UserService(ISaveUserComponent saveUserComponent, ILoginComponent loginComponent, ISendRecoveryCodeComponent sendRecoveryCodeComponent)
		{
			_saveUserComponent = saveUserComponent;
			_loginComponent = loginComponent;
            _sendRecoveryCodeComponent = sendRecoveryCodeComponent;
		}

        public bool RegisterUser(string email, string password)
        {
            return _saveUserComponent.Execute(email, password);
        }

        public bool Login(string Username, string Password)
        {
            return _loginComponent.Execute(Username, Password);
        }

        public bool SendRecoveryCode(string enteredUsername)
        {
            return _sendRecoveryCodeComponent.Execute(enteredUsername);
        }
    }
}