using BusinessLogic.Users;

namespace Services
{
    public class UserService : IUserService
    {
        private readonly ICreateUserComponent _createUserComponent;
        private readonly IGetRecoveryCodeComponent _getRecoveryCodeComponent;

        public UserService(ICreateUserComponent createUserComponent, IGetRecoveryCodeComponent getRecoveryCodeComponent)
        {
            _createUserComponent = createUserComponent;
            _getRecoveryCodeComponent = getRecoveryCodeComponent;
        }

        public bool CreateUser(string Username, string Password)
        {
            return _createUserComponent.Execute(Username, Password);
        }

        public string GetRecoveryCode(string Username)
        {
            return _getRecoveryCodeComponent.Execute(Username);
        }
    }
}
