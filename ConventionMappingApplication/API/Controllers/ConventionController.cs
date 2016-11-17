﻿using System;
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
                new GetAllConventionsComponent()
            );
        }

        [HttpPost]
        [Route("Convention/GetAllConventions/")]
        public IHttpActionResult GetAllConventions()
        {
            List<Convention> result;
            try
            {
                result = _ConventionService.GetAllConventions();
            }
            catch (Exception e)
            {
                return BadRequest("An error has occurred");
            }
            if (result.Count == 0)
            {
                return BadRequest("No conventions found");
            }
            return Ok(result);
        }
    }
}