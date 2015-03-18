using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace TrainGainV1.Models
{
    public class SportProgram
    {
        [Key]
        public int IDsport { get; set; }
        public String Sport { get; set; }
        public String Goal { get; set; }
        public String Title { get; set; }
        public String Program { get; set; }
        public String Intensity { get; set; }
        public double HrsPerDay { get; set; }
        public int SessionsPerDay { get; set; }
        [JsonIgnore]
        public virtual ICollection<ApplicationUser> ApplicationUsers { get; set; }
       // [JsonIgnore]
        public virtual List<Exercise> Exercises { get; set; }
    }
}