using System.Collections.Generic;
using BusinessObjects;

namespace Services
{
    public interface IMapService
    {
        List<Map> GetMapForConvention(int conventionId);
    }
}
