namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ExerciseUserFK : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.ApplicationUserExercise",
                c => new
                    {
                        ApplicationUser_Id = c.String(nullable: false, maxLength: 128),
                        Exercise_IDexercise = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.ApplicationUser_Id, t.Exercise_IDexercise })
                .ForeignKey("dbo.AspNetUsers", t => t.ApplicationUser_Id, cascadeDelete: true)
                .ForeignKey("dbo.Exercise", t => t.Exercise_IDexercise, cascadeDelete: true)
                .Index(t => t.ApplicationUser_Id)
                .Index(t => t.Exercise_IDexercise);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.ApplicationUserExercise", "Exercise_IDexercise", "dbo.Exercise");
            DropForeignKey("dbo.ApplicationUserExercise", "ApplicationUser_Id", "dbo.AspNetUsers");
            DropIndex("dbo.ApplicationUserExercise", new[] { "Exercise_IDexercise" });
            DropIndex("dbo.ApplicationUserExercise", new[] { "ApplicationUser_Id" });
            DropTable("dbo.ApplicationUserExercise");
        }
    }
}
