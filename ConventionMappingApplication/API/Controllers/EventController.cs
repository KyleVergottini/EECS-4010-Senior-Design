using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Linq.Expressions;
using BusinessLogic;
using BusinessLogic.Services;

namespace API.Controllers
{
    public class EventController : ApiController
    {
        private DatabaseReadService _DatabaseReadService;

        public EventController()
        {
            _DatabaseReadService = new DatabaseReadService();
        }

        [HttpGet]
        [Route("Event/GetScheduleForConvention/{ConventionID}")]
        public IHttpActionResult GetScheduleForConvention(int ConventionID, int? RoomID = null, DateTime? StartDate = null, DateTime? EndDate = null)
        {
            Expression<Func<Event, bool>> filter = e => e.Room.ConventionID == ConventionID;
            if (RoomID != null)
            {
                int RoomIDParam = RoomID ?? default(int);
                Expression<Func<Event, bool>> newFilter = e => e.RoomID == RoomIDParam;
                filter = Expression.Lambda<Func<Event, bool>>(Expression.And(filter, newFilter));
            }
            if (StartDate != null)
            {
                DateTime StartDateParam = StartDate ?? default(DateTime);
                Expression<Func<Event, bool>> newFilter = e => e.StartDate >= StartDateParam;
                filter = Expression.Lambda<Func<Event, bool>>(Expression.And(filter, newFilter));
            }
            if (EndDate != null)
            {
                DateTime EndDateParam = EndDate ?? default(DateTime);
                Expression<Func<Event, bool>> newFilter = e => e.EndDate <= EndDateParam;
                filter = Expression.Lambda<Func<Event, bool>>(Expression.And(filter, newFilter));
            }
            IList<Event> result = _DatabaseReadService.GetEvents(filter);
            if (result.Count == 0)
            {
                return BadRequest("No events found for this criteria");
            }
            return Ok(result);
        }
    }
}