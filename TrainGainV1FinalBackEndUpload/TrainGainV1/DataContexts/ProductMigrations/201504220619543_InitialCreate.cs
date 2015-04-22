namespace TrainGainV1.DataContexts.ProductMigrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class InitialCreate : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Products",
                c => new
                    {
                        ProductId = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                    })
                .PrimaryKey(t => t.ProductId);
            
            CreateTable(
                "dbo.Stores",
                c => new
                    {
                        StoreID = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Address = c.String(),
                    })
                .PrimaryKey(t => t.StoreID);
            
            CreateTable(
                "dbo.StoreProducts",
                c => new
                    {
                        Store_StoreID = c.Int(nullable: false),
                        Product_ProductId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.Store_StoreID, t.Product_ProductId })
                .ForeignKey("dbo.Stores", t => t.Store_StoreID, cascadeDelete: true)
                .ForeignKey("dbo.Products", t => t.Product_ProductId, cascadeDelete: true)
                .Index(t => t.Store_StoreID)
                .Index(t => t.Product_ProductId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.StoreProducts", "Product_ProductId", "dbo.Products");
            DropForeignKey("dbo.StoreProducts", "Store_StoreID", "dbo.Stores");
            DropIndex("dbo.StoreProducts", new[] { "Product_ProductId" });
            DropIndex("dbo.StoreProducts", new[] { "Store_StoreID" });
            DropTable("dbo.StoreProducts");
            DropTable("dbo.Stores");
            DropTable("dbo.Products");
        }
    }
}
