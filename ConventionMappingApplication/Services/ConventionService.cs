using BusinessLogic.Conventions;
using BusinessObjects;

namespace Services
{
    public class ConventionService : IConventionService
    {
        private readonly IGetConventionByIdComponent _getConventionByIdComponent;

        public ConventionService(IGetConventionByIdComponent getConventionByIdComponent)
        {
            _getConventionByIdComponent = getConventionByIdComponent;
        }

        public Convention GetConventionById(int id)
        {
            return _getConventionByIdComponent.Execute(id);
        }
    }
}
