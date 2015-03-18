using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace TrainGainV1.Models
{
    public class Exercise
    {
        [Key]
        public int IDexercise { get; set; }
        public String Name { get; set; }
        public double LiftValue { get; set; }
        [JsonIgnore]
        public virtual ICollection<ApplicationUser> ApplicationUsersEx { get; set; }
       // [JsonIgnore]
        public virtual ICollection<SportProgram> SportProgramCollectionE { get; set; }

    }
}