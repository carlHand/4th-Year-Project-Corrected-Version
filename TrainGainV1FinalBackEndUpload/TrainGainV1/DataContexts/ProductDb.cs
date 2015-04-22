using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;
using TrainGainV1.Models;

namespace TrainGainV1.DataContexts
{
    public class ProductDb : DbContext
    {
        public ProductDb()
            : base("DefaultConnection")
        {

        }
        public DbSet<Product> Products { get; set; }
         }
}