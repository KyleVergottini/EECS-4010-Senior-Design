using System.Collections.Generic;

namespace BusinessLogic.Schedules
{
    public interface ISaveScheduleComponent
    {
        bool Execute(string username, int conventionID, List<int> eventIDs);
    }
}
