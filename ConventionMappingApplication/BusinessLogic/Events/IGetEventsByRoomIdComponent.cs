using System.Collections.Generic;
using BusinessObjects;

namespace BusinessLogic.Events
{
    public interface IGetEventsByRoomIdComponent
    {
        List<Event> Execute(int roomId);
    }
}
