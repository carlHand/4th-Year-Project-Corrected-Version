using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TrainGainV1.Models
{
    public class Product
    {
        public int ProductId { get; set; }
        public String Name { get; set; }
        public virtual List<Store> Stores { get; set; }
    }
}