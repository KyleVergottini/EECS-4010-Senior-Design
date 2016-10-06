using System.Linq;
using DataAccess;
using Event = BusinessObjects.Event;

namespace BusinessLogic.Events
{
    public class GetEventByIdComponent : IGetEventByIdComponent
    {
        public Event Execute(int eventId)
        {
            using (var context = new Entities())
            {
                return context.Events.Where(x => x.ID == eventId)
                    .Select(x => new
                    { 
                        ID = x.ID,
                        RoomID = x.RoomID,
                        Name = x.Name,
                        Description = x.Description,
                        EndDate = x.EndDate,
                        StartDate = x.StartDate
                    }).ToList()
                    .Select(x => new Event
                    {
                        ID = x.ID,
                        RoomID = x.RoomID,
                        Name = x.Name,
                        Description = x.Description,
                        EndDate = x.EndDate.ToString("yyyy/MM/dd hh:mm:ss"),
                        StartDate = x.StartDate.ToString("yyyy/MM/dd hh:mm:ss")
                    }).FirstOrDefault();
            }
        }
    }
}