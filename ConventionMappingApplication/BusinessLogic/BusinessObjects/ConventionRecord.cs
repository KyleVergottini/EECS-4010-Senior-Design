using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.Serialization;

namespace BusinessLogic.BusinessObjects
{
    [DataContract]
    public class ConventionRecord
    {
        [DataMember]
        public int ID { get; set; }

        [DataMember]
        public string Name { get; set; }

        [DataMember]
        public DateTime StartDate { get; set; }

        [DataMember]
        public DateTime EndDate { get; set; }

        [DataMember]
        public string Address { get; set; }

        [DataMember]
        public string State { get; set; }

        [DataMember]
        public int ZipCode { get; set; }

        [DataMember]
        public string Description { get; set; }

        public ConventionRecord (Convention convention)
        {
            this.ID = convention.ID;
            this.Name = convention.Name;
            this.StartDate = convention.StartDate;
            this.EndDate = convention.EndDate;
            this.Address = convention.Address;
            this.State = convention.State;
            this.ZipCode = convention.ZipCode;
            this.Description = convention.Description;
        }
    }
}
