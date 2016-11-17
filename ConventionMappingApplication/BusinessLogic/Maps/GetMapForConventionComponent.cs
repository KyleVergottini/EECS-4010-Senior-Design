using System.Drawing;
using System.IO;
using System.Linq;
using DataAccess;
using Map = BusinessObjects.Map;

namespace BusinessLogic.Maps
{
    public class GetMapForConventionComponent : IGetMapForConventionComponent
    {
        public Map Execute(int conventionId)
        {
            using (var context = new Entities())
            {
                return context.Conventions.Where(x => x.ID == conventionId)
                    .Select(x => new Map
                    {
                        MapImage1 = x.Map1,
                        MapImage2 = x.Map2,
                        MapImage3 = x.Map3
                    }).FirstOrDefault();
            }
        }
    }
}
