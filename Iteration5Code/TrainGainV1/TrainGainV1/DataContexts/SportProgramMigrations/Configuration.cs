namespace TrainGainV1.DataContexts.SportProgramMigrations
{
    using System;
    using System.Collections.Generic;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;
    using TrainGainV1.Models;

    internal sealed class Configuration : DbMigrationsConfiguration<TrainGainV1.DataContexts.SportProgramDb>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = true;
            MigrationsDirectory = @"DataContexts\SportProgramMigrations";
        }

        protected override void Seed(TrainGainV1.DataContexts.SportProgramDb context)
        {
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
            /*
            var Programs = new List<SportProgram>{
                new SportProgram { Sport = "Boxing", Goal="Slipping", Title = "Program for slipping 1", Program="This program is for boxers for Slipping"},
                new SportProgram { Sport = "Boxing", Goal="Slipping", Title = "Program for slipping 2", Program="This program 2 is for boxers for Slipping"},
                new SportProgram { Sport = "Basketball", Goal="Dribbling", Title = "Program for Dribbling", Program="This program 1 is for BasketBall"},
                 new SportProgram { Sport = "Basketball", Goal="Dribbling", Title = "Program for Dribbling 2", Program="This program 2 is for BasketBall for dribbling"},
                new SportProgram { Sport = "Tennis", Goal="Serve", Title = "Program for Serve", Program="This program is for Tennis"},
                new SportProgram { Sport = "Thaiboxing", Goal="Checking", Title = "Program for checking", Program="This program is for Thaiboxing"},
                new SportProgram { Sport = "Boxing", Goal="Fast Hands", Title = "Program for Fast Hands", Program="This program is for boxers for Fast Hands"},
                new SportProgram { Sport = "Thaiboxing", Goal="Fast Hands", Title = "Program for Fast Hands", Program="This program is for Thaiboxing for fast hands"}
            };
            Programs.ForEach(s => context.TrainingPrograms.AddOrUpdate(p => p.Program, s));
            context.SaveChanges();
             * */
            /*
            var Programs = new List<SportProgram>{
                new SportProgram { Sport = "Boxing", Goal="Slipping", Title = "Program for slipping 1", Program="This program is for boxers for Slipping"},
                new SportProgram { Sport = "Boxing", Goal="Slipping", Title = "Program for slipping 2", Program="This program 2 is for boxers for Slipping"},
                new SportProgram { Sport = "Basketball", Goal="Dribbling", Title = "Program for Dribbling", Program="This program 1 is for BasketBall"},
                 new SportProgram { Sport = "Basketball", Goal="Dribbling", Title = "Program for Dribbling 2", Program="This program 2 is for BasketBall for dribbling"},
                new SportProgram { Sport = "Tennis", Goal="Serve", Title = "Program for Serve", Program="This program is for Tennis"},
                new SportProgram { Sport = "Thaiboxing", Goal="Checking", Title = "Program for checking", Program="This program is for Thaiboxing"},
                new SportProgram { Sport = "Boxing", Goal="Fast Hands", Title = "Program for Fast Hands", Program="This program is for boxers for Fast Hands"},
                new SportProgram { Sport = "Thaiboxing", Goal="Fast Hands", Title = "Program for Fast Hands", Program="This program is for Thaiboxing for fast hands"}
            };
            Programs.ForEach(s => context.SportContext.AddOrUpdate(p => p.IDsport, s));
            context.SaveChanges();
            */
        }
    }
}
