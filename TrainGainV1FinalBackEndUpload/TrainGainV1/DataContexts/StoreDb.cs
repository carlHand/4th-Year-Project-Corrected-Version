using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;
using TrainGainV1.Models;

namespace TrainGainV1.DataContexts
{
    public class StoreDb : DbContext
    {

        public StoreDb()
            : base("DefaultConnection")
        {

        }
        public DbSet<Store> Stores { get; set; }
         }
}