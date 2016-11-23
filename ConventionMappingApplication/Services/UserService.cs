using BusinessLogic.Users;

namespace Services
{
    public class UserService : IUserService
    {
        private readonly ISaveUserComponent _saveUserComponent;

        public UserService(ISaveUserComponent saveUserComponent)
        {
            _saveUserComponent = saveUserComponent;
        }

        public bool RegisterUser(string email, string password)
        {
            return _saveUserComponent.Execute(email, password);
        }
    }
}
