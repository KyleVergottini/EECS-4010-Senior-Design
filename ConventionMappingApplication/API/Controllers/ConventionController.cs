using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using BusinessLogic;
using BusinessLogic.Services;
using BusinessLogic.BusinessObjects;

namespace API.Controllers
{
    public class ConventionController : ApiController
    {
        private DatabaseReadService _DatabaseReadService;

        public ConventionController()
        {
            _DatabaseReadService = new DatabaseReadService();
        }

        [HttpGet]
        [Route("Convention/GetByID/{ID}")]
        public IHttpActionResult GetConventionByID(int ID)
        {
            IList<ConventionRecord> result = _DatabaseReadService.GetConventions(c => c.ID == ID);
            if (result.Count == 0)
            {
                return BadRequest("No convention found for this ID");
            }
            return Ok(result.First<ConventionRecord>());
        }

        [HttpGet]
        [Route("Convention/Find/")]
        public IHttpActionResult FindConventions()
        // -- TODO
        // -- Should have optional parameters
        {
            // -- TODO
            // Query database for multiple Convention records
            IList<ConventionRecord> result = _DatabaseReadService.GetConventions(c => 0 == 0);
            return Ok(result);
        }
    }
}