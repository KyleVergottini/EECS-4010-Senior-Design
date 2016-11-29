using System.Collections.Generic;
using BusinessLogic.Rooms;
using BusinessObjects;

namespace Services
{
    public class RoomService : IRoomService
    {
        private readonly IGetRoomsForAGivenConventionComponent _getRoomsComponent;
        private readonly IGetAllRoomsComponent _getAllRoomsComponent;
        private readonly ISaveRoomsComponent _saveRoomsComponent;

        public RoomService(IGetRoomsForAGivenConventionComponent getRoomsComponent, ISaveRoomsComponent saveRoomsComponent)
        {
            _getRoomsComponent = getRoomsComponent;
            _saveRoomsComponent = saveRoomsComponent;
        }

        public RoomService(IGetRoomsForAGivenConventionComponent getRoomsComponent, IGetAllRoomsComponent getAllRoomsComponent)
        {
            _getRoomsComponent = getRoomsComponent;
            _getAllRoomsComponent = getAllRoomsComponent;
        }

        public List<Room> GetRoomsForGivenConventionId(int conventionId)
        {
            return _getRoomsComponent.Execute(conventionId);
        }

        public List<Room> GetAllRooms()
        {
            return _getAllRoomsComponent.Execute();
		}
		
        public bool SaveRooms(int conventionId, int floorLevel, List<Room> rooms)
        {
            return _saveRoomsComponent.Execute(conventionId, floorLevel, rooms);
        }
    }
}
