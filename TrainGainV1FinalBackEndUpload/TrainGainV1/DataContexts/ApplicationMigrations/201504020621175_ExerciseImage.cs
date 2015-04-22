namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ExerciseImage : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.ExerciseImage",
                c => new
                    {
                        ID = c.Int(nullable: false, identity: true),
                        Image = c.Binary(),
                        IDexercise = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.ID)
                .ForeignKey("dbo.Exercise", t => t.IDexercise, cascadeDelete: true)
                .Index(t => t.IDexercise);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.ExerciseImage", "IDexercise", "dbo.Exercise");
            DropIndex("dbo.ExerciseImage", new[] { "IDexercise" });
            DropTable("dbo.ExerciseImage");
        }
    }
}
