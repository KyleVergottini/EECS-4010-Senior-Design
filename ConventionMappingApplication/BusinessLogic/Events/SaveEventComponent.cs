using System;
using System.Linq;
using DataAccess;
using Event = BusinessObjects.Event;

namespace BusinessLogic.Events
{
    public class SaveEventComponent : ISaveEventComponent
    {
        public bool Execute(Event conEvent)
        {
            using (var context = new Entities())
            {
                var returnedEvent = context.Events.FirstOrDefault(x => x.ID == conEvent.ID);
                if (returnedEvent == null)
                {
                    returnedEvent = new DataAccess.Event
                    {
                        Name = conEvent.Name,
                        RoomID = conEvent.RoomID,
                        Description = conEvent.Description,
                        EndDate = Convert.ToDateTime(conEvent.EndDate),
                        StartDate = Convert.ToDateTime(conEvent.StartDate)
                    };
                    context.Events.Add(returnedEvent);
                }
                else
                {
                    returnedEvent.Name = conEvent.Name;
                    returnedEvent.RoomID = conEvent.RoomID;
                    returnedEvent.Description = conEvent.Description;
                    returnedEvent.EndDate = Convert.ToDateTime(conEvent.EndDate);
                    returnedEvent.StartDate = Convert.ToDateTime(conEvent.StartDate);
                }
                context.SaveChanges();
            }
            return true;
        }
    }
}
