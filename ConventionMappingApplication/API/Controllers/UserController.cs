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
                new GetRecoveryCodeComponent()
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

        [HttpPost]
        [Route("Users/SendRecoveryCode/")]
        public IHttpActionResult SendRecoveryCode(UserCredentials userCredentials)
        {
            string recoveryCode;
            try
            {
                recoveryCode = _UserService.GetRecoveryCode(userCredentials.Username);
                new SmtpClient().Send(new RecoveryEmail(userCredentials.Username, recoveryCode));
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            return Ok("Recovery email has been sent");
        }
    }
}