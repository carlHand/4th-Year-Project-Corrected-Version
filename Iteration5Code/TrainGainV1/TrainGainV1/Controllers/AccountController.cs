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
            return Ok(this.UserManager.Users.ToList().Select(u => u));//.Select(u => this.TheModelFactory.Create(u)));
        }

        [Route("users/{name}")]
        public ApplicationUser GetTrainingProgram3(String name)
        {
            var user = UserManager.FindByName(name);
           // var u = UserManager.FindByName(name);//.Users.Where(w => (w.UserName.ToUpper().Equals(name.ToUpper())));
            return user;
        }
        /*
        [Route("updateUser/{username}")]
        public IHttpActionResult PutUser(String username)//, SportProgram program)
        {
            var user = UserManager.FindByName(username);
            SportProgram programNew = new SportProgram()
            {
                Program = "This is a test program",
                Goal = "Test goal",
                Title = "Test title"
            };
             try
                {
                    //var user = UserManager.FindByName(username);
                    user.SportProgramCollection.Add(programNew);
                    //user.Email = "Test@hotmail.com";
                    UserManager.Update(user);
                    db.SaveChanges();    
                }
                catch (NullReferenceException)
                {
                    return NotFound();
                }
            return Ok(user);
        }
        */
        [Route("updateUser/{username}")]
        public IHttpActionResult PutUser(String username, SportProgram programN)
        {
            var user = UserManager.FindByName(username);
            if (ModelState.IsValid)
            {
                try
                {
                    //var user = UserManager.FindByName(username);
                    user.SportProgramCollection.Add(programN);
                    //user.Email = "Test@hotmail.com";
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

        [Route("updateUser6/{username}")]
        public IHttpActionResult PutUser6(String username, SportProgram programN)
        {
            var user = UserManager.FindByName(username);
            if (ModelState.IsValid)
            {
                Exercise ee = new Exercise();
                ee.Name = "Bench Pressss3";
                List<Exercise> e = new List<Exercise>();
                e.Add(ee);
                SportProgram program = new SportProgram()
                {
                    Program = "This is a test program3",
                    Goal = "Test goal3",
                    Title = "Test title3",
                    Exercises = e
                };
                 
                //var user = UserManager.FindByName(username);
                try
                {
                   
                    //var user = UserManager.FindByName(username);
                    user.SportProgramCollection.Add(program);
                    //user.Email = "Test@hotmail.com";
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

        [Route("updateUser9/{username}")]
        public IHttpActionResult PutUser9(String username, SportProgram programN)
        {
            var user = UserManager.FindByName(username);
            if (ModelState.IsValid)
            {
                /*
                SportProgram program = new SportProgram()
                {
                    Program = "This is a test program",
                    Goal = "Test goal",
                    Title = "Test title"
                };
                 * */
                //var user = UserManager.FindByName(username);
                try
                {
                    programN.ApplicationUsers.Add(user);
                    //var user = UserManager.FindByName(username);

                    //user.Email = "Test@hotmail.com";
                    UserManager.UpdateAsync(user);
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
            //return Ok(user);
        }


        [Route("userPrograms/{username}")]
        public List<SportProgram> GetUserTrainingPrograms(string username)
        {
            var e = dbs.Users.Where(x => x.UserName.ToUpper().Equals(username.ToUpper())).Select(s => s.SportProgramCollection);
           // collection<SportProgram> l = e;
            //return dbs.Users.Where(x => x.UserName.ToUpper().Equals(username.ToUpper())).Select(s => s.SportProgramCollection);
            //return Ok(e);
            Collection<SportProgram> a = new Collection<SportProgram>();
           // a = e.ToList();
            //return a;
            var query = from s in dbs.SportContext
                        from u in dbs.Users
                        where u.UserName.ToUpper().Equals(username.ToUpper())
                        select u.SportProgramCollection;
            //SportProgram quer = dbs.SportContext.Where(u => u.UserName.ToUpper().Equals(username.ToUpper()));
            //return dbs.Users.Where(m => m.UserName.ToUpper().Equals(username.ToUpper())).SelectMany(m => m.SportProgramCollection);
            var user = UserManager.FindByName(username);
            return user.SportProgramCollection;
        }

        [Route("userLifts/{username}")]
        public List<Exercise> getUserLifts(string username)
        {
            var user = UserManager.FindByName(username);
            return user.ExerciseCollection;
        }
        /*
        [Route("userPrograms/{username}")]
        public IHttpActionResult GetUserTrainingPrograms(string username)
        {
            var data = new NavigationData();
            var user = UserManager.FindByName(username);
            List<SportProgram> ss = new List<SportProgram>();
            //var program = dbs.SportContext.Select(p => p.ApplicationUsers.Where(t => t.UserName.ToUpper().Equals(username.ToUpper()));
            var e = dbs.Users.Where(x => x.UserName.ToUpper().Equals(username.ToUpper())).Select(s=>s.SportProgramCollection);
            var use = data.ApplicationUsers.Where(o => o.UserName.ToUpper().Equals(username.ToUpper()));
            //dbs.Entry(use).Collection(v =>v.)
            data.SportPrograms = data.ApplicationUsers.Where(o => o.UserName.ToUpper().Equals(username.ToUpper())).Single().SportProgramCollection;
            /*
            foreach(SportProgram s in e)
            {
                if (s == null)
                {
                    return NotFound();
                }
                else
                {
                    ss.Add(s);
                }
            }
             
            return Ok(data);
            
            //var program = dbs.Users.Where(a => a.UserName.ToUpper().Equals(username.ToUpper())).Select(b => b.SportProgramCollection);
            //return program.AsEnumerable();//dbs.SportContext.Where(u => u.ApplicationUsers.Equals(user.));
        }
         */

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

        [Route("updateUserExercise2/{username}/{eList}")]
        public IHttpActionResult PutUserExercise2(String username, List<Exercise> eList)
        {
            var user = UserManager.FindByName(username);
            if (ModelState.IsValid)
            {
                try
                {
                    //var user = UserManager.FindByName(username);
                    user.ExerciseCollection = eList;
                    //user.Email = "Test@hotmail.com";
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

        [Route("updateUserExercise/{username}")]
        public IHttpActionResult PutUserExercise(String username, List<Exercise> eList)
        {
            var user = UserManager.FindByName(username);
            if (ModelState.IsValid)
            {
                try
                {
                    /*
                    Exercise e = new Exercise(){ Name = "Bench Press", LiftValue = 20 };
                    Exercise e1 = new Exercise() { Name = "Squat", LiftValue = 25 };
                    Exercise e2 = new Exercise() { Name = "Pull Ups", LiftValue = 30 };
                    Exercise e3 = new Exercise() { Name = "Front Squat", LiftValue = 60 };
                    Exercise e4 = new Exercise() { Name = "Shoulder Press", LiftValue = 15 };
                    Exercise e5 = new Exercise() { Name = "Deadlift", LiftValue = 90 };
                    List<Exercise> eList = new List<Exercise>();
                    eList.Add(e);
                    eList.Add(e1);
                    eList.Add(e2);
                    eList.Add(e3);
                    eList.Add(e4);
                    eList.Add(e5);
                    */
                    //var user = UserManager.FindByName(username);
                    user.ExerciseCollection = eList;
                    //user.Email = "Test@hotmail.com";
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
        public async Task<IHttpActionResult> ChangePassword(ChangePasswordBindingModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            IdentityResult result = await UserManager.ChangePasswordAsync(User.Identity.GetUserId(), model.OldPassword,
                model.NewPassword);
            
            if (!result.Succeeded)
            {
                return GetErrorResult(result);
            }

            return Ok();
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
        [Route("Register/{name}/{email}/{password}")]
        public async Task<IHttpActionResult> PostRegister(string name, string email, string password)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            IdentityUser users = new IdentityUser
            {
                UserName = name,
                Email = email
            };
            var user = new ApplicationUser()
            {
                UserName = name,
                Email = email
            };
          


            IdentityResult result = await UserManager.CreateAsync(user, password);
            if (!result.Succeeded)
            {
                return GetErrorResult(result);
            }
            /*
            IHttpActionResult errorResult = GetErrorResult(result);

                if (errorResult != null)
                {
                    return errorResult;
                }

               // IdentityDbContext<IdentityUser> identityUsers;// { get; set; }
                //identityUser.Users.Add(user);
              //  TrainGainContext.persons.Add(p);
            
                //identityUser.SaveChanges();
            //    TrainGainContext.SaveChanges();
                */
            return Ok("Working");
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
