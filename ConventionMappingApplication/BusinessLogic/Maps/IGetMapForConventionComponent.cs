using BusinessObjects;

namespace BusinessLogic.Maps
{
    public interface IGetMapForConventionComponent
    {
        Map Execute(int conventionId);
    }
}
