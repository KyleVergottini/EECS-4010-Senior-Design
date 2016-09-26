using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogic.Services
{
    public class DatabaseReadService
    {
        private ConventionMappingDataContext dbConventionMapping = new ConventionMappingDataContext("Data Source=URASHIMA\\SQLEXPRESS;Initial Catalog=ConventionMappingApplication;Integrated Security=True");

        public IList<Convention> GetConventions(Expression<Func<Convention, bool>> Filter)
        {
            return
                (from Conventions in dbConventionMapping.Conventions
                select Conventions)
                .Where(Filter)
                .ToList<Convention>();
        }

        public IList<Room> GetRooms(Expression<Func<Room, bool>> Filter)
        {
            return
                (from Rooms in dbConventionMapping.Rooms
                select Rooms)
                .Where(Filter)
                .ToList<Room>();
        }

        public IList<Event> GetEvents(Expression<Func<Event, bool>> Filter)
        {
            return
                (from Events in dbConventionMapping.Events
                 select Events)
                .Where(Filter)
                .ToList<Event>();
        }

        public IList<User> GetUsers(Expression<Func<User, bool>> Filter)
        {
            return
                (from Users in dbConventionMapping.Users
                select Users)
                .Where(Filter)
                .ToList<User>();
        }

        public IList<ConventionRole> GetConventionRoles(Expression<Func<ConventionRole, bool>> Filter)
        {
            return
                (from ConventionRoles in dbConventionMapping.ConventionRoles
                select ConventionRoles)
                .Where(Filter)
                .ToList<ConventionRole>();
        }

        public IList<Schedule> GetSchedules(Expression<Func<Schedule, bool>> Filter)
        {
            return
                (from Schedules in dbConventionMapping.Schedules
                select Schedules)
                .Where(Filter)
                .ToList<Schedule>();;
        }
    }
}
