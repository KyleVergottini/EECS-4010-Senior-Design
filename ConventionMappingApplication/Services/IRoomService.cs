using System.Collections.Generic;
using BusinessObjects;

namespace Services
{
    public interface IRoomService
    {
        List<Room> GetRoomsForGivenConventionId(int conventionId);
		
        List<Room> GetAllRooms();
        
		bool SaveRooms(int conventionId, int floorLevel, List<Room> rooms);
    }
}
