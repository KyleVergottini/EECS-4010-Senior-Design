namespace DataAccess
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class UserRecovery
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public UserRecovery()
        {
        }

        [Required]
        [Key]
        public int UserID { get; set; }

        [Required]
        [StringLength(256)]
        public string HashedRecoveryCode { get; set; }

        [Required]
        public DateTime ExpirationDate { get; set; }

        public virtual User User { get; set; }
    }
}
