namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class ExerciseL : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Exercise",
                c => new
                    {
                        IDexercise = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        LiftValue = c.Double(nullable: false),
                    })
                .PrimaryKey(t => t.IDexercise);
            
            CreateTable(
                "dbo.SportProgramExercise",
                c => new
                    {
                        SportProgram_IDsport = c.Int(nullable: false),
                        Exercise_IDexercise = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.SportProgram_IDsport, t.Exercise_IDexercise })
                .ForeignKey("dbo.SportProgram", t => t.SportProgram_IDsport, cascadeDelete: true)
                .ForeignKey("dbo.Exercise", t => t.Exercise_IDexercise, cascadeDelete: true)
                .Index(t => t.SportProgram_IDsport)
                .Index(t => t.Exercise_IDexercise);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.SportProgramExercise", "Exercise_IDexercise", "dbo.Exercise");
            DropForeignKey("dbo.SportProgramExercise", "SportProgram_IDsport", "dbo.SportProgram");
            DropIndex("dbo.SportProgramExercise", new[] { "Exercise_IDexercise" });
            DropIndex("dbo.SportProgramExercise", new[] { "SportProgram_IDsport" });
            DropTable("dbo.SportProgramExercise");
            DropTable("dbo.Exercise");
        }
    }
}
