namespace TrainGainV1.DataContexts.ProductMigrations
{
    using System;
    using System.Collections.Generic;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;
    using TrainGainV1.Models;

    internal sealed class Configuration : DbMigrationsConfiguration<TrainGainV1.DataContexts.ProductDb>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = true;
            MigrationsDirectory = @"DataContexts\ProductMigrations";
        }

        protected override void Seed(TrainGainV1.DataContexts.ProductDb context)
        {
            var Products = new List<Product>{
                new Product { Name = "Quinoa", Stores = new List<Store>(){ new Store { Name = "Holland and Barrett", Address = "236A, The Square Shopping Centre, Tallaght, Dublin"},
                new Store { Name = "Nourish", Address = "Unit 28, Nutgrove Shopping Centre, Nutgrove Way, Rathfarnham, Co. Dublin"}}}
            };
            Products.ForEach(s => context.Products.AddOrUpdate(p => p.ProductId, s));
            context.SaveChanges();
            //  This method will be called after migrating to the latest version.

            //  You can use the DbSet<T>.AddOrUpdate() helper extension method 
            //  to avoid creating duplicate seed data. E.g.
            //
            //    context.People.AddOrUpdate(
            //      p => p.FullName,
            //      new Person { FullName = "Andrew Peters" },
            //      new Person { FullName = "Brice Lambson" },
            //      new Person { FullName = "Rowan Miller" }
            //    );
            //
        }
    }
}
