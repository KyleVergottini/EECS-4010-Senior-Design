using BusinessObjects;

namespace BusinessLogic.Conventions
{
    public interface IGetConventionByIdComponent
    {
        Convention Execute(int id);
    }
}
