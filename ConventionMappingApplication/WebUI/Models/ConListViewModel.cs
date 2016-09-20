using BusinessObjects;
using System.Collections.Generic;

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