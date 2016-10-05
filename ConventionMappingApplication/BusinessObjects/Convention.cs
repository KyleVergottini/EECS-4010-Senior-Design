using System;

namespace BusinessObjects
{
    public class Convention
    {
        public int ID { get; set; }

        public string Name { get; set; }

        public DateTime StartDate { get; set; }

        public DateTime EndDate { get; set; }

        public string Description { get; set; }

        public string Address { get; set; }

        public int ZipCode { get; set; }

        public string State { get; set; }

        public string City { get; set; }
    }
}
