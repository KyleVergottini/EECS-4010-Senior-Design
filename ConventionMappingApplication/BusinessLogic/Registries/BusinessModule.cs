using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
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
