using System.Collections.Generic;
using System.Linq;
using DataAccess;
using Convention = BusinessObjects.Convention;

namespace BusinessLogic.Conventions
{
    public class GetAllConventionsComponent : IGetAllConventionsComponent
    {
        public List<Convention> Execute()
        {
            using (var context = new Entities())
            {
                return context.Conventions
                    .Select(x => new Convention
                    {
                        ID = x.ID,
                        Name = x.Name,
                        Address = x.Address,
                        //City = x.City, TODO: forgot city in db
                        State = x.State,
                        ZipCode = x.ZipCode,
                        Description = x.Description,
                        EndDate = x.EndDate,
                        StartDate = x.StartDate
                    }).ToList();
            }
        }
    }
}
