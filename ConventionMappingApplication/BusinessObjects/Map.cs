using System.Collections.Generic;

namespace BusinessObjects
{
    public class Map
    {
        public int ConventionId { get; set; }

        public int Level { get; set; }

        public byte[] MapImage { get; set; }

        public List<Room> RoomList { get; set; }
    }
}
