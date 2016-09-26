using BusinessObjects;
using WebUI.Models;

namespace WebUI.Transformers
{
    public class EventToEventViewModelTransformer : IEventToEventViewModelTransformer
    {
        public EventViewModel Trasform(Event conEvent)
        {
            return new EventViewModel
            {
                ID = conEvent.ID,
                RoomID = conEvent.RoomID, //--TODO: change this
                Name = conEvent.Name,
                Description = conEvent.Description,
                EndDate = conEvent.EndDate,
                StartDate = conEvent.StartDate
            };
        }
    }
}