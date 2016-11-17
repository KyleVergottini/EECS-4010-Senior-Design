using System.Collections.Generic;
using BusinessLogic.Rooms;
using BusinessObjects;

namespace Services
{
    public class RoomService : IRoomService
    {
        private readonly IGetRoomsForAGivenConventionComponent _getRoomsComponent;
        private readonly IGetAllRoomsComponent _getAllRoomsComponent;

        public RoomService(IGetRoomsForAGivenConventionComponent getRoomsComponent)
        {
            _getRoomsComponent = getRoomsComponent;
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
    }
}
