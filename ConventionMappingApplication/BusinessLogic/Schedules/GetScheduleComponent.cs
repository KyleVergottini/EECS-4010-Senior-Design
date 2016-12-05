using System.Collections.Generic;
using System.Linq;
using DataAccess;

namespace BusinessLogic.Schedules
{
    public class GetScheduleComponent : IGetScheduleComponent
    {
        public List<int> Execute(string username, int conventionID)
        {
            using (var context = new Entities())
            {
                //Get userID for username
                int userID = context.Users
                    .Where(x => x.Username == username)
                    .Select(x => x.ID)
                    .FirstOrDefault();

                List<int> schedule = context.Schedules
                    .Where(x => x.UserID == userID && x.ConventionID == conventionID)
                    .Select(x => x.EventID)
                    .ToList();

                return schedule;
            }
        }
    }
}
