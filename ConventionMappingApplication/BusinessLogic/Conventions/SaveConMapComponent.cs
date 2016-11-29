using System.Linq;
using DataAccess;

namespace BusinessLogic.Conventions
{
    public class SaveConMapComponent : ISaveConMapComponent
    {
        public bool Execute(int conId, byte[] map)
        {
            using (var context = new Entities())
            {
                var con = context.Conventions.FirstOrDefault(x => x.ID == conId);

                if (con == null)
                    return false;

                if (con.Map1 == null)
                {
                    con.Map1 = map;
                }
                else if (con.Map2 == null)
                {
                    con.Map2 = map;
                }
                else if (con.Map3 == null)
                {
                    con.Map3 = map;
                }
                else
                {
                    return false;
                }

                context.SaveChanges();
            }

            return true;
        }
    }
}
