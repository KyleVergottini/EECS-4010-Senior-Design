// <auto-generated />
// This file was generated by a T4 template.
// Don't change it directly as your change would get overwritten.  Instead, make changes
// to the .tt file (i.e. the T4 template) and save it to regenerate this file.

// Make sure the compiler doesn't complain about missing Xml comments and CLS compliance
// 0108: suppress "Foo hides inherited member Foo. Use the new keyword if hiding was intended." when a controller and its abstract parent are both processed
// 0114: suppress "Foo.BarController.Baz()' hides inherited member 'Qux.BarController.Baz()'. To make the current member override that implementation, add the override keyword. Otherwise add the new keyword." when an action (with an argument) overrides an action in a parent controller
#pragma warning disable 1591, 3008, 3009, 0108, 0114
#region T4MVC

using System;
using System.Diagnostics;
using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;
using System.Web;
using System.Web.Hosting;
using System.Web.Mvc;
using System.Web.Mvc.Ajax;
using System.Web.Mvc.Html;
using System.Web.Routing;
using T4MVC;
namespace WebUI.Controllers
{
    public partial class ConventionController
    {
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        protected ConventionController(Dummy d) { }

        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        protected RedirectToRouteResult RedirectToAction(ActionResult result)
        {
            var callInfo = result.GetT4MVCResult();
            return RedirectToRoute(callInfo.RouteValueDictionary);
        }

        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        protected RedirectToRouteResult RedirectToAction(Task<ActionResult> taskResult)
        {
            return RedirectToAction(taskResult.Result);
        }

        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        protected RedirectToRouteResult RedirectToActionPermanent(ActionResult result)
        {
            var callInfo = result.GetT4MVCResult();
            return RedirectToRoutePermanent(callInfo.RouteValueDictionary);
        }

        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        protected RedirectToRouteResult RedirectToActionPermanent(Task<ActionResult> taskResult)
        {
            return RedirectToActionPermanent(taskResult.Result);
        }

