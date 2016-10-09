using BusinessLogic.Users;

namespace Services
{
    public class UserService : IUserService
    {
        private readonly ICreateUserComponent _createUserComponent;

        public UserService(ICreateUserComponent createUserComponent)
        {
            _createUserComponent = createUserComponent;
        }

        public bool CreateUser(string Username, string Password)
        {
            return _createUserComponent.Execute(Username, Password);
        }
    }
}
