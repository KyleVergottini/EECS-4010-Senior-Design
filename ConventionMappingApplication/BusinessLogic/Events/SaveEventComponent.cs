using System;
using DataAccess;
using Event = BusinessObjects.Event;

namespace BusinessLogic.Events
{
    public class SaveEventComponent : ISaveEventComponent
    {
        private readonly IGetEventByIdComponent _getEventByIdComponent;

        public SaveEventComponent(IGetEventByIdComponent getEventByIdComponent)
        {
            _getEventByIdComponent = getEventByIdComponent;
        }

        public bool Execute(Event conEvent)
        {
            var returnedEvent = ConvertEvent(_getEventByIdComponent.Execute(conEvent.ID));

            using (var context = new Entities())
            {
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
                }
                else
                {
                    returnedEvent.Name = conEvent.Name;
                    returnedEvent.RoomID = conEvent.RoomID;
                    returnedEvent.Description = conEvent.Description;
                    returnedEvent.EndDate = Convert.ToDateTime(conEvent.EndDate);
                    returnedEvent.StartDate = Convert.ToDateTime(conEvent.StartDate);
                }
                context.Events.Add(returnedEvent);
                context.SaveChanges();
            }
            return true;
        }

        private static DataAccess.Event ConvertEvent(Event conEvent)
        {
            if (conEvent == null)
            {
                return null;
            }
            return new DataAccess.Event
            {
                Name = conEvent.Name,
                RoomID = conEvent.RoomID,
                Description = conEvent.Description,
                EndDate = Convert.ToDateTime(conEvent.EndDate),
                StartDate = Convert.ToDateTime(conEvent.StartDate)
            };
        }
    }
}
