using Services;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebUI.Models;
using WebUI.Transformers;

namespace WebUI.Controllers
{
    public partial class ConventionController : Controller
    {
        private readonly IEventService _eventService;
        private readonly IConventionService _conventionService;
        private readonly IRoomService _roomService;
        private readonly IEventTransformer _eventTransformer;
        private readonly IConventionTransformer _conventionTransformer;

        public ConventionController(IEventService eventService, IEventTransformer eventTransformer, IConventionService conventionService, IConventionTransformer conventionTransformer, IRoomService roomService)
        {
            _eventService = eventService;
            _eventTransformer = eventTransformer;
            _conventionService = conventionService;
            _conventionTransformer = conventionTransformer;
            _roomService = roomService;
        }

        [HttpGet]
        public virtual ActionResult Create()
        {
            var model = new ConventionViewModel
            {
                ID = 0
            };
            return View(model);
        }

        [HttpPost]
        public virtual ActionResult Create(ConventionViewModel model)
        {
            if (!ModelState.IsValid)
            {
                return View(model);
            }
            var convention = _conventionTransformer.TransformToBusinessObject(model);
            Session["conventionId"] = _conventionService.SaveConvention(convention);
            return RedirectToAction(MVC.Convention.Map());
        }

        [HttpGet]
        public virtual ActionResult ConList()
        {
            var model = new ConListViewModel
            {
                Conventions = _conventionService.GetAllConventions()
            };
            return View(MVC.Convention.Views.ConList, model);
        }

        [HttpGet]
        public virtual ActionResult Edit(int id)
        {
            var con = _conventionService.GetConventionById(id);
            var model = _conventionTransformer.TransformToViewModel(con);
            return View(MVC.Convention.Views.Edit, model);
        }

        [HttpPost]
        public virtual ActionResult Edit(ConventionViewModel model)
        {
            var convention = _conventionTransformer.TransformToBusinessObject(model);
            Session["conventionId"] = _conventionService.SaveConvention(convention);
            return RedirectToAction(MVC.Convention.Map());
        }

        [HttpGet]
        public virtual ActionResult EventList()
        {
            var model = new EventListViewModel
            {
                Events = _eventService.GetAllEventsForAConvention(1)
            };
            return View(MVC.Convention.Views.EventList, model);
        }

        [HttpGet]
        public virtual ActionResult EditEvent(int id)
        {
            var model = new EventViewModel{ ID = id };
            model.Rooms = _roomService.GetRoomsForGivenConventionId(1)
                .Select(x => new SelectListItem
                {
                    Text = x.Name,
                    Value = x.ID.ToString()
                });
            var conEvent = _eventService.GetEventById(id);
            if (conEvent != null)
            {
                model = _eventTransformer.TrasformToViewModel(conEvent);
            }
            return View(MVC.Convention.Views.Event, model);
        }

        [HttpPost]
        public virtual ActionResult EditEvent(EventViewModel model)
        {
            var conEvent = _eventTransformer.TrasformToBusinessObject(model);
            _eventService.SaveEvent(conEvent);
            return RedirectToAction(MVC.Convention.EditEvent(0));
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
            return RedirectToAction(MVC.Convention.EditEvent(0));
        }
    }
}