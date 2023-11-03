using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Data.Migrations
{
    /// <inheritdoc />
    public partial class InitialMigration : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "SpecialDeals",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    DealType = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_SpecialDeals", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "GroseryItems",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    PriceInClouds = table.Column<int>(type: "int", nullable: false),
                    SpecialDealId = table.Column<Guid>(type: "uniqueidentifier", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_GroseryItems", x => x.Id);
                    table.ForeignKey(
                        name: "FK_GroseryItems_SpecialDeals_SpecialDealId",
                        column: x => x.SpecialDealId,
                        principalTable: "SpecialDeals",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateIndex(
                name: "IX_GroseryItems_SpecialDealId",
                table: "GroseryItems",
                column: "SpecialDealId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "GroseryItems");

            migrationBuilder.DropTable(
                name: "SpecialDeals");
        }
    }
}
