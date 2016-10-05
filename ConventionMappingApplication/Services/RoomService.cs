using System.Collections.Generic;
using BusinessLogic.Rooms;
using BusinessObjects;

namespace Services
{
    public class RoomService : IRoomService
    {
        private readonly IGetRoomsForAGivenConventionComponent _getRoomsComponent;

        public RoomService(IGetRoomsForAGivenConventionComponent getRoomsComponent)
        {
            _getRoomsComponent = getRoomsComponent;
        }

        public List<Room> GetRoomsForGivenConventionId(int conventionId)
        {
            return _getRoomsComponent.Execute(conventionId);
        }
    }
}
