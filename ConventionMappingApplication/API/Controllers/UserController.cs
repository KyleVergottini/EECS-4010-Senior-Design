using System;
using System.Web;
using System.Web.Http;
using System.Net.Mail;
using Services;
using BusinessLogic.Users;
using API.Models;

namespace API.Controllers
{
    public class UserController : ApiController
    {
        private IUserService _UserService;

        public UserController()
        {
            _UserService = new UserService(
                new CreateUserComponent(),
                new LoginComponent()
            );
        }

        [HttpPost]
        [Route("User/CreateUser/")]
        public IHttpActionResult CreateUser(UserCredentials userCredentials)
        {
            bool result = false;
            try
            {
                result = _UserService.CreateUser(userCredentials.email, userCredentials.password);
            }
            catch (Exception e)
            {
                return BadRequest("An error has occurred");
            }
            if (result == false)
            {
                return Ok(new ErrorReturn("A user with this email already exists"));
            }
            return Ok(new SuccessReturn("User created successfully"));
        }

        [HttpPost]
        [Route("User/Login/")]
        public IHttpActionResult Login(UserCredentials post)
        {
            bool result = false;
            try
            {
                result = _UserService.Login(post.email, post.password);
            }
            catch (Exception e)
            {
                return BadRequest("An error has occurred");
            }
            if (result == false)
            {
                return Ok(new ErrorReturn("Invalid username or password"));
            }
            return Ok(new SuccessReturn(post.email));
        }
    }
}