using System;
using System.Collections.Generic;
using System.Web;
using System.Web.Http;
using Services;
using BusinessLogic.Rooms;
using BusinessObjects;
using API.Models;

namespace API.Controllers
{
    public class RoomController : ApiController
    {
        private IRoomService _RoomService;

        public RoomController()
        {
            _RoomService = new RoomService(
                new GetRoomsForAGivenConventionComponent(),
                new GetAllRoomsComponent()
            );
        }

        [HttpPost]
        [Route("Room/GetRoomsForAGivenConvention/")]
        public IHttpActionResult GetRoomsForAGivenConvention(ConventionId post)
        {
            List<Room> result;
            try
            {
                result = _RoomService.GetRoomsForGivenConventionId(post.conventionid);
            }
            catch (Exception e)
            {
                return BadRequest("An error has occurred");
            }
            return Ok(new RoomReturnList(result));
        }

        [HttpPost]
        [Route("Room/GetAllRooms/")]
        public IHttpActionResult GetAllRooms()
        {
            List<Room> result;
            try
            {
                result = _RoomService.GetAllRooms();
            }
            catch (Exception e)
            {
                return BadRequest("An error has occurred");
            }
            return Ok(new RoomReturnList(result));
        }
    }
}
