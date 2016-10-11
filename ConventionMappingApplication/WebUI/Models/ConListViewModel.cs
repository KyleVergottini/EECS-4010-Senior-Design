using BusinessObjects;
using System.Collections.Generic;

namespace WebUI.Models
{
    public class ConListViewModel
    {
        public ConListViewModel()
        {
            Conventions = new List<ConventionViewModel>();
        }

        public List<ConventionViewModel> Conventions { get; set; }
    }
}