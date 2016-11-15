using System.Drawing;
using BusinessObjects;

namespace Services
{
    public interface IMapService
    {
        Map GetMapForConvention(int conventionId);
    }
}
