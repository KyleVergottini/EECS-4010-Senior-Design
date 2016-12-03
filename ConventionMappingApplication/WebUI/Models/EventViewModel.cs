using System;
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

        [Required, DisplayName("Date*")]
        public string Date { get; set; }

        [Required, DisplayName("Start Time*"), DisplayFormat(ApplyFormatInEditMode = true,
            DataFormatString = "{0:hh:mm tt}"), DataType(DataType.Time)]
        public DateTime StartTime { get; set; }
        public string STime { get; set; }

        [Required, DisplayName("End Time*"), DisplayFormat(ApplyFormatInEditMode = true,
            DataFormatString = "{0:hh:mm tt}"), DataType(DataType.Time)]
        public DateTime EndTime { get; set; }
        public string ETime { get; set; }

        [DisplayName("Description")]
        public string Description { get; set; }

        [DisplayName("Location*")]
        public IEnumerable<SelectListItem> Rooms { get; set; }
    }
}