using System.Collections.Generic;
using BusinessObjects;

namespace Services
{
    public interface IEventService
    {
        Event GetEventById(int eventId);
        List<Event> GetEventForRoomId(int roomId);
    }
}
