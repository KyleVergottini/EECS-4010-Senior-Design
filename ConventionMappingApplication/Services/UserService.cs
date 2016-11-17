using BusinessLogic.Users;

namespace Services
{
    public class UserService : IUserService
    {
        private readonly ICreateUserComponent _createUserComponent;
        private readonly ILoginComponent _loginComponent;

        public UserService(ICreateUserComponent createUserComponent, ILoginComponent loginComponent)
        {
            _createUserComponent = createUserComponent;
            _loginComponent = loginComponent;
        }

        public bool CreateUser(string Username, string Password)
        {
            return _createUserComponent.Execute(Username, Password);
        }

        public bool Login(string Username, string Password)
        {
            return _loginComponent.Execute(Username, Password);
        }
    }
}
