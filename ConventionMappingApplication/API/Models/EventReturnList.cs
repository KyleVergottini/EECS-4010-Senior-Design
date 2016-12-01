using System.Collections.Generic;
using Event = BusinessObjects.Event;

namespace API.Models
{
    public class EventReturnList
    {
        public List<EventReturn> events;

        public EventReturnList(List<Event> eventList)
        {
            events = new List<EventReturn>();
            foreach (Event eventObject in eventList)
            {
                events.Add(new EventReturn(eventObject));
            }
        }
    }

    public class EventReturn
    {
        public string EventID;

        public string RoomID;

        public string Name;

        public string StartTime;

        public string EndTime;

        public string Description;

        public EventReturn(Event eventObject)
        {
            this.EventID = eventObject.ID.ToString();

            this.RoomID = eventObject.RoomID.ToString();

            this.Name = eventObject.Name;

            this.StartTime = eventObject.StartDate.ToString("yyyy-MM-dd HH:mm:ss");

            this.EndTime = eventObject.EndDate.ToString("yyyy-MM-dd HH:mm:ss");

            this.Description = eventObject.Description;
        }
    }
}