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
    public class EventController : ApiController
    {
        private IEventService _EventService;
        private IUserService _UserService;

        public EventController()
        {
            _EventService = new EventService(
                new GetAllEventsForAConventionComponent(),
                new GetAllEventsComponent()
            );
            _UserService = new UserService(
                new SaveUserComponent(),
                new LoginComponent(),
                new SendRecoveryCodeComponent()
            );
        }

        [HttpPost]
        [Route("Event/GetAllEventsForAConvention/")]
        public IHttpActionResult GetEventsForConvention(ConventionId post)
        {
            List<Event> result;
            try
            {
                result = _EventService.GetAllEventsForAConvention(int.Parse(post.conventionID));
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            return Ok(new EventReturnList(result));
        }

        [HttpPost]
        [Route("Event/GetAllEvents/")]
        public IHttpActionResult GetAllEvents()
        {
            List<Event> result;
            try
            {
                result = _EventService.GetAllEvents();
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            return Ok(new EventReturnList(result));
        }
    }
}