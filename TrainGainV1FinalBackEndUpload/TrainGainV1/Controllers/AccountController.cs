using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Threading.Tasks;
using System.Web;
using System.Data.Entity;
using System.Web.Http;
using System.Web.Http.ModelBinding;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity.Owin;
using Microsoft.Owin.Security;
using Microsoft.Owin.Security.Cookies;
using Microsoft.Owin.Security.OAuth;
using TrainGainV1.Models;
using TrainGainV1.Providers;
using TrainGainV1.Results;
using System.Linq;

using System.Collections;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Net;
using System.Drawing;

namespace TrainGainV1.Controllers
{
    //[Authorize]
    [RoutePrefix("api/Account")]
    public class AccountController : ApiController
    {
        private const string LocalLoginProvider = "Local";
        private IdentityDbContext db = new IdentityDbContext();
        private ApplicationUserManager _userManager;
        private ApplicationDbContext dbs = new ApplicationDbContext();
        List<SportProgram> SportProgramLists = new List<SportProgram>(); 
        public AccountController()
        {
        }

        public AccountController(ApplicationUserManager userManager,
            ISecureDataFormat<AuthenticationTicket> accessTokenFormat)
        {
            UserManager = userManager;
            AccessTokenFormat = accessTokenFormat;
        }

        public ApplicationUserManager UserManager
        {
            get
            {
                return _userManager ?? Request.GetOwinContext().GetUserManager<ApplicationUserManager>();
            }
            private set
            {
                _userManager = value;
            }
        }

        public ISecureDataFormat<AuthenticationTicket> AccessTokenFormat { get; private set; }

        [Route("users")]
        public IHttpActionResult GetUsers()
        {
            return Ok(this.UserManager.Users.ToList().Select(u => u));
        }

        [Route("users/{name}")]
        public ApplicationUser GetTrainingProgram3(String name)
        {
            var user = UserManager.FindByName(name);
            return user;
        }
       
        [Route("updateUser/{username}")]
        public IHttpActionResult PutUser(String username, SportProgram programN)
        {
            var user = UserManager.FindByName(username);
          
            var response = new HttpResponseMessage();
            if (ModelState.IsValid)
            {
                try
                {
                    var programExists = dbs.Users.Any(a => a.SportProgramCollection.Any(c => c.Title == programN.Title));
                    if (programExists)
                    {
                        return NotFound();
                    }
                    else if(!programExists)
                    {
                        user.SportProgramCollection.Add(programN);
                        UserManager.Update(user);
                        dbs.SaveChanges();
                        return Ok(user);
                    }
                }
                catch (NullReferenceException)
                {
                    return NotFound();
                }
            }
            else
            {
                return BadRequest();
            }
            return Ok();
        }
        
        [Route("userProfile/{username}")]
        public IHttpActionResult getUserProfile(String username)
        {
            var user = UserManager.FindByName(username);
            if (user != null)
            {
                return Ok(user);
            }
            else
            {
                return NotFound();
            }
        }

        [Route("userHelp/{username}")]
        public IHttpActionResult getUserHelpOption(String username)
        {
            var user = UserManager.FindByName(username);
            if (user != null)
            {
                return Ok(user.ShowHelp);
            }
            else
            {
                return NotFound();
            }
        }


        [Route("userPrograms/{username}")]
        public List<SportProgram> GetUserTrainingPrograms(string username)
        {
            var e = dbs.Users.Where(x => x.UserName.ToUpper().Equals(username.ToUpper())).Select(s => s.SportProgramCollection);
            Collection<SportProgram> a = new Collection<SportProgram>();
            var query = from s in dbs.SportContext
                        from u in dbs.Users
                        where u.UserName.ToUpper().Equals(username.ToUpper())
                        select u.SportProgramCollection;  
            var user = UserManager.FindByName(username);
            return user.SportProgramCollection;
        }

        [Route("userLifts2/{username}/{sport}")]
        public List<Exercise> getUserLifts2(string username, string sport)
        {
            var user = UserManager.FindByName(username);
            var p = dbs.SportContext.Where(e => e.Sport.ToUpper().Equals(sport.ToUpper()));
            var r = user.ExerciseCollection.Where(e => e.Name.ToUpper().Equals(sport.ToUpper()));
            return r.ToList();
        }

