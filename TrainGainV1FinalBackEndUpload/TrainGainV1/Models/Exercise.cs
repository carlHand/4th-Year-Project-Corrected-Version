using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Drawing;
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
        public DateTime DateLogged { get; set; }
        public virtual List<ExerciseImage> ExerciseImageL { get; set; }
        public virtual List<Step> StepL { get; set; }
        [JsonIgnore]
        public virtual ICollection<ApplicationUser> ApplicationUsersEx { get; set; }
       // [JsonIgnore]
        public virtual ICollection<SportProgram> SportProgramCollectionE { get; set; }

    }
}