namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ShowHelp : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.AspNetUsers", "ShowHelp", c => c.Boolean(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.AspNetUsers", "ShowHelp");
        }
    }
}