        [Route("userLifts101/{username}/{title}")]
        public IHttpActionResult getUserLifts101(String username, String title)
        {
            List<Exercise> eList = new List<Exercise>();
          
            var errors = ModelState.Values.SelectMany(v => v.Errors);
            try
            {
                var user = UserManager.FindByName(username);
                int size = user.ExerciseCollection.Count;
                //var el = user.ExerciseCollection.Where(o => o.Where(m => m.Title.ToUpper().Equals(program.Title)));
                int sizeSport = 0;
                foreach (var e in user.ExerciseCollection)
                {
                    // sizeSport = e.SportProgramCollectionE.Count;
                    /*
                    if (e == null)
                    {
                        return NotFound();
                    }
                     * */

                    Debug.WriteLine("SIZE: " + size);

                    Debug.WriteLine("SIZE: " + size);
                    //Debug.WriteLine("SIZE SPORT: " + sizeSport);
                    if (e.SportProgramCollectionE.ElementAt(0).Title.ToUpper().Equals(title.ToUpper()))
                    {
                        /*
                        for (int i = 0; i < e.Exercises.Count; i++)
                        {
                             
                            eList.Add(e);   
                        }
                         * */
                        eList.Add(e);
                    }
                }
            }
            catch (NullReferenceException nre)
            {
                Debug.WriteLine(nre.ToString());
            }
          
            //var p = user.SportProgramCollection.Where(a => a.Title.ToUpper().Equals(program.Title.ToUpper())).Select(u => u.Exercises);
            return Ok(eList);
            //return user.ExerciseCollection;
        }

        // GET api/Account/UserInfo
        [HostAuthentication(DefaultAuthenticationTypes.ExternalBearer)]
        [Route("UserInfo")]
        public UserInfoViewModel GetUserInfo()
        {
            ExternalLoginData externalLogin = ExternalLoginData.FromIdentity(User.Identity as ClaimsIdentity);

            return new UserInfoViewModel
            {
                Email = User.Identity.GetUserName(),
                HasRegistered = externalLogin == null,
                LoginProvider = externalLogin != null ? externalLogin.LoginProvider : null
            };
        }

        [Route("updateUserExercise/{username}")]
        public IHttpActionResult PutUserExercise(String username, Exercise e)
        {
            var user = UserManager.FindByName(username);
            if (ModelState.IsValid)
            {
                try
                {
                    DateTime now = DateTime.UtcNow;
                    DateTime currTimeZone;
                    currTimeZone = DateTime.SpecifyKind(now, DateTimeKind.Utc);
                    e.DateLogged = currTimeZone;
                    user.ExerciseCollection.Add(e);
                    UserManager.Update(user);
                    dbs.SaveChanges();
                }
                catch (NullReferenceException)
                {
                    return NotFound();
                }
            }
            else
            {
                return BadRequest(ModelState);
            }
            return Ok(user);
        }

        [Route("removeUserProgram/{username}/{title}")]
        public IHttpActionResult DeleteUserProgram(String username, String title)
        {
            var user = UserManager.FindByName(username);
            var response = new HttpResponseMessage();
            if (ModelState.IsValid)
            {
                try
                {
                    var programExists = dbs.Users.Any(a => a.SportProgramCollection.Any(c => c.Title.ToUpper().Equals(title.ToUpper())));
                    if (!programExists)
                    {
                        return NotFound();
                    }
                    else if (programExists)
                    {
                        user.SportProgramCollection.RemoveAll(r => r.Title.ToUpper().Equals(title.ToUpper()));
                        UserManager.Update(user);
                        dbs.SaveChanges();
                        return Ok(user);
                    }
                }
                catch (NullReferenceException)
                {
                    return NotFound();
                }
            }
            else
            {
                return BadRequest();
            }
            return Ok();
        }

        // POST api/Account/Logout
        [Route("Logout")]
        public IHttpActionResult Logout()
        {
            Authentication.SignOut(CookieAuthenticationDefaults.AuthenticationType);
            return Ok();
        }

        // GET api/Account/ManageInfo?returnUrl=%2F&generateState=true
        [Route("ManageInfo")]
        public async Task<ManageInfoViewModel> GetManageInfo(string returnUrl, bool generateState = false)
        {
            IdentityUser user = await UserManager.FindByIdAsync(User.Identity.GetUserId());

            if (user == null)
            {
                return null;
            }

            List<UserLoginInfoViewModel> logins = new List<UserLoginInfoViewModel>();

            foreach (IdentityUserLogin linkedAccount in user.Logins)
            {
                logins.Add(new UserLoginInfoViewModel
                {
                    LoginProvider = linkedAccount.LoginProvider,
                    ProviderKey = linkedAccount.ProviderKey
                });
            }

            if (user.PasswordHash != null)
            {
                logins.Add(new UserLoginInfoViewModel
                {
                    LoginProvider = LocalLoginProvider,
                    ProviderKey = user.UserName,
                });
            }

            return new ManageInfoViewModel
            {
                LocalLoginProvider = LocalLoginProvider,
                Email = user.UserName,
                Logins = logins,
                ExternalLoginProviders = GetExternalLogins(returnUrl, generateState)
            };
        }

