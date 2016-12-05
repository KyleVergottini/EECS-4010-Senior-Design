using System.Collections.Generic;
using BusinessLogic.Schedules;

namespace Services
{
    public class ScheduleService : IScheduleService
    {
        private readonly ISaveScheduleComponent _saveScheduleComponent;
        private readonly IGetScheduleComponent _getScheduleComponent;

        public ScheduleService(ISaveScheduleComponent saveScheduleComponent, IGetScheduleComponent getScheduleComponent)
        {
            _saveScheduleComponent = saveScheduleComponent;
            _getScheduleComponent = getScheduleComponent;
        }

        public bool SaveSchedule(string username, int conventionID, List<int> eventIDs)
        {
            return _saveScheduleComponent.Execute(username, conventionID, eventIDs);
        }

        public List<int> GetSchedule(string username, int conventionID)
        {
            return _getScheduleComponent.Execute(username, conventionID);
        }
    }
}
