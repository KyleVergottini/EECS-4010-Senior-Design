using System.Collections.Generic;
using System.Linq;

namespace API.Models
{
    public class SchedulePost
    {
        public string email { get; set; }

        public string password { get; set; }

        public string conventionID { get; set; }

        public string eventIDList { get; set; }

        public List<int> parseEventIDList()
        {
            return new List<string>(eventIDList.Split(',')).Select(x => int.Parse(x)).ToList();
        }
    }
}