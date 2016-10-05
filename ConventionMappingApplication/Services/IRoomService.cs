using System.Collections.Generic;
using BusinessObjects;

namespace Services
{
    public interface IRoomService
    {
        List<Room> GetRoomsForGivenConventionId(int conventionId);
    }
}
