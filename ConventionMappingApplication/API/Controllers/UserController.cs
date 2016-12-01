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
                new SaveUserComponent(),
                new LoginComponent(),
                new SendRecoveryCodeComponent()
            );
        }

        [HttpPost]
        [Route("User/CreateUser/")]
        public IHttpActionResult CreateUser(UserCredentials post)
        {
            bool result = false;
            try
            {
                result = _UserService.RegisterUser(post.email, post.password);
            }

            catch (Exception e)
            {
                if (e is System.Data.Entity.Infrastructure.DbUpdateException)
                {
                    return Ok(new ErrorReturn("A user with this email already exists"));
                }
                return BadRequest(e.ToString());
            }
            if (result == false)
            {
                return Ok(new ErrorReturn("Something when wrong when creating the user"));
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
        [Route("User/SendRecoveryCode/")]
        public IHttpActionResult SendRecoveryCode(UserCredentials post)
        {
            bool result = false;
            try
            {
                result = _UserService.SendRecoveryCode(post.email);
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            if (result == false)
            {
                return Ok(new ErrorReturn("Invalid username"));
            }
            return Ok(new SuccessReturn("Recovery email sent"));
        }
    }
}