namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class HeartRates : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.AspNetUsers", "HeartMax", c => c.Double(nullable: false));
            AddColumn("dbo.AspNetUsers", "HeartRange1", c => c.Double(nullable: false));
            AddColumn("dbo.AspNetUsers", "HeartRange2", c => c.Double(nullable: false));
            DropColumn("dbo.AspNetUsers", "HeartRateAge");
        }
        
        public override void Down()
        {
            AddColumn("dbo.AspNetUsers", "HeartRateAge", c => c.Double(nullable: false));
            DropColumn("dbo.AspNetUsers", "HeartRange2");
            DropColumn("dbo.AspNetUsers", "HeartRange1");
            DropColumn("dbo.AspNetUsers", "HeartMax");
        }
    }
}
