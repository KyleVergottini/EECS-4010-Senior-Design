using System.Collections.Generic;
using BusinessObjects;

namespace BusinessLogic.Rooms
{
    public interface IGetAllRoomsComponent
    {
        List<Room> Execute();
    }
}
