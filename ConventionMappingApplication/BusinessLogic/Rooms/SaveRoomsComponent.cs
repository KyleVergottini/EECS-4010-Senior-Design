using System.Collections.Generic;
using System.Linq;
using DataAccess;
using Room = BusinessObjects.Room;

namespace BusinessLogic.Rooms
{
    public class SaveRoomsComponent : ISaveRoomsComponent
    {
        public bool Execute(int conventionId, int floorLevel, List<Room> rooms)
        {
            using (var context = new Entities())
            {
                foreach (var r in rooms)
                {
                    int id;
                    int.TryParse(r.JavascriptID, out id);

                    var room = context.Rooms.FirstOrDefault(x => x.ConventionID == conventionId && x.ID == id);
                    if (room == null)
                    {
                        room = new DataAccess.Room
                        {
                            Name = "Floor " + floorLevel + ": " + r.JavascriptID,
                            ConventionID = conventionId,
                            Level = floorLevel,
                            XCoordinate = r.XCoordinate,
                            YCoordinate = r.YCoordinate
                        };
                        context.Rooms.Add(room);
                    }
                    else
                    {
                        room.ConventionID = r.ConventionID;
                        room.Level = floorLevel;
                        room.XCoordinate = r.XCoordinate;
                        room.YCoordinate = r.YCoordinate;
                    }
                    context.SaveChanges();
                }
                return true;
            }
        }
    }
}
