using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace TrainGainV1.Models
{
    public class Store
    {
        [Key]
        public int StoreID { get; set; }
        public String Name { get; set; }
        public String Address { get; set; }
        public virtual List<Product> Products { get; set; }
       
    }
}