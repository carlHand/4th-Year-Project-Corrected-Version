using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity.Owin;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;

namespace TrainGainV1.Models
{
    // You can add profile data for the user by adding more properties to your ApplicationUser class, please visit http://go.microsoft.com/fwlink/?LinkID=317594 to learn more.
    public class ApplicationUser : IdentityUser
    {
        public String Password { get; set; }
        public String Dob { get; set; }
        public double Weight { get; set; }
        public double Height { get; set; }
        public String Sport { get; set; }
        public String Gender { get; set; }
        public String ActivityLevel { get; set; }
       // public List<SportProgram> SportProgramList { get; set; }// = new List<SportProgram>();//{ get; set; }
        public virtual List<SportProgram> SportProgramCollection { get; set; }
        public virtual List<Exercise> ExerciseCollection { get; set; }
        /*
        public List<SportProgram> getSportProgramList()
        {
            return SportProgramList;
        }

        public void setSportProgramList(List<SportProgram> SportProgramList)
        {
            this.SportProgramList = SportProgramList;
        }
        */
        public async Task<ClaimsIdentity> GenerateUserIdentityAsync(UserManager<ApplicationUser> manager, string authenticationType)
        {
            // Note the authenticationType must match the one defined in CookieAuthenticationOptions.AuthenticationType
            var userIdentity = await manager.CreateIdentityAsync(this, authenticationType);
            // Add custom user claims here
            return userIdentity;
        }
    }

    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public ApplicationDbContext()
            : base("DefaultConnection", throwIfV1Schema: false)
        {
        }
        
        public static ApplicationDbContext Create()
        {
            return new ApplicationDbContext();
        }

        public DbSet<SportProgram> SportContext {get; set;}
        public DbSet<Exercise> ExerciseContext { get; set; }
       
        
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Conventions.Remove<PluralizingTableNameConvention>();
            /*
            modelBuilder.Entity<SportProgram>().HasMany(t => t.ApplicationUsers).WithMany(t => t.SportProgramCollection)
                .Map(t => t.MapLeftKey("ID")
                    .MapRightKey("IDsport")
                    .ToTable("UserProgram"));
            */
             modelBuilder.Entity<SportProgram>()
            .HasMany(c => c.ApplicationUsers).WithMany(i => i.SportProgramCollection)
            .Map(t => t.MapLeftKey("IDsport")
            .MapRightKey("ID")
            .ToTable("UserProgram"));
            // the all important base class call! Add this line to make your problems go away.
            base.OnModelCreating(modelBuilder);
        }
         
    }
}