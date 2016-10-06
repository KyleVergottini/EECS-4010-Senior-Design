namespace DataAccess
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Room
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Room()
        {
            Events = new HashSet<Event>();
        }

        public int ID { get; set; }

        public int ConventionID { get; set; }

        [Required]
        [StringLength(256)]
        public string Name { get; set; }

        public int Level { get; set; }

        public int XCoordinate { get; set; }

        public int YCoordinate { get; set; }

        public virtual Convention Convention { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Event> Events { get; set; }
    }
}
