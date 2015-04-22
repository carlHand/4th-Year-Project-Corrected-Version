using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;
using TrainGainV1.Models;

namespace TrainGainV1.DataContexts
{
    public class ExerciseImageDb : DbContext
    {
        public ExerciseImageDb()
            : base("DefaultConnection")
        {

        }

        public DbSet<ExerciseImage> Exercises { get; set; }
    }
}
  