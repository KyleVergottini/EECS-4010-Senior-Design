using System.Collections.Generic;
using System.Linq;
using DataAccess;

namespace BusinessLogic.Schedules
{
    public class SaveScheduleComponent : ISaveScheduleComponent
    {
        public bool Execute(string username, int conventionID, List<int> eventIDs)
        {
            using (var context = new Entities())
            {
                //Get userID for username
                int userID = context.Users
                    .Where(x => x.Username == username)
                    .Select(x => x.ID)
                    .FirstOrDefault();

                //Build list of schedule records from passed-in IDs
                List<Schedule> scheduleRecords = new List<Schedule>();
                foreach (int eventID in eventIDs)
                {
                    scheduleRecords.Add(new Schedule
                    {
                        ConventionID = conventionID,
                        UserID = userID,
                        EventID = eventID
                    });
                }

                //Remove all schedule records in the database that weren't in the passed-in list
                List<Schedule> currentSchedule = context.Schedules
                    .Where(x => x.ConventionID == conventionID && x.UserID == userID)
                    .ToList();
                foreach (Schedule scheduleRecord in currentSchedule)
                {
                    if (!eventIDs.Contains(scheduleRecord.EventID))
                    {
                        context.Schedules.Remove(scheduleRecord);
                    }
                }

                //Get list of schedule records still in the database, and add any passed-in events that aren't in it
                List<int> currentEventIds = context.Schedules
                    .Where(x => x.ConventionID == conventionID && x.UserID == userID)
                    .Select(x => x.EventID)
                    .ToList();
                foreach (Schedule scheduleRecord in scheduleRecords)
                {
                    if (!currentEventIds.Contains(scheduleRecord.EventID))
                    {
                        context.Schedules.Add(scheduleRecord);
                    }
                }

                context.SaveChanges();
            }
            return true;
        }
    }
}