        // POST api/Account/ChangePassword
        [Route("ChangePassword")]
        public async Task<IHttpActionResult> PutChangePassword(ApplicationUser user)
        {
            var userTemp = await UserManager.FindByNameAsync(user.UserName);
            userTemp.Password = user.Password;
            await UserManager.UpdateAsync(userTemp);
            return Ok();
        }
        [Route("ChangeHelpOption/{username}/{show}")]
        public async Task<IHttpActionResult> PutUserHelp(string username, bool show)
        {
            var userTemp = await UserManager.FindByNameAsync(username);
            if (userTemp != null)
            {
                userTemp.ShowHelp = show;
                await UserManager.UpdateAsync(userTemp);
                return Ok(userTemp);
            }
            else
            {
                return NotFound();
            }
        }
        [Route("ChangeUserProfile")]
        public async Task<IHttpActionResult> PutUserProfile(ApplicationUser user)
        {
            var userTemp = await UserManager.FindByNameAsync(user.UserName);
            userTemp.BMI = user.BMI;
            userTemp.BMR = user.BMR;
            userTemp.Calories = user.Calories;
            userTemp.Email = user.Email;
            userTemp.Dob = user.Dob;
            userTemp.Height = user.Height;
            userTemp.Weight = user.Weight;
            userTemp.Gender = user.Gender;
            userTemp.HeartMax = user.HeartMax;
            userTemp.HeartRange1 = user.HeartRange1;
            userTemp.HeartRange2 = user.HeartRange2;
            userTemp.Password = user.Password;
            userTemp.ShowHelp = user.ShowHelp;
            await UserManager.UpdateAsync(userTemp);
            return Ok(userTemp);
        }
        [Route("ChangeUserStatsBMI")]
        public async Task<IHttpActionResult> PutUserStatsBMI(ApplicationUser user)
        {
            var userTemp = await UserManager.FindByNameAsync(user.UserName);
            userTemp.BMI = user.BMI;
            await UserManager.UpdateAsync(userTemp);
            return Ok(userTemp);  
        }
        [Route("ChangeUserStatsBMR")]
        public async Task<IHttpActionResult> PutUserStatsBMR(ApplicationUser user)
        {
            var userTemp = await UserManager.FindByNameAsync(user.UserName);
            userTemp.BMR = user.BMR;
            await UserManager.UpdateAsync(userTemp);
            return Ok(userTemp);
        }
        [Route("ChangeUserStatsCalories")]
        public async Task<IHttpActionResult> PutUserStatsCalories(ApplicationUser user)
        {
            var userTemp = await UserManager.FindByNameAsync(user.UserName);
            userTemp.Calories = user.Calories;
            await UserManager.UpdateAsync(userTemp);
            return Ok(userTemp);
        }

        [Route("ChangeUserStatsHeartRate")]
        public async Task<IHttpActionResult> PutUserStatsHeartRate(ApplicationUser user)
        {
            var userTemp = await UserManager.FindByNameAsync(user.UserName);
            userTemp.HeartMax = user.HeartMax;
            userTemp.HeartRange1 = user.HeartRange1;
            userTemp.HeartRange2 = user.HeartRange2;
            await UserManager.UpdateAsync(userTemp);
            return Ok(userTemp);
        }

        // POST api/Account/SetPassword
        [Route("SetPassword")]
        public async Task<IHttpActionResult> SetPassword(SetPasswordBindingModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            IdentityResult result = await UserManager.AddPasswordAsync(User.Identity.GetUserId(), model.NewPassword);

            if (!result.Succeeded)
            {
                return GetErrorResult(result);
            }

            return Ok();
        }

