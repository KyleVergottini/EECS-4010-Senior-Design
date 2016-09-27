using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.Serialization;

namespace BusinessLogic.BusinessObjects
{
    [DataContract]
    public class EventRecord
    {
        [DataMember]
        public int ID { get; set; }

        [DataMember]
        public int RoomID { get; set; }

        [DataMember]
        public string Name { get; set; }

        [DataMember]
        public DateTime StartDate { get; set; }

        [DataMember]
        public DateTime EndDate { get; set; }

        [DataMember]
        public string Description { get; set; }

        public EventRecord(Event eventObject)
        {
            this.ID = eventObject.ID;
            this.RoomID = eventObject.RoomID;
            this.Name = eventObject.Name;
            this.StartDate = eventObject.StartDate;
            this.EndDate = eventObject.EndDate;
            this.Description = eventObject.Description;
        }
    }
}
