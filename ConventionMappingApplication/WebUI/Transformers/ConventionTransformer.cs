using System;
using BusinessObjects;
using WebUI.Models;

namespace WebUI.Transformers
{
    public class ConventionTransformer : IConventionTransformer
    {
        public ConventionViewModel TransformToViewModel(Convention convention)
        {
            return new ConventionViewModel
            {
                ID = convention.ID,
                Name = convention.Name,
                Address = convention.Address,
                Description = convention.Description,
                State = convention.State,
                City = convention.City,
                ZipCode = convention.ZipCode,
                EndDate = convention.EndDate.ToString("d"),
                StartDate = convention.StartDate.ToString("d")
            };
        }

        public Convention TransformToBusinessObject(ConventionViewModel viewModel)
        {
            return new Convention
            {
                ID = viewModel.ID,
                Name = viewModel.Name,
                Address = viewModel.Address,
                StartDate = Convert.ToDateTime(viewModel.StartDate),
                EndDate = Convert.ToDateTime(viewModel.EndDate),
                City = viewModel.City,
                Description = viewModel.Description,
                State = viewModel.State,
                ZipCode = viewModel.ZipCode
            };
        }
    }
}