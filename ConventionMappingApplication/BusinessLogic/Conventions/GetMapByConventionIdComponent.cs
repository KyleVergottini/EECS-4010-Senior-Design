using System.Linq;
using BusinessObjects;
using DataAccess;

namespace BusinessLogic.Conventions
{
    public class GetMapByConventionIdComponent : IGetMapByConventionIdComponent
    {
        public ConMap Execute(int conId)
        {
            var conMap = new ConMap();
            using (var context = new Entities())
            {
                var con = context.Conventions.FirstOrDefault(x => x.ID == conId);
                if (con == null)
                    return null;
                conMap.Floor1 = con.Map1;
                conMap.Floor2 = con.Map2;
                conMap.Floor3 = con.Map3;
            }
            return conMap;
        }
    }
}
