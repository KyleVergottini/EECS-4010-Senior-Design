using System;
using System.Web;
using System.Web.Http;
using System.Net;
using System.Web.Script.Serialization;
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
                new LoginComponent(),
                new GetRecoveryCodeComponent()
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
                return BadRequest(e.ToString());
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
                return BadRequest(e.ToString());
            }
            if (result == false)
            {
                return Ok(new ErrorReturn("Invalid username or password"));
            }
            return Ok(new SuccessReturn(post.email));
        }


        [HttpPost]
        [Route("Users/SendRecoveryCode/")]
        public IHttpActionResult SendRecoveryCode(UserCredentials userCredentials)
        {
            string recoveryCode;
            try
            {
                recoveryCode = _UserService.GetRecoveryCode(userCredentials.email);
                if (recoveryCode == null)
                {
                    return Ok(new ErrorReturn("Invalid username"));
                }
                var json = (new RecoveryEmail(userCredentials.email, recoveryCode)).ToJSON();
                string result = "";
                using (var client = new WebClient())
                {
                    client.Headers[HttpRequestHeader.ContentType] = "application/json";
                    result = client.UploadString("https://api.smtp2go.com/v3/email/send", "POST", json);
                }
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            return Ok(new SuccessReturn("Recovery email has been sent"));
        }
    }
}