using System.Collections.Generic;
using System.Linq;
using DataAccess;
using Event = BusinessObjects.Event;

namespace BusinessLogic.Events
{
    public class GetAllEventsComponent : IGetAllEventsComponent
    {
        public List<Event> Execute()
        {
            using (var context = new Entities())
            {
                return context.Events
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
