using System;
using System.Web;
using System.Web.Http;
using System.Drawing;
using Services;
using API.Models;
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

        [HttpPost]
        [Route("Map/GetMapForConvention/")]
        public IHttpActionResult GetMapForConvention(ConventionId post)
        {
            Map result;
            try
            {
                result = _MapService.GetMapForConvention(post.conventionid);
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            return Ok(result);
        }
    }
}