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
        private readonly ISaveConMapComponent _saveConMapComponent;
        private readonly IGetMapByConventionIdComponent _getMapByConventionIdComponent;

        public ConventionService(IGetConventionByIdComponent getConventionByIdComponent, ISaveConventionComponent saveConventionComponent, IGetAllConventionsComponent getAllConventionsComponent, ISaveConMapComponent saveConMapComponent, IGetMapByConventionIdComponent getMapByConventionIdComponent)
        {
            _getConventionByIdComponent = getConventionByIdComponent;
            _saveConventionComponent = saveConventionComponent;
            _getAllConventionsComponent = getAllConventionsComponent;
            _saveConMapComponent = saveConMapComponent;
            _getMapByConventionIdComponent = getMapByConventionIdComponent;
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

        public bool SaveMap(int conId, byte[] map)
        {
            return _saveConMapComponent.Execute(conId, map);
        }

        public ConMap GetMapByConventionId(int conId)
        {
            return _getMapByConventionIdComponent.Execute(conId);
        }
    }
}
