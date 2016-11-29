using System;
using System.Linq;
using DataAccess;
using Convention = BusinessObjects.Convention;

namespace BusinessLogic.Conventions
{
    public class SaveConventionComponent : ISaveConventionComponent
    {
        public int Execute(Convention convention)
        {
            using (var context = new Entities())
            {
                var con = context.Conventions.FirstOrDefault(x => x.ID == convention.ID);
                if (con == null)
                {
                    con = new DataAccess.Convention
                    {
                        Name = convention.Name,
                        Address = convention.Address,
                        Description = convention.Description,
                        EndDate = Convert.ToDateTime(convention.EndDate),
                        StartDate = Convert.ToDateTime(convention.StartDate),
                        State = convention.State,
                        City = convention.City,
                        ZipCode = convention.ZipCode
                    };
                    context.Conventions.Add(con);
                }
                else
                {
                    con.Name = convention.Name;
                    con.Address = convention.Address;
                    con.Description = convention.Description;
                    con.EndDate = Convert.ToDateTime(convention.EndDate);
                    con.StartDate = Convert.ToDateTime(convention.StartDate);
                    con.State = convention.State;
                    con.City = convention.City;
                    con.ZipCode = convention.ZipCode;
                }
                context.SaveChanges();
                return con.ID;
            }
        }
    }
}
