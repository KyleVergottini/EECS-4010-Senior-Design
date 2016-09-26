namespace DataAccess
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("ConventionRole")]
    public partial class ConventionRole
    {
        public int ID { get; set; }

        public int UserID { get; set; }

        public int ConventionID { get; set; }

        [Required]
        [StringLength(25)]
        public string Role { get; set; }

        public virtual Convention Convention { get; set; }

        public virtual User User { get; set; }
    }
}
