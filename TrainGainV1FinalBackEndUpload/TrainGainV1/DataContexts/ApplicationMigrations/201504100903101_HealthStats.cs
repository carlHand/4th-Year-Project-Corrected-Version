namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class HealthStats : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.AspNetUsers", "BMI", c => c.Double(nullable: false));
            AddColumn("dbo.AspNetUsers", "BMR", c => c.Double(nullable: false));
            AddColumn("dbo.AspNetUsers", "HeartRateAge", c => c.Double(nullable: false));
            AddColumn("dbo.AspNetUsers", "Calories", c => c.Double(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.AspNetUsers", "Calories");
            DropColumn("dbo.AspNetUsers", "HeartRateAge");
            DropColumn("dbo.AspNetUsers", "BMR");
            DropColumn("dbo.AspNetUsers", "BMI");
        }
    }
}
