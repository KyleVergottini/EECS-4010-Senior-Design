using System;
using System.Collections.Generic;
using System.Web;
using System.Web.Http;
using Services;
using BusinessLogic.Events;
using BusinessObjects;
using API.Models;

namespace API.Controllers
{
    public class EventController : ApiController
    {
        private IEventService _EventService;

        public EventController()
        {
            _EventService = new EventService(
                new GetAllEventsForAConventionComponent(),
                new GetAllEventsComponent()
            );
        }

        [HttpPost]
        [Route("Event/GetAllEventsForAConvention/")]
        public IHttpActionResult GetEventsForConvention(ConventionId post)
        {
            List<Event> result;
            try
            {
                result = _EventService.GetAllEventsForAConvention(post.conventionid);
            }
            catch (Exception e)
            {
                return BadRequest("An error has occurred");
            }
            if (result.Count == 0)
            {
                return BadRequest("No events found for this convention Id");
            }
            return Ok(result);
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
                return BadRequest("An error has occurred");
            }
            if (result.Count == 0)
            {
                return BadRequest("No events found");
            }
            return Ok(result);
        }
    }
}