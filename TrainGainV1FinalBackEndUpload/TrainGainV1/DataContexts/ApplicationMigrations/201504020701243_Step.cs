namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Step : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Step",
                c => new
                    {
                        IDstep = c.Int(nullable: false, identity: true),
                        StepDes = c.String(),
                        IDexercise = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.IDstep)
                .ForeignKey("dbo.Exercise", t => t.IDexercise, cascadeDelete: true)
                .Index(t => t.IDexercise);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Step", "IDexercise", "dbo.Exercise");
            DropIndex("dbo.Step", new[] { "IDexercise" });
            DropTable("dbo.Step");
        }
    }
}
