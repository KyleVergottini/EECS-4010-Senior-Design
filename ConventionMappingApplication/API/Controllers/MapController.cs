using System;
using System.Web;
using System.Web.Http;
using System.Drawing;
using Services;
using BusinessLogic.Maps;
using Map = BusinessObjects.Map;

namespace API.Controllers
{
    public class MapController : ApiController
    {
        private IMapService _MapService;

        public MapController()
        {
            _MapService = new MapService(
                new GetMapForConventionComponent()
            );
        }

        [HttpGet]
        [Route("Map/GetMapForConvention/{conventionId}")]
        public IHttpActionResult GetMapForConvention(int conventionId)
        {
            Map result;
            try
            {
                result = _MapService.GetMapForConvention(conventionId);
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            return Ok(result);
        }
    }
}