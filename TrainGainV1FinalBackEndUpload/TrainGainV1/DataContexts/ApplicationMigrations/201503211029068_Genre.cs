namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Genre : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.SportProgram", "Genre", c => c.String());
        }
        
        public override void Down()
        {
            DropColumn("dbo.SportProgram", "Genre");
        }
    }
}
