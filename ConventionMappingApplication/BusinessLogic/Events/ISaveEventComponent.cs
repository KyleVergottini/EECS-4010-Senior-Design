using BusinessObjects;

namespace BusinessLogic.Events
{
    public interface ISaveEventComponent
    {
        bool Execute(Event conEvent);
    }
}
