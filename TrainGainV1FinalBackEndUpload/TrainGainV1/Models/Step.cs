using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace TrainGainV1.Models
{
    public class Step
    {
        [Key]
        public int IDstep { get; set; }
        public String StepDes { get; set; }
        [ForeignKey("Exercise")]
        public int IDexercise { get; set; }
        public virtual Exercise Exercise { get; set; }
    }
}