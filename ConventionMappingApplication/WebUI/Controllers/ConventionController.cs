using System.Web;
using System.Web.Mvc;
using Services;
using WebUI.Models;

namespace WebUI.Controllers
{
    public partial class ConventionController : Controller
    {
        private IEventService _eventService;

        public ConventionController(IEventService eventService)
        {
            _eventService = eventService;
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
        public virtual ActionResult Edit()
        {
            var model = new ConventionViewModel
            {
                Name = "Test Convetion"
            };
            return View(MVC.Convention.Views.Edit, model);
        }

        [HttpPost]
        public virtual ActionResult Edit(ConventionViewModel model)
        {
            return View();
        }

        [HttpGet]
        public virtual ActionResult EditEvent()
        {
            var model = new EventViewModel();
            //var conEvent = _eventService.GetEventsForRoom(roomID);
            //if(conEvent != null)
            //{
            //  TODO: Set Model Fields Here
            //}
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