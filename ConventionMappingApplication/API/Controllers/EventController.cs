using System;
using System.Collections.Generic;
using System.Web;
using System.Web.Http;
using Services;
using BusinessLogic.Events;
using BusinessObjects;

namespace API.Controllers
{
    public class EventController : ApiController
    {
        private IEventService _EventService;

        public EventController()
        {
            _EventService = new EventService(
                new GetEventsByRoomIdComponent(),
                new GetEventByIdComponent()
            );
        }

        [HttpGet]
        [Route("Event/GetEventById/{Id}")]
        public IHttpActionResult GetEventById(int Id)
        {
            Event result;
            try
            {
                result = _EventService.GetEventById(Id);
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            if (result == null)
            {
                return BadRequest("No event found for this Id");
            }
            return Ok(result);
        }

        [HttpGet]
        [Route("Event/GetEventByRoomId/{RoomId}")]
        public IHttpActionResult GetEventByRoomId(int RoomId)
        {
            List<Event> result;
            try
            {
                result = _EventService.GetEventForRoomId(RoomId);
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            if (result.Count == 0)
            {
                return BadRequest("No events found for this room Id");
            }
            return Ok(result);
        }
    }
}