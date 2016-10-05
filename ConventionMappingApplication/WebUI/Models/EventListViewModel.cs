using System.Collections.Generic;
using BusinessObjects;

namespace WebUI.Models
{
    public class EventListViewModel
    {
        public EventListViewModel()
        {
            Events = new List<Event>();
        }

        public List<Event> Events { get; set; }
    }
}