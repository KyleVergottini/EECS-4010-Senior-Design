using System.Reflection;
using Autofac;
using Module = Autofac.Module;

namespace BusinessLogic.Registries
{
    public class BusinessModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            var businessLogic = Assembly.GetExecutingAssembly();

            builder.RegisterAssemblyTypes(businessLogic)
                .AsImplementedInterfaces()
                .PropertiesAutowired();
        }
    }
}
