using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace WebUI.Models
{
    public class EventViewModel
    {
        public int? ID { get; set; }
        public int? RoomID { get; set; }

        [Required, DisplayName("Name*")]
        public string Name { get; set; }

        [Required, DisplayName("Start Date*")]
        public string StartDate { get; set; }

        [Required, DisplayName("End Date*")]
        public string EndDate { get; set; }

        [DisplayName("Description")]
        public string Description { get; set; }
    }
}