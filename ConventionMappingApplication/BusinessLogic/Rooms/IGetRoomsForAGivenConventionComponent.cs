using System.Collections.Generic;
using BusinessObjects;

namespace BusinessLogic.Rooms
{
    public interface IGetRoomsForAGivenConventionComponent
    {
        List<Room> Execute(int conventionId);
    }
}
