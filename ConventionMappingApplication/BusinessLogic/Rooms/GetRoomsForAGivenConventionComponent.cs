using System.Collections.Generic;
using System.Linq;
using DataAccess;
using Room = BusinessObjects.Room;

namespace BusinessLogic.Rooms
{
    public class GetRoomsForAGivenConventionComponent : IGetRoomsForAGivenConventionComponent
    {
        public List<Room> Execute(int conventionId)
        {
            using (var context = new Entities())
            {
                return context.Rooms.Where(x => x.ConventionID == conventionId)
                    .Select(x => new Room
                    {
                        ID = x.ID,
                        ConventionID = x.ConventionID,
                        Name = x.Name,
                        Level = x.Level,
                        XCoordinate = x.XCoordinate,
                        YCoordinate = x.YCoordinate
                    }).ToList();
            }
        }
    }
}
