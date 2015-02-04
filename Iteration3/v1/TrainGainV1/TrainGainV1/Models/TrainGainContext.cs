using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace TrainGainV1.Models
{
    public partial class TrainGainContext : DbContext
    {
       // public DbSet<User> Users { get; set; }

        public TrainGainContext() : base("name=TrainGainContext")
        {
        }

        public TrainGainContext(string connString)
            : base(connString)
        {
        }
            
        public System.Data.Entity.DbSet<Person> persons { get; set; }
    }
}