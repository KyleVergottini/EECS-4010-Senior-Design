using System.Collections.Generic;
using BusinessObjects;

namespace BusinessLogic.Events
{
    public interface IGetAllEventsForAConventionComponent
    {
        List<Event> Execute(int conventionId);
    }
}
