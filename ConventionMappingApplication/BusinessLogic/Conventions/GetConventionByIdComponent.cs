using System.Linq;
using DataAccess;
using Convention = BusinessObjects.Convention;

namespace BusinessLogic.Conventions
{
    public class GetConventionByIdComponent : IGetConventionByIdComponent
    {
        public Convention Execute(int id)
        {
            using (var context = new Entities())
            {
                return context.Conventions.Where(x => x.ID == id)
                    .Select(x => new Convention
                    {
                        ID = x.ID,
                        Name = x.Name,
                        Address = x.Address,
                        //City = x.City, TODO: forgot city in db
                        State = x.State,
                        ZipCode = x.ZipCode,
                        Description = x.Description,
                        EndDate = x.EndDate.ToString("d"),
                        StartDate = x.StartDate.ToString("d")
                    }).FirstOrDefault();
            }
        }
    }
}
