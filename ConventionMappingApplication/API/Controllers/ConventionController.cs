using System;
using System.Collections.Generic;
using System.Web;
using System.Web.Http;
using Services;
using BusinessLogic.Conventions;
using BusinessObjects;

namespace API.Controllers
{
    public class ConventionController : ApiController
    {
        private IConventionService _ConventionService;

        public ConventionController()
        {
            _ConventionService = new ConventionService(
                new GetConventionByIdComponent()
            );
        }

        [HttpGet]
        [Route("Convention/GetConventionById/{Id}")]
        public IHttpActionResult GetConventionById(int Id)
        {
            Convention result;
            try
            {
                result = _ConventionService.GetConventionById(Id);
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            if (result == null)
            {
                return BadRequest("No convention found for this Id");
            }
            return Ok(result);
        }

        [HttpGet]
        [Route("Convention/Find/")]
        public IHttpActionResult FindConventions()
        // -- TODO
        // -- Should have optional parameters
        {
            // -- TODO
            // Query database for multiple Convention records
            return Ok();
        }
    }
}