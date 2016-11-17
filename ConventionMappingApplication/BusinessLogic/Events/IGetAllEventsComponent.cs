using System.Collections.Generic;
using BusinessObjects;

namespace BusinessLogic.Events
{
    public interface IGetAllEventsComponent
    {
        List<Event> Execute();
    }
}
