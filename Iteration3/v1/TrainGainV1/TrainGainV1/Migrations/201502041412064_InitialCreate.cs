namespace TrainGainV1.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class InitialCreate : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.People",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        PersonName = c.String(),
                        Email = c.String(),
                        Password = c.String(),
                        Dob = c.String(),
                        Weight = c.Double(nullable: false),
                        Height = c.Double(nullable: false),
                        Sport = c.String(),
                        Gender = c.String(),
                        ActivityLevel = c.String(),
                    })
                .PrimaryKey(t => t.Id);
        }
        
        public override void Down()
        {
            CreateTable(
                "dbo.Users",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        UserNamee = c.String(),
                        Emaile = c.String(),
                        Passworde = c.String(),
                        Dob = c.String(),
                        Weight = c.Double(nullable: false),
                        Height = c.Double(nullable: false),
                        Sport = c.String(),
                        Gender = c.String(),
                        ActivityLevel = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            DropTable("dbo.People");
        }
    }
}
