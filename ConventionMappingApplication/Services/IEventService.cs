using BusinessObjects;

namespace Services
{
    public interface IEventService
    {
        Event GetEventsForRoom(int roomId);
    }
}
