namespace DataAccess
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Schedule
    {
        public int ID { get; set; }

        public int UserID { get; set; }

        public int ConventionID { get; set; }

        public int EventID { get; set; }

        public virtual Convention Convention { get; set; }

        public virtual Event Event { get; set; }

        public virtual User User { get; set; }
    }
}
