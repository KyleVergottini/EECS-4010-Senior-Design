using System.Collections.Generic;
using System.Linq;
using DataAccess;
using Event = BusinessObjects.Event;

namespace BusinessLogic.Events
{
    public class GetAllEventsForAConventionComponent : IGetAllEventsForAConventionComponent
    {
        public List<Event> Execute(int conventionId)
        {
            using (var context = new Entities())
            {
                return context.Conventions.Where(x => x.ID == conventionId)
                    .SelectMany(x => x.Rooms.Where(y => y.ConventionID == x.ID)
                        .SelectMany(e => e.Events.Where(z => z.RoomID == e.ID)))
                        .Select(x => new Event
                    {
                        ID = x.ID,
                        RoomID = x.RoomID,
                        Name = x.Name,
                        Description = x.Description,
                        EndDate = x.EndDate,
                        StartDate = x.StartDate
                    }).ToList();
            }
        }
    }
}
