using BusinessLogic.BusinessObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebUI.Models
{
    public class ConListViewModel
    {
        public ConListViewModel()
        {
            Conventions = new List<Convention>();
        }

        public List<Convention> Conventions { get; set; }
    }
}