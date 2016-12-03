using System;
using BusinessObjects;
using WebUI.Models;

namespace WebUI.Transformers
{
    public class EventTransformer : IEventTransformer
    {
        public EventViewModel TrasformToViewModel(Event conEvent)
        {
            return new EventViewModel
            {
                ID = conEvent.ID,
                RoomID = conEvent.RoomID,
                Name = conEvent.Name,
                Description = conEvent.Description,
                Date = conEvent.StartDate.ToString("d"),
                StartTime = conEvent.StartDate,
                EndTime = conEvent.EndDate,
                STime = conEvent.StartDate.ToString("hh:mm tt"),
                ETime = conEvent.EndDate.ToString("hh:mm tt")
            };
        }

        public Event TrasformToBusinessObject(EventViewModel viewModel)
        {
            var startDate = Convert.ToDateTime(viewModel.Date) + viewModel.StartTime.TimeOfDay;
            var endDate = Convert.ToDateTime(viewModel.Date) + viewModel.EndTime.TimeOfDay;

            return new Event
            {
                ID = viewModel.ID,
                RoomID = viewModel.RoomID,
                Name = viewModel.Name,
                Description = viewModel.Description,
                StartDate = startDate,
                EndDate = endDate
            };
        }
    }
}