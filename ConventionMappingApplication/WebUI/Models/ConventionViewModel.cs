using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace WebUI.Models
{
    public class ConventionViewModel
    {
        public int ID { get; set; }

        public int TotalMaps { get; set; }

        [Required, DisplayName("Name*")]
        public string Name { get; set; }

        [Required, DisplayName("Start Date*")]
        public string StartDate { get; set; }

        [Required, DisplayName("End Date*")]
        public string EndDate { get; set; }

        [DisplayName("Description")]
        public string Description { get; set; }

        [Required, DisplayName("Address*")]
        public string Address { get; set; }

        [Required, DisplayName("ZipCode*")]
        public int ZipCode { get; set; }

        [Required, DisplayName("State*")]
        public string State { get; set; }

        [Required, DisplayName("City*")]
        public string City { get; set; }
    }
}