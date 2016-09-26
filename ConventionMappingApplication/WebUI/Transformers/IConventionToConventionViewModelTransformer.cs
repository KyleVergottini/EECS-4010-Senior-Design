using BusinessObjects;
using WebUI.Models;

namespace WebUI.Transformers
{
    public interface IConventionToConventionViewModelTransformer
    {
        ConventionViewModel Transform(Convention convention);
    }
}
