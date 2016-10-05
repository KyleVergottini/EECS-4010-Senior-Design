using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Web.Mvc;

namespace WebUI.Models
{
    public class EventViewModel
    {
        public EventViewModel()
        {
            Rooms = new List<SelectListItem>();
        }

        public int ID { get; set; }
        public int RoomID { get; set; }

        [Required, DisplayName("Name*")]
        public string Name { get; set; }

        [Required, DisplayName("Start Date*")]
        public string StartDate { get; set; }

        [Required, DisplayName("End Date*")]
        public string EndDate { get; set; }

        [DisplayName("Description")]
        public string Description { get; set; }

        [DisplayName("Location*")]
        public IEnumerable<SelectListItem> Rooms { get; set; }
    }
}