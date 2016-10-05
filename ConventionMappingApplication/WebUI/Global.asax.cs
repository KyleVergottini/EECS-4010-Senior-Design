using System.Reflection;
using System.Web;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;
using Autofac;
using Autofac.Integration.Mvc;
using Services.Registries;
using WebUI.Transformers;

namespace WebUI
{
    public class MvcApplication : HttpApplication
    {
        protected void Application_Start()
        {
            AreaRegistration.RegisterAllAreas();
            FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);
            BundleConfig.RegisterBundles(BundleTable.Bundles);
            this.WireAutofacMVC();
        }

        private void WireAutofacMVC()
        {
            var builder = new ContainerBuilder();
            builder.RegisterControllers(typeof(MvcApplication).Assembly)
                .InstancePerHttpRequest();
            builder.RegisterModule<ServiceModule>();
            builder.RegisterType<EventTransformer>()
                .AsImplementedInterfaces()
                .InstancePerRequest();
            builder.RegisterType<ConventionTransformer>()
                .AsImplementedInterfaces()
                .InstancePerRequest();
            
            DependencyResolver.SetResolver(new AutofacDependencyResolver(builder.Build()));
        }
    }
}
