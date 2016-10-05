using System;
using DataAccess;
using Convention = BusinessObjects.Convention;

namespace BusinessLogic.Conventions
{
    public class SaveConventionComponent : ISaveConventionComponent
    {
        private readonly IGetConventionByIdComponent _getConComponent;

        public SaveConventionComponent(IGetConventionByIdComponent getConComponent)
        {
            _getConComponent = getConComponent;
        }

        public int Execute(Convention convention)
        {
            var con = ConvertConvention(_getConComponent.Execute(convention.ID));

            using (var context = new Entities())
            {
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
                        //City = convention.City, TODO: add city to db
                        ZipCode = convention.ZipCode
                    };
                }
                else
                {
                    con.Name = convention.Name;
                    con.Address = convention.Address;
                    con.Description = convention.Description;
                    con.EndDate = Convert.ToDateTime(convention.EndDate);
                    con.StartDate = Convert.ToDateTime(convention.StartDate);
                    con.State = convention.State;
                    //con.City = convention.City;
                    con.ZipCode = convention.ZipCode;
                }
                context.Conventions.Add(con);
                context.SaveChanges();
            }
            return con.ID;
        }

        private static DataAccess.Convention ConvertConvention(Convention convention)
        {
            if (convention == null)
            {
                return null;
            }
            return new DataAccess.Convention
            {
                Name = convention.Name,
                Address = convention.Address,
                Description = convention.Description,
                EndDate = Convert.ToDateTime(convention.EndDate),
                StartDate = Convert.ToDateTime(convention.StartDate),
                State = convention.State,
                //City = convention.City, TODO: add city to db
                ZipCode = convention.ZipCode
            };
        }
    }
}
