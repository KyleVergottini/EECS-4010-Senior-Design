using System.Collections.Generic;
using BusinessLogic.Events;
using BusinessObjects;

namespace Services
{
    public class EventService : IEventService
    {
        private readonly IGetEventsByRoomIdComponent _getEventByRoomIdComponent;
        private readonly IGetEventByIdComponent _getEventByIdComponent;

        public EventService(IGetEventsByRoomIdComponent getEventByRoomIdComponent, IGetEventByIdComponent getEventByIdComponent)
        {
            _getEventByRoomIdComponent = getEventByRoomIdComponent;
            _getEventByIdComponent = getEventByIdComponent;
        }

        public Event GetEventById(int eventId)
        {
            return _getEventByIdComponent.Execute(eventId);
        }

        public List<Event> GetEventForRoomId(int roomId)
        {
            return _getEventByRoomIdComponent.Execute(roomId);
        }
    }
}
