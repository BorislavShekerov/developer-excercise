using Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Core.Interfaces
{
    public interface IGrosaryTillService
    {
        public void ScanItem(GroseryItem item);
        public int CalculateTotal(List<SpecialDeal> specialDeals);
        public double ConvertToAws(int totalInClouds);
    }
}
