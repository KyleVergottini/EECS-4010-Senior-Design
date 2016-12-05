using System.Collections.Generic;

namespace Services
{
    public interface IScheduleService
    {
        bool SaveSchedule(string username, int conventionID, List<int> eventIDs);

        List<int> GetSchedule(string username, int conventionID);
    }
}