        // POST api/Account/AddExternalLogin
        [Route("AddExternalLogin")]
        public async Task<IHttpActionResult> AddExternalLogin(AddExternalLoginBindingModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            Authentication.SignOut(DefaultAuthenticationTypes.ExternalCookie);

            AuthenticationTicket ticket = AccessTokenFormat.Unprotect(model.ExternalAccessToken);

            if (ticket == null || ticket.Identity == null || (ticket.Properties != null
                && ticket.Properties.ExpiresUtc.HasValue
                && ticket.Properties.ExpiresUtc.Value < DateTimeOffset.UtcNow))
            {
                return BadRequest("External login failure.");
            }

            ExternalLoginData externalData = ExternalLoginData.FromIdentity(ticket.Identity);

            if (externalData == null)
            {
                return BadRequest("The external login is already associated with an account.");
            }

            IdentityResult result = await UserManager.AddLoginAsync(User.Identity.GetUserId(),
                new UserLoginInfo(externalData.LoginProvider, externalData.ProviderKey));

            if (!result.Succeeded)
            {
                return GetErrorResult(result);
            }

            return Ok();
        }

        // POST api/Account/RemoveLogin
        [Route("RemoveLogin")]
        public async Task<IHttpActionResult> RemoveLogin(RemoveLoginBindingModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            IdentityResult result;

            if (model.LoginProvider == LocalLoginProvider)
            {
                result = await UserManager.RemovePasswordAsync(User.Identity.GetUserId());
            }
            else
            {
                result = await UserManager.RemoveLoginAsync(User.Identity.GetUserId(),
                    new UserLoginInfo(model.LoginProvider, model.ProviderKey));
            }

            if (!result.Succeeded)
            {
                return GetErrorResult(result);
            }

            return Ok();
        }

        // GET api/Account/ExternalLogin
        [OverrideAuthentication]
        [HostAuthentication(DefaultAuthenticationTypes.ExternalCookie)]
        [AllowAnonymous]
        [Route("ExternalLogin", Name = "ExternalLogin")]
        public async Task<IHttpActionResult> GetExternalLogin(string provider, string error = null)
        {
            if (error != null)
            {
                return Redirect(Url.Content("~/") + "#error=" + Uri.EscapeDataString(error));
            }

            if (!User.Identity.IsAuthenticated)
            {
                return new ChallengeResult(provider, this);
            }

            ExternalLoginData externalLogin = ExternalLoginData.FromIdentity(User.Identity as ClaimsIdentity);

            if (externalLogin == null)
            {
                return InternalServerError();
            }

            if (externalLogin.LoginProvider != provider)
            {
                Authentication.SignOut(DefaultAuthenticationTypes.ExternalCookie);
                return new ChallengeResult(provider, this);
            }

            ApplicationUser user = await UserManager.FindAsync(new UserLoginInfo(externalLogin.LoginProvider,
                externalLogin.ProviderKey));

            bool hasRegistered = user != null;

            if (hasRegistered)
            {
                Authentication.SignOut(DefaultAuthenticationTypes.ExternalCookie);
                
                 ClaimsIdentity oAuthIdentity = await user.GenerateUserIdentityAsync(UserManager,
                    OAuthDefaults.AuthenticationType);
                ClaimsIdentity cookieIdentity = await user.GenerateUserIdentityAsync(UserManager,
                    CookieAuthenticationDefaults.AuthenticationType);

                AuthenticationProperties properties = ApplicationOAuthProvider.CreateProperties(user.UserName);
                Authentication.SignIn(properties, oAuthIdentity, cookieIdentity);
            }
            else
            {
                IEnumerable<Claim> claims = externalLogin.GetClaims();
                ClaimsIdentity identity = new ClaimsIdentity(claims, OAuthDefaults.AuthenticationType);
                Authentication.SignIn(identity);
            }

            return Ok();
        }

        // GET api/Account/ExternalLogins?returnUrl=%2F&generateState=true
        [AllowAnonymous]
        [Route("ExternalLogins")]
        public IEnumerable<ExternalLoginViewModel> GetExternalLogins(string returnUrl, bool generateState = false)
        {
            IEnumerable<AuthenticationDescription> descriptions = Authentication.GetExternalAuthenticationTypes();
            List<ExternalLoginViewModel> logins = new List<ExternalLoginViewModel>();

            string state;

            if (generateState)
            {
                const int strengthInBits = 256;
                state = RandomOAuthStateGenerator.Generate(strengthInBits);
            }
            else
            {
                state = null;
            }

            foreach (AuthenticationDescription description in descriptions)
            {
                ExternalLoginViewModel login = new ExternalLoginViewModel
                {
                    Name = description.Caption,
                    Url = Url.Route("ExternalLogin", new
                    {
                        provider = description.AuthenticationType,
                        response_type = "token",
                        client_id = Startup.PublicClientId,
                        redirect_uri = new Uri(Request.RequestUri, returnUrl).AbsoluteUri,
                        state = state
                    }),
                    State = state
                };
                logins.Add(login);
            }

            return logins;
        }

