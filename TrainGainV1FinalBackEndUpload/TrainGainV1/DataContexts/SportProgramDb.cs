using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;
using TrainGainV1.Models;

namespace TrainGainV1.DataContexts
{
    public class SportProgramDb : DbContext
    {
        
         public SportProgramDb()
            : base("DefaultConnection")
        {

        }

        public DbSet<SportProgram> TrainingPrograms { get; set; }
    
         }
        
}