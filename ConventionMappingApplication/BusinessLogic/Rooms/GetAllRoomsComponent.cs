using System.Collections.Generic;
using System.Linq;
using DataAccess;
using Room = BusinessObjects.Room;

namespace BusinessLogic.Rooms
{
    public class GetAllRoomsComponent : IGetAllRoomsComponent
    {
        public List<Room> Execute()
        {
            using (var context = new Entities())
            {
                return context.Rooms
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
