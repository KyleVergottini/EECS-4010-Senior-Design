using BusinessObjects;
using WebUI.Models;

namespace WebUI.Transformers
{
    public interface IEventToEventViewModelTransformer
    {
        EventViewModel Trasform(Event conEvent);
    }
}
