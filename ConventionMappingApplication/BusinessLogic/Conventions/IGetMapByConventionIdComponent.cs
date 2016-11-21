using BusinessObjects;

namespace BusinessLogic.Conventions
{
    public interface IGetMapByConventionIdComponent
    {
        ConMap Execute(int conId);
    }
}
