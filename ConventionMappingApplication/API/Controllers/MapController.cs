using System;
using System.Collections.Generic;
using System.Web;
using System.Web.Http;
using Services;
using BusinessLogic.Maps;
using BusinessObjects;

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
        [Route("Convention/GetMapForConvention/{conventionId}")]
        public IHttpActionResult GetMapForConvention(int conventionId)
        {
            List<Map> result;
            try
            {
                result = _MapService.GetMapForConvention(conventionId);
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            if (result.Count == 0)
            {
                return BadRequest("No maps found for this convention Id");
            }
            return Ok(result);
        }
    }
}