        // POST api/Account/Register
        [AllowAnonymous]
        [Route("Register/{password}")]
        public async Task<IHttpActionResult> PostRegister(string password, ApplicationUser user)//string name, string email, string password)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var userNew = new ApplicationUser()
            {
                UserName = user.UserName,
                Email = user.Email,
                Gender = user.Gender,
                Weight = user.Weight,
                Dob = user.Dob,
                Height = user.Height,
                ShowHelp = true,
            };

            IdentityResult result = await UserManager.CreateAsync(userNew, password);
            if (!result.Succeeded)
            {
                return GetErrorResult(result);
            }
            return Ok(userNew);
        }

        // POST api/Account/RegisterExternal
        [OverrideAuthentication]
        [HostAuthentication(DefaultAuthenticationTypes.ExternalBearer)]
        [Route("RegisterExternal")]
        public async Task<IHttpActionResult> RegisterExternal(RegisterExternalBindingModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var info = await Authentication.GetExternalLoginInfoAsync();
            if (info == null)
            {
                return InternalServerError();
            }

            var user = new ApplicationUser() { UserName = model.Email, Email = model.Email };

            IdentityResult result = await UserManager.CreateAsync(user);
            if (!result.Succeeded)
            {
                return GetErrorResult(result);
            }

            result = await UserManager.AddLoginAsync(user.Id, info.Login);
            if (!result.Succeeded)
            {
                return GetErrorResult(result); 
            }
            return Ok();
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing && _userManager != null)
            {
                _userManager.Dispose();
                _userManager = null;
            }

            base.Dispose(disposing);
        }

        #region Helpers

        private IAuthenticationManager Authentication
        {
            get { return Request.GetOwinContext().Authentication; }
        }

        private IHttpActionResult GetErrorResult(IdentityResult result)
        {
            if (result == null)
            {
                return InternalServerError();
            }

            if (!result.Succeeded)
            {
                if (result.Errors != null)
                {
                    foreach (string error in result.Errors)
                    {
                        ModelState.AddModelError("", error);
                    }
                }

                if (ModelState.IsValid)
                {
                    // No ModelState errors are available to send, so just return an empty BadRequest.
                    return BadRequest();
                }

                return BadRequest(ModelState);
            }

            return null;
        }

        private class ExternalLoginData
        {
            public string LoginProvider { get; set; }
            public string ProviderKey { get; set; }
            public string UserName { get; set; }

            public IList<Claim> GetClaims()
            {
                IList<Claim> claims = new List<Claim>();
                claims.Add(new Claim(ClaimTypes.NameIdentifier, ProviderKey, null, LoginProvider));

                if (UserName != null)
                {
                    claims.Add(new Claim(ClaimTypes.Name, UserName, null, LoginProvider));
                }

                return claims;
            }

            public static ExternalLoginData FromIdentity(ClaimsIdentity identity)
            {
                if (identity == null)
                {
                    return null;
                }

                Claim providerKeyClaim = identity.FindFirst(ClaimTypes.NameIdentifier);

                if (providerKeyClaim == null || String.IsNullOrEmpty(providerKeyClaim.Issuer)
                    || String.IsNullOrEmpty(providerKeyClaim.Value))
                {
                    return null;
                }

                if (providerKeyClaim.Issuer == ClaimsIdentity.DefaultIssuer)
                {
                    return null;
                }

                return new ExternalLoginData
                {
                    LoginProvider = providerKeyClaim.Issuer,
                    ProviderKey = providerKeyClaim.Value,
                    UserName = identity.FindFirstValue(ClaimTypes.Name)
                };
            }
        }

        private static class RandomOAuthStateGenerator
        {
            private static RandomNumberGenerator _random = new RNGCryptoServiceProvider();

            public static string Generate(int strengthInBits)
            {
                const int bitsPerByte = 8;

                if (strengthInBits % bitsPerByte != 0)
                {
                    throw new ArgumentException("strengthInBits must be evenly divisible by 8.", "strengthInBits");
                }

                int strengthInBytes = strengthInBits / bitsPerByte;

                byte[] data = new byte[strengthInBytes];
                _random.GetBytes(data);
                return HttpServerUtility.UrlTokenEncode(data);
            }
        }

        #endregion
    }
}
