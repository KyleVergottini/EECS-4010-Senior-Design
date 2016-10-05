using BusinessObjects;
using WebUI.Models;

namespace WebUI.Transformers
{
    public interface IEventTransformer
    {
        EventViewModel TrasformToViewModel(Event conEvent);

        Event TrasformToBusinessObject(EventViewModel viewModel);
    }
}