        [NonAction]
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public virtual System.Web.Mvc.ActionResult Edit()
        {
            return new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.Edit);
        }
        [NonAction]
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public virtual System.Web.Mvc.ActionResult EditEvent()
        {
            return new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.EditEvent);
        }
        [NonAction]
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public virtual System.Web.Mvc.ActionResult FloorMap()
        {
            return new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.FloorMap);
        }
        [NonAction]
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public virtual System.Web.Mvc.ActionResult Rooms()
        {
            return new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.Rooms);
        }

        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public ConventionController Actions { get { return MVC.Convention; } }
        [GeneratedCode("T4MVC", "2.0")]
        public readonly string Area = "";
        [GeneratedCode("T4MVC", "2.0")]
        public readonly string Name = "Convention";
        [GeneratedCode("T4MVC", "2.0")]
        public const string NameConst = "Convention";
        [GeneratedCode("T4MVC", "2.0")]
        static readonly ActionNamesClass s_actions = new ActionNamesClass();
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public ActionNamesClass ActionNames { get { return s_actions; } }
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public class ActionNamesClass
        {
            public readonly string Create = "Create";
            public readonly string ConList = "ConList";
            public readonly string GetConList = "GetConList";
            public readonly string Edit = "Edit";
            public readonly string EventList = "EventList";
            public readonly string GetEventList = "GetEventList";
            public readonly string EditEvent = "EditEvent";
            public readonly string Map = "Map";
            public readonly string FloorMap = "FloorMap";
            public readonly string Rooms = "Rooms";
        }

        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public class ActionNameConstants
        {
            public const string Create = "Create";
            public const string ConList = "ConList";
            public const string GetConList = "GetConList";
            public const string Edit = "Edit";
            public const string EventList = "EventList";
            public const string GetEventList = "GetEventList";
            public const string EditEvent = "EditEvent";
            public const string Map = "Map";
            public const string FloorMap = "FloorMap";
            public const string Rooms = "Rooms";
        }


        static readonly ActionParamsClass_Create s_params_Create = new ActionParamsClass_Create();
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public ActionParamsClass_Create CreateParams { get { return s_params_Create; } }
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public class ActionParamsClass_Create
        {
            public readonly string model = "model";
        }
        static readonly ActionParamsClass_Edit s_params_Edit = new ActionParamsClass_Edit();
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public ActionParamsClass_Edit EditParams { get { return s_params_Edit; } }
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public class ActionParamsClass_Edit
        {
            public readonly string id = "id";
            public readonly string model = "model";
        }
        static readonly ActionParamsClass_EditEvent s_params_EditEvent = new ActionParamsClass_EditEvent();
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public ActionParamsClass_EditEvent EditEventParams { get { return s_params_EditEvent; } }
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public class ActionParamsClass_EditEvent
        {
            public readonly string id = "id";
            public readonly string model = "model";
        }
        static readonly ActionParamsClass_Map s_params_Map = new ActionParamsClass_Map();
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public ActionParamsClass_Map MapParams { get { return s_params_Map; } }
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public class ActionParamsClass_Map
        {
            public readonly string file = "file";
        }
        static readonly ActionParamsClass_FloorMap s_params_FloorMap = new ActionParamsClass_FloorMap();
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public ActionParamsClass_FloorMap FloorMapParams { get { return s_params_FloorMap; } }
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public class ActionParamsClass_FloorMap
        {
            public readonly string floorNumber = "floorNumber";
        }
        static readonly ActionParamsClass_Rooms s_params_Rooms = new ActionParamsClass_Rooms();
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public ActionParamsClass_Rooms RoomsParams { get { return s_params_Rooms; } }
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public class ActionParamsClass_Rooms
        {
            public readonly string floorNumber = "floorNumber";
            public readonly string rooms = "rooms";
        }
        static readonly ViewsClass s_views = new ViewsClass();
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public ViewsClass Views { get { return s_views; } }
        [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
        public class ViewsClass
        {
            static readonly _ViewNamesClass s_ViewNames = new _ViewNamesClass();
            public _ViewNamesClass ViewNames { get { return s_ViewNames; } }
            public class _ViewNamesClass
            {
                public readonly string ConList = "ConList";
                public readonly string Create = "Create";
                public readonly string Edit = "Edit";
                public readonly string Event = "Event";
                public readonly string EventList = "EventList";
                public readonly string Map = "Map";
                public readonly string Rooms = "Rooms";
            }
            public readonly string ConList = "~/Views/Convention/ConList.cshtml";
            public readonly string Create = "~/Views/Convention/Create.cshtml";
            public readonly string Edit = "~/Views/Convention/Edit.cshtml";
            public readonly string Event = "~/Views/Convention/Event.cshtml";
            public readonly string EventList = "~/Views/Convention/EventList.cshtml";
            public readonly string Map = "~/Views/Convention/Map.cshtml";
            public readonly string Rooms = "~/Views/Convention/Rooms.cshtml";
        }
    }

    [GeneratedCode("T4MVC", "2.0"), DebuggerNonUserCode]
    public partial class T4MVC_ConventionController : WebUI.Controllers.ConventionController
    {
        public T4MVC_ConventionController() : base(Dummy.Instance) { }

        [NonAction]
        partial void CreateOverride(T4MVC_System_Web_Mvc_ActionResult callInfo);

        [NonAction]
        public override System.Web.Mvc.ActionResult Create()
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.Create);
            CreateOverride(callInfo);
            return callInfo;
        }

        [NonAction]
        partial void CreateOverride(T4MVC_System_Web_Mvc_ActionResult callInfo, WebUI.Models.ConventionViewModel model);

        [NonAction]
        public override System.Web.Mvc.ActionResult Create(WebUI.Models.ConventionViewModel model)
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.Create);
            ModelUnbinderHelpers.AddRouteValues(callInfo.RouteValueDictionary, "model", model);
            CreateOverride(callInfo, model);
            return callInfo;
        }

        [NonAction]
        partial void ConListOverride(T4MVC_System_Web_Mvc_ActionResult callInfo);

        [NonAction]
        public override System.Web.Mvc.ActionResult ConList()
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.ConList);
            ConListOverride(callInfo);
            return callInfo;
        }

        [NonAction]
        partial void GetConListOverride(T4MVC_System_Web_Mvc_JsonResult callInfo);

        [NonAction]
        public override System.Web.Mvc.JsonResult GetConList()
        {
            var callInfo = new T4MVC_System_Web_Mvc_JsonResult(Area, Name, ActionNames.GetConList);
            GetConListOverride(callInfo);
            return callInfo;
        }

        [NonAction]
        partial void EditOverride(T4MVC_System_Web_Mvc_ActionResult callInfo, int id);

        [NonAction]
        public override System.Web.Mvc.ActionResult Edit(int id)
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.Edit);
            ModelUnbinderHelpers.AddRouteValues(callInfo.RouteValueDictionary, "id", id);
            EditOverride(callInfo, id);
            return callInfo;
        }

        [NonAction]
        partial void EditOverride(T4MVC_System_Web_Mvc_ActionResult callInfo, WebUI.Models.ConventionViewModel model);

        [NonAction]
        public override System.Web.Mvc.ActionResult Edit(WebUI.Models.ConventionViewModel model)
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.Edit);
            ModelUnbinderHelpers.AddRouteValues(callInfo.RouteValueDictionary, "model", model);
            EditOverride(callInfo, model);
            return callInfo;
        }

        [NonAction]
        partial void EventListOverride(T4MVC_System_Web_Mvc_ActionResult callInfo);

        [NonAction]
        public override System.Web.Mvc.ActionResult EventList()
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.EventList);
            EventListOverride(callInfo);
            return callInfo;
        }

        [NonAction]
        partial void GetEventListOverride(T4MVC_System_Web_Mvc_JsonResult callInfo);

        [NonAction]
        public override System.Web.Mvc.JsonResult GetEventList()
        {
            var callInfo = new T4MVC_System_Web_Mvc_JsonResult(Area, Name, ActionNames.GetEventList);
            GetEventListOverride(callInfo);
            return callInfo;
        }

        [NonAction]
        partial void EditEventOverride(T4MVC_System_Web_Mvc_ActionResult callInfo, int id);

        [NonAction]
        public override System.Web.Mvc.ActionResult EditEvent(int id)
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.EditEvent);
            ModelUnbinderHelpers.AddRouteValues(callInfo.RouteValueDictionary, "id", id);
            EditEventOverride(callInfo, id);
            return callInfo;
        }

        [NonAction]
        partial void EditEventOverride(T4MVC_System_Web_Mvc_ActionResult callInfo, WebUI.Models.EventViewModel model);

        [NonAction]
        public override System.Web.Mvc.ActionResult EditEvent(WebUI.Models.EventViewModel model)
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.EditEvent);
            ModelUnbinderHelpers.AddRouteValues(callInfo.RouteValueDictionary, "model", model);
            EditEventOverride(callInfo, model);
            return callInfo;
        }

        [NonAction]
        partial void MapOverride(T4MVC_System_Web_Mvc_ActionResult callInfo);

        [NonAction]
        public override System.Web.Mvc.ActionResult Map()
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.Map);
            MapOverride(callInfo);
            return callInfo;
        }

        [NonAction]
        partial void MapOverride(T4MVC_System_Web_Mvc_ActionResult callInfo, System.Web.HttpPostedFileBase file);

        [NonAction]
        public override System.Web.Mvc.ActionResult Map(System.Web.HttpPostedFileBase file)
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.Map);
            ModelUnbinderHelpers.AddRouteValues(callInfo.RouteValueDictionary, "file", file);
            MapOverride(callInfo, file);
            return callInfo;
        }

        [NonAction]
        partial void FloorMapOverride(T4MVC_System_Web_Mvc_ActionResult callInfo, int floorNumber);

        [NonAction]
        public override System.Web.Mvc.ActionResult FloorMap(int floorNumber)
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.FloorMap);
            ModelUnbinderHelpers.AddRouteValues(callInfo.RouteValueDictionary, "floorNumber", floorNumber);
            FloorMapOverride(callInfo, floorNumber);
            return callInfo;
        }

        [NonAction]
        partial void RoomsOverride(T4MVC_System_Web_Mvc_ActionResult callInfo, int? floorNumber);

        [NonAction]
        public override System.Web.Mvc.ActionResult Rooms(int? floorNumber)
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.Rooms);
            ModelUnbinderHelpers.AddRouteValues(callInfo.RouteValueDictionary, "floorNumber", floorNumber);
            RoomsOverride(callInfo, floorNumber);
            return callInfo;
        }

        [NonAction]
        partial void RoomsOverride(T4MVC_System_Web_Mvc_ActionResult callInfo, int floorNumber, System.Collections.Generic.List<BusinessObjects.Room> rooms);

        [NonAction]
        public override System.Web.Mvc.ActionResult Rooms(int floorNumber, System.Collections.Generic.List<BusinessObjects.Room> rooms)
        {
            var callInfo = new T4MVC_System_Web_Mvc_ActionResult(Area, Name, ActionNames.Rooms);
            ModelUnbinderHelpers.AddRouteValues(callInfo.RouteValueDictionary, "floorNumber", floorNumber);
            ModelUnbinderHelpers.AddRouteValues(callInfo.RouteValueDictionary, "rooms", rooms);
            RoomsOverride(callInfo, floorNumber, rooms);
            return callInfo;
        }

    }
}

#endregion T4MVC
#pragma warning restore 1591, 3008, 3009, 0108, 0114