namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class DateLogged : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Exercise", "DateLogged", c => c.DateTime(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Exercise", "DateLogged");
        }
    }
}
