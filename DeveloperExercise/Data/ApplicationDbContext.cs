using Data.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static Microsoft.EntityFrameworkCore.DbLoggerCategory.Database;

namespace Data
{
    public class ApplicationDbContext:DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
       : base(options)
        {

        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            
        }

        public DbSet<GroseryItem> GroseryItems { get; set; }
        public DbSet<SpecialDeal> SpecialDeals { get; set; }
        
    }
}
