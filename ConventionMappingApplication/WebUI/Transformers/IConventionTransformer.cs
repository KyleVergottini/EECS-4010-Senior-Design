using BusinessObjects;
using WebUI.Models;

namespace WebUI.Transformers
{
    public interface IConventionTransformer
    {
        ConventionViewModel TransformToViewModel(Convention convention);

        Convention TransformToBusinessObject(ConventionViewModel viewModel);
    }
}
