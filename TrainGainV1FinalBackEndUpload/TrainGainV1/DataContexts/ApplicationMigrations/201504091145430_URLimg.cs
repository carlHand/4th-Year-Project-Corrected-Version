namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class URLimg : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.ExerciseImage", "URLimg", c => c.String());
            DropColumn("dbo.ExerciseImage", "Image");
        }
        
        public override void Down()
        {
            AddColumn("dbo.ExerciseImage", "Image", c => c.Binary());
            DropColumn("dbo.ExerciseImage", "URLimg");
        }
    }
}
