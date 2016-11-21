using System.Collections.Generic;
using BusinessObjects;

namespace BusinessLogic.Rooms
{
    public interface ISaveRoomsComponent
    {
        bool Execute(int conventionId, int floorLevel, List<Room> rooms);
    }
}
