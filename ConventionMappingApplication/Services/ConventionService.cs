using System.Collections.Generic;
using BusinessLogic.Conventions;
using BusinessObjects;

namespace Services
{
    public class ConventionService : IConventionService
    {
        private readonly IGetConventionByIdComponent _getConventionByIdComponent;
        private readonly ISaveConventionComponent _saveConventionComponent;
        private readonly IGetAllConventionsComponent _getAllConventionsComponent;

        public ConventionService(IGetConventionByIdComponent getConventionByIdComponent, ISaveConventionComponent saveConventionComponent, IGetAllConventionsComponent getAllConventionsComponent)
        {
            _getConventionByIdComponent = getConventionByIdComponent;
            _saveConventionComponent = saveConventionComponent;
            _getAllConventionsComponent = getAllConventionsComponent;
        }

        public ConventionService(IGetAllConventionsComponent getAllConventionsComponent)
        {
            _getAllConventionsComponent = getAllConventionsComponent;
        }

        public Convention GetConventionById(int id)
        {
            return _getConventionByIdComponent.Execute(id);
        }

        public int SaveConvention(Convention convention)
        {
            return _saveConventionComponent.Execute(convention);
        }

        public List<Convention> GetAllConventions()
        {
            return _getAllConventionsComponent.Execute();
        }
    }
}
