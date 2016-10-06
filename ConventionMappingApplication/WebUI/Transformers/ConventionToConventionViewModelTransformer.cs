using BusinessObjects;
using WebUI.Models;

namespace WebUI.Transformers
{
    public class ConventionToConventionViewModelTransformer : IConventionToConventionViewModelTransformer
    {
        public ConventionViewModel Transform(Convention convention)
        {
            return new ConventionViewModel
            {
                Name = convention.Name,
                Description = convention.Description,
                State = convention.State,
                City = convention.City,
                ZipCode = convention.ZipCode,
                EndDate = convention.EndDate,
                StartDate = convention.StartDate
            };
        }
    }
}