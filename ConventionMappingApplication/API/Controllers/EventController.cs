using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Linq.Expressions;
using BusinessLogic;
using BusinessLogic.Services;
using BusinessLogic.BusinessObjects;

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
            ParameterExpression param = filter.Parameters.Single();
            if (RoomID != null)
            {
                int RoomIDParam = RoomID ?? default(int);
                Expression<Func<Event, bool>> newFilter = e => e.RoomID == RoomIDParam;
                BinaryExpression body = Expression.AndAlso(filter.Body, Expression.Invoke(newFilter, param));
                filter = Expression.Lambda<Func<Event, bool>>(body, param);
            }
            if (StartDate != null)
            {
                DateTime StartDateParam = StartDate ?? default(DateTime);
                Expression<Func<Event, bool>> newFilter = e => e.StartDate >= StartDateParam;
                BinaryExpression body = Expression.AndAlso(filter.Body, Expression.Invoke(newFilter, param));
                filter = Expression.Lambda<Func<Event, bool>>(body, param);
            }
            if (EndDate != null)
            {
                DateTime EndDateParam = EndDate ?? default(DateTime);
                Expression<Func<Event, bool>> newFilter = e => e.EndDate <= EndDateParam;
                BinaryExpression body = Expression.AndAlso(filter.Body, Expression.Invoke(newFilter, param));
                filter = Expression.Lambda<Func<Event, bool>>(body, param);
            }
            IList<EventRecord> result = _DatabaseReadService.GetEvents(filter);
            if (result.Count == 0)
            {
                return BadRequest("No events found for this criteria");
            }
            return Ok(result);
        }
    }
}