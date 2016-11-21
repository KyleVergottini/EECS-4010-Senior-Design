using System.Collections.Generic;
using BusinessLogic.Rooms;
using BusinessObjects;

namespace Services
{
    public class RoomService : IRoomService
    {
        private readonly IGetRoomsForAGivenConventionComponent _getRoomsComponent;
        private readonly ISaveRoomsComponent _saveRoomsComponent;

        public RoomService(IGetRoomsForAGivenConventionComponent getRoomsComponent, ISaveRoomsComponent saveRoomsComponent)
        {
            _getRoomsComponent = getRoomsComponent;
            _saveRoomsComponent = saveRoomsComponent;
        }

        public List<Room> GetRoomsForGivenConventionId(int conventionId)
        {
            return _getRoomsComponent.Execute(conventionId);
        }

        public bool SaveRooms(int conventionId, int floorLevel, List<Room> rooms)
        {
            return _saveRoomsComponent.Execute(conventionId, floorLevel, rooms);
        }
    }
}
