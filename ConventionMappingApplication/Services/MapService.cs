using BusinessLogic.Maps;
using BusinessObjects;

namespace Services
{
    public class MapService : IMapService
    {
        private readonly IGetMapForConventionComponent _getMapForConventionComponent;

        public MapService(IGetMapForConventionComponent getMapForConventionComponent)
        {
            _getMapForConventionComponent = getMapForConventionComponent;
        }

        public Map GetMapForConvention(int conventionId)
        {
            return _getMapForConventionComponent.Execute(conventionId);
        }
    }
}
