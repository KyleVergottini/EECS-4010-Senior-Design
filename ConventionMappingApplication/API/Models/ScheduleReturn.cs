using System.Collections.Generic;
using System.Linq;

namespace API.Models
{
    public class ScheduleReturn
    {
        public string ConventionID { get; set; }

        public List<string> Schedule { get; set; }

        public ScheduleReturn(string conventionID, List<int> schedule)
        {
            ConventionID = conventionID;
            Schedule = schedule.Select(x => x.ToString()).ToList();
        }
    }
}