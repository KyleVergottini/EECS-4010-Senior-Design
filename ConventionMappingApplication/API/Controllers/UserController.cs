using System;
using System.Web;
using System.Web.Http;
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
                new CreateUserComponent()
            );
        }

        [HttpPost]
        [Route("Users/CreateUser/")]
        public IHttpActionResult CreateUser(UserCredentials userCredentials)
        {
            bool result;
            try
            {
                result = _UserService.CreateUser(userCredentials.Username, userCredentials.Password);
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            if (result == false)
            {
                return BadRequest("A user with this email already exists");
            }
            return Ok("User created successfully");
        }
    }
}