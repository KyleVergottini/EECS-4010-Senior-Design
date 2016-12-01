using System;
using System.Web;
using System.Web.Mvc;
using Services;
using BusinessLogic.UserRecovery;
using API.Models;

namespace API.Controllers
{
    public partial class UserRecoveryController : Controller
    {
        private IUserRecoveryService _UserRecoveryService;

        public UserRecoveryController()
        {
            _UserRecoveryService = new UserRecoveryService(
                new ResetPasswordComponent()
            );
        }

        [HttpGet]
        public ActionResult RecoveryForm()
        {
            UserRecoveryModel model = new UserRecoveryModel();
            model.userRecoveryCode = this.Request.QueryString["RecoveryCode"];
            return View(model);
        }

        [HttpPost]
        public ActionResult RecoveryForm(UserRecoveryModel model)
        {
            bool result = false;
            try
            {
                result = _UserRecoveryService.ResetPassword(model.email, model.password, model.userRecoveryCode);
            }
            catch (Exception e)
            {
                model.returnMessage = "Something went wrong while trying to reset your password.";
            }
            if (result == false)
            {
                model.returnMessage = "Invalid password recovery attempt.";
            }
            else
            {
                model.returnMessage = "Password successfully changed!";
            }
            return View(model);
        }
    }
}