namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.Migrations;
using System.Linq;
using TrainGainV1.Models;

    internal sealed class Configuration : DbMigrationsConfiguration<TrainGainV1.Models.ApplicationDbContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
            MigrationsDirectory = @"DataContexts\ApplicationMigrations";
        }

        protected override void Seed(TrainGainV1.Models.ApplicationDbContext context)
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
               
            var Programs = new List<SportProgram>{
                new SportProgram { Sport = "Boxing", Goal="Slipping", Title = "Program for slipping 1", Program="This program is for boxers for Slipping", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Boxing", Goal="Slipping", Title = "Program for slipping 2", Program="This program 2 is for boxers for Slipping", ApplicationUsers = new List<ApplicationUser>()},
                new SportProgram { Sport = "Rugby", Goal="Strength", Title = "Program for Strength", Program="This program is for Rugby for Strength", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>(){
                     new Exercise { Name = "Bench Press"}, new Exercise { Name = "Deadlift"}, new Exercise { Name = "Squat"}, new Exercise { Name = "Chin Ups"}, new Exercise { Name = "Shoulder Press"}}},
                new SportProgram { Sport = "Basketball", Goal="Dribbling", Title = "Program for Dribbling", Program="This program 1 is for BasketBall", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Basketball", Goal="Dribbling", Title = "Program for Dribbling 2", Program="This program 2 is for BasketBall for dribbling", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Tennis", Goal="Serve", Title = "Program for Serve", Program="This program is for Tennis", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Thaiboxing", Goal="Checking", Title = "Program for checking", Program="This program is for Thaiboxing", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Boxing", Goal="Fast Hands", Title = "Program for Fast Hands", Program="This program is for boxers for Fast Hands", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Thaiboxing", Goal="Fast Hands", Title = "Program for Fast Hands Thaiboxing", Program="This program is for Thaiboxing for fast hands", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
            };
            Programs.ForEach(s => context.SportContext.AddOrUpdate(p => p.IDsport, s));
            context.SaveChanges();

            var userStore = new UserStore<ApplicationUser>(context);
            var userManager = new UserManager<ApplicationUser>(userStore);
            var userToInsert = new ApplicationUser
            {
                UserName = "john",
                Email = "john@hotmail.com",
                SportProgramCollection = new List<SportProgram>(),
                ExerciseCollection = new List<Exercise>()
            };
            userManager.Create(userToInsert, "Password-1");
        }
    }
}
