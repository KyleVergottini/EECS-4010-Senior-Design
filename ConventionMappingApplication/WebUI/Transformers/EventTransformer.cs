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
                RoomID = conEvent.RoomID, //--TODO: change this
                Name = conEvent.Name,
                Description = conEvent.Description,
                EndDate = conEvent.EndDate.ToString("d"),
                StartDate = conEvent.StartDate.ToString("d")
            };
        }

        public Event TrasformToBusinessObject(EventViewModel viewModel)
        {
            return new Event
            {
                ID = viewModel.ID,
                RoomID = viewModel.RoomID, //--TODO: change this
                Name = viewModel.Name,
                Description = viewModel.Description,
                EndDate = Convert.ToDateTime(viewModel.EndDate),
                StartDate = Convert.ToDateTime(viewModel.StartDate)
            };
        }
    }
}