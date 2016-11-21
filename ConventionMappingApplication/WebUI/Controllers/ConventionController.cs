using System;
using System.Collections.Generic;
using Services;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using BusinessObjects;
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
            return View(MVC.Convention.Views.ConList);
        }

        [HttpGet]
        public virtual JsonResult GetConList()
        {
            var cons = _conventionService.GetAllConventions();
            var conventions = cons.Select(con => _conventionTransformer.TransformToViewModel(con)).ToList();
            return Json(conventions, JsonRequestBehavior.AllowGet); 
        }

        [HttpGet]
        public virtual ActionResult Edit(int id)
        {
            var con = _conventionService.GetConventionById(id);
            Session["conventionId"] = con.ID;
            var model = _conventionTransformer.TransformToViewModel(con);

            var maps = _conventionService.GetMapByConventionId(con.ID);

            var totalMaps = 0;
            if (maps.Floor1 != null)
            {
                totalMaps++;
            }
            if (maps.Floor2 != null)
            {
                totalMaps++;
            }
            if (maps.Floor3 != null)
            {
                totalMaps++;
            }

            model.TotalMaps = totalMaps;
            return View(MVC.Convention.Views.Edit, model);
        }

        [HttpPost]
        public virtual ActionResult Edit(ConventionViewModel model)
        {
            var convention = _conventionTransformer.TransformToBusinessObject(model);
            Session["conventionId"] = _conventionService.SaveConvention(convention);
            return RedirectToAction(MVC.Convention.ConList());
        }

        [HttpGet]
        public virtual ActionResult EventList()
        {
            return View(MVC.Convention.Views.EventList);
        }

        [HttpGet]
        public virtual JsonResult GetEventList()
        {
            var eventList = _eventService.GetAllEventsForAConvention((int)Session["conventionId"]);
            var events = eventList.Select(e => _eventTransformer.TrasformToViewModel(e)).ToList();
            return Json(events, JsonRequestBehavior.AllowGet); 
        }

        [HttpGet]
        public virtual ActionResult EditEvent(int id)
        {
            var model = new EventViewModel{ ID = id };
            
            var conEvent = _eventService.GetEventById(id);
            if (conEvent != null)
            {
                model = _eventTransformer.TrasformToViewModel(conEvent);
            }

            model.Rooms = _roomService.GetRoomsForGivenConventionId((int)Session["conventionId"])
                .Select(x => new SelectListItem
                {
                    Text = x.Name,
                    Value = x.ID.ToString()
                });

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
            byte[] map = new byte[file.InputStream.Length];
            file.InputStream.Read(map, 0, map.Length);

            _conventionService.SaveMap((int) Session["conventionId"], map);
            return Json(true);
        }

        [HttpGet]
        public virtual ActionResult FloorMap(int floorNumber)
        {
            var allFloors = _conventionService.GetMapByConventionId((int) Session["conventionId"]);
            byte[] map;
            switch (floorNumber)
            {
                case 2:
                    map = allFloors.Floor2;
                    break;
                case 3:
                    map = allFloors.Floor3;
                    break;
                default:
                    map = allFloors.Floor1;
                    break;
            }
            return File(map, "image/jpeg");
        }

        [HttpGet]
        public virtual ActionResult Rooms(int? floorNumber)
        {
            if (floorNumber == null)
            {
                floorNumber = 1;
            }

            int totalFloors = 0;
            var allFloors = _conventionService.GetMapByConventionId((int) Session["conventionId"]);
            if (allFloors.Floor1 != null)
            {
                totalFloors++;
            }
            if (allFloors.Floor2 != null)
            {
                totalFloors++;
            }
            if (allFloors.Floor3 != null)
            {
                totalFloors++;
            }

            var model = new RoomViewModel
            {
                FloorNumber = (int)floorNumber,
                TotalFloors = totalFloors
            };
            return View(model);
        }

        [HttpPost]
        public virtual ActionResult Rooms(int floorNumber, List<Room> rooms)
        {
            _roomService.SaveRooms((int) Session["conventionId"], floorNumber, rooms);
            return Json(true);
        }
    }
}