namespace Services
{
    public class UserService : IUserService
    {
        private readonly ISaveUserComponent _saveUserComponent;
        private readonly ILoginComponent _loginComponent;
        private readonly IGetRecoveryCodeComponent _getRecoveryCodeComponent;

        public UserService(ISaveUserComponent saveUserComponent)
        {
            _saveUserComponent = saveUserComponent;
        }
		
		public UserService(ISaveUserComponent saveUserComponent, ILoginComponent loginComponent, IGetRecoveryCode getRecoveryCode)
		{
			_saveUserComponent = saveUserComponent;
			_loginComponent = loginComponent;
			_getRecoveryCodeComponent = getRecoveryCode;
		}

        public bool RegisterUser(string email, string password)
        {
            return _saveUserComponent.Execute(email, password);
        }

        public bool Login(string Username, string Password)
        {
            return _loginComponent.Execute(Username, Password);
        }

        public string GetRecoveryCode(string Username)
        {
            return _getRecoveryCodeComponent.Execute(Username);
        }
    }
}