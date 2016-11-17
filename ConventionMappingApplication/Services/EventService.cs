using System.Collections.Generic;
using BusinessLogic.Events;
using BusinessObjects;

namespace Services
{
    public class EventService : IEventService
    {
        private readonly IGetEventsByRoomIdComponent _getEventByRoomIdComponent;
        private readonly IGetEventByIdComponent _getEventByIdComponent;
        private readonly ISaveEventComponent _saveEventComponent;
        private readonly IGetAllEventsForAConventionComponent _getAllEventsForAConventionComponent;
        private readonly IGetAllEventsComponent _getAllEventsComponent;

        public EventService(IGetEventsByRoomIdComponent getEventByRoomIdComponent, IGetEventByIdComponent getEventByIdComponent, ISaveEventComponent saveEventComponent, IGetAllEventsForAConventionComponent getAllEventsForAConventionComponent)
        {
            _getEventByRoomIdComponent = getEventByRoomIdComponent;
            _getEventByIdComponent = getEventByIdComponent;
            _saveEventComponent = saveEventComponent;
            _getAllEventsForAConventionComponent = getAllEventsForAConventionComponent;
        }

        public EventService(IGetAllEventsForAConventionComponent getAllEventsForAConventionComponent, IGetAllEventsComponent getAllEventsComponent)
        {
            _getAllEventsForAConventionComponent = getAllEventsForAConventionComponent;
            _getAllEventsComponent = getAllEventsComponent;
        }

        public Event GetEventById(int eventId)
        {
            return _getEventByIdComponent.Execute(eventId);
        }

        public List<Event> GetEventForRoomId(int roomId)
        {
            return _getEventByRoomIdComponent.Execute(roomId);
        }

        public bool SaveEvent(Event conEvent)
        {
            return _saveEventComponent.Execute(conEvent);
        }

        public List<Event> GetAllEventsForAConvention(int conventionId)
        {
            return _getAllEventsForAConventionComponent.Execute(conventionId);
        }

        public List<Event> GetAllEvents()
        {
            return _getAllEventsComponent.Execute();
        }
    }
}
