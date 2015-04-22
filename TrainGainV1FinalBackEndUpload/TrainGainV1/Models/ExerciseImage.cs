using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace TrainGainV1.Models
{
    public class ExerciseImage
    {
        public int ID { get; set; }
        //public byte[] Image { get; set; }
        public String URLimg { get; set; }
        [ForeignKey("Exercise")]
        public int IDexercise { get; set; }
        public virtual Exercise Exercise { get; set; }
    }
}
 