using System.Collections.Generic;
using Room = BusinessObjects.Room;

namespace API.Models
{
    public class RoomReturnList
    {
        public List<RoomReturn> rooms;

        public RoomReturnList(List<Room> roomList)
        {
            this.rooms = new List<RoomReturn>();
            foreach (Room roomObject in roomList)
            {
                rooms.Add(new RoomReturn(roomObject));
            }
        }

        public class RoomReturn
        {
            public string RoomID;

            public string ConventionID;

            public string Name;

            public string Level;

            public string XCoordinate;

            public string YCoordinate;

            public RoomReturn(Room roomObject)
            {
                this.RoomID = roomObject.ID.ToString();

                this.ConventionID = roomObject.ConventionID.ToString();

                this.Name = roomObject.Name;

                this.Level = roomObject.Level.ToString();

                this.XCoordinate = roomObject.XCoordinate.ToString();

                this.YCoordinate = roomObject.YCoordinate.ToString();
            }
        }
    }
}