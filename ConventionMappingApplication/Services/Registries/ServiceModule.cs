using System.Reflection;
using Autofac;
using BusinessLogic.Registries;
using Module = Autofac.Module;

namespace Services.Registries
{
    public class ServiceModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            var assemby = Assembly.GetExecutingAssembly();

            builder.RegisterAssemblyTypes(assemby)
                .AsImplementedInterfaces()
                .SingleInstance();

            builder.RegisterModule<BusinessModule>();
        }
    }
}
