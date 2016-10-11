using System.Collections.Generic;

namespace WebUI.Models
{
    public class EventListViewModel
    {
        public EventListViewModel()
        {
            Events = new List<EventViewModel>();
        }

        public List<EventViewModel> Events { get; set; }
    }
}