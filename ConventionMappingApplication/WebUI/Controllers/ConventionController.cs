using System.Web;
using System.Web.Mvc;
using Services;
using WebUI.Models;
using WebUI.Transformers;

namespace WebUI.Controllers
{
    public partial class ConventionController : Controller
    {
        private readonly IEventService _eventService;
        private readonly IConventionService _conventionService;
        private readonly IEventToEventViewModelTransformer _eventTransformer;
        private readonly IConventionToConventionViewModelTransformer _conTransformer;

        public ConventionController(IEventService eventService, IEventToEventViewModelTransformer eventTransformer, IConventionService conventionService, IConventionToConventionViewModelTransformer conTransformer)
        {
            _eventService = eventService;
            _eventTransformer = eventTransformer;
            _conventionService = conventionService;
            _conTransformer = conTransformer;
        }

        [HttpGet]
        public virtual ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        public virtual ActionResult Create(ConventionViewModel model)
        {
            if (!ModelState.IsValid)
            {
                return View(model);
            }
            return RedirectToAction(MVC.Convention.Map());
        }

        [HttpGet]
        public virtual ActionResult GetConList()
        {
            var model = new ConListViewModel();
            return View(MVC.Convention.Views.ConList, model);
        }

        [HttpGet]
        public virtual ActionResult Edit(int id)
        {
            var con = _conventionService.GetConventionById(id);
            var model = _conTransformer.Transform(con);
            return View(MVC.Convention.Views.Edit, model);
        }

        [HttpPost]
        public virtual ActionResult Edit(ConventionViewModel model)
        {
            //--TODO: add save
            return View();
        }

        [HttpGet]
        public virtual ActionResult EditEvent()
        {
            var model = new EventViewModel();
            var conEvent = _eventService.GetEventById(0);
            if(conEvent != null)
            {
                model = _eventTransformer.Trasform(conEvent);
            }
            return View(MVC.Convention.Views.Event, model);
        }

        [HttpPost]
        public virtual ActionResult EditEvent(ConventionViewModel model)
        {
            return View(MVC.Convention.Views.ConList);
        }

        [HttpGet]
        public virtual ActionResult Map()
        {
            return View();
        }

        [HttpPost]
        public virtual ActionResult Map(HttpPostedFileBase file)
        {
            //--can get images from Request.Files
            return View();
        }
    }
}