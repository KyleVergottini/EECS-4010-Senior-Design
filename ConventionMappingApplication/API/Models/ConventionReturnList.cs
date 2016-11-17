using System.Collections.Generic;
using Convention = BusinessObjects.Convention;

namespace API.Models
{
    public class ConventionReturnList
    {
        public List<ConventionReturn> conventions;

        public ConventionReturnList(List<Convention> conventionList)
        {
            this.conventions = new List<ConventionReturn>();
            foreach (Convention conObject in conventionList)
            {
                conventions.Add(new ConventionReturn(conObject));
            }
        }

        public class ConventionReturn
        {
            public string ConventionID;

            public string Name;

            public string StartDate;

            public string EndDate;

            public string StreetAddress;

            public string State;

            public string ZipCode;

            public string Description;

            public string City;

            public ConventionReturn(Convention conObject)
            {
                this.ConventionID = conObject.ID.ToString();

                this.Name = conObject.Name;

                this.StartDate = conObject.StartDate.ToString("yyyy-MM-dd");

                this.EndDate = conObject.EndDate.ToString("yyyy-MM-dd");

                this.StreetAddress = conObject.Address;

                this.State = conObject.State;

                this.ZipCode = conObject.ZipCode.ToString();

                this.Description = conObject.Description;

                this.City = conObject.City;
            }
        }
    }
}