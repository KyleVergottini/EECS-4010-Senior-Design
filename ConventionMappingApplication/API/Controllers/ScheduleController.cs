using System;
using System.Collections.Generic;
using System.Web;
using System.Web.Http;
using Services;
using BusinessLogic.Events;
using BusinessLogic.Users;
using BusinessLogic.Schedules;
using BusinessObjects;
using API.Models;

namespace API.Controllers
{
    public class ScheduleController : ApiController
    {
        private IScheduleService _ScheduleService;
        private IUserService _UserService;

        public ScheduleController()
        {
            _ScheduleService = new ScheduleService(
                new SaveScheduleComponent(),
                new GetScheduleComponent()
            );
            _UserService = new UserService(
                new SaveUserComponent(),
                new LoginComponent(),
                new SendRecoveryCodeComponent()
            );
        }
        
        [HttpPost]
        [Route("Schedule/GetUserSchedule/")]
        public IHttpActionResult GetUserSchedule(SchedulePost post)
        {
            //Validate user login
            bool loginSuccess = false;
            try
            {
                loginSuccess = _UserService.Login(post.email, post.password);
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            if (!loginSuccess)
            {
                return Ok(new ErrorReturn("Invalid username or password"));
            }

            //Get user's schedule
            List<int> result;
            try
            {
                result = _ScheduleService.GetSchedule(post.email, int.Parse(post.conventionID));
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            return Ok(new ScheduleReturn(post.conventionID, result));
        }

        [HttpPost]
        [Route("Schedule/SaveUserSchedule/")]
        public IHttpActionResult SaveUserSchedule(SchedulePost post)
        {
            bool result = false;

            //Validate user login
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

            //Update schedule
            try
            {
                _ScheduleService.SaveSchedule(post.email, int.Parse(post.conventionID), post.parseEventIDList());
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            return Ok(new SuccessReturn("User schedule updated"));
        }
    }
}