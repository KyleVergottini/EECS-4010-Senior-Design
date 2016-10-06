using BusinessObjects;

namespace BusinessLogic.Events
{
    public interface IGetEventByIdComponent
    {
        Event Execute(int eventId);
    }
}
