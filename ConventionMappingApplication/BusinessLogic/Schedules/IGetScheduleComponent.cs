using System.Collections.Generic;

namespace BusinessLogic.Schedules
{
    public interface IGetScheduleComponent
    {
        List<int> Execute(string username, int conventionID);
    }
}
