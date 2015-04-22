using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TrainGainV1.Models
{
    public class NavigationData
    {
        public IEnumerable<ApplicationUser> ApplicationUsers { get; set; }
        public IEnumerable<SportProgram> SportPrograms { get; set; }
    }
}