using Core.Discounts;
using Core.Interfaces;
using Data.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Core.Services
{
    public class GrosaryTillService : IGrosaryTillService
    {
        private List<GroseryItem> scannedItems = new List<GroseryItem>();
        private readonly IDiscount _discount1;
        private readonly IDiscount _discount2;
        public GrosaryTillService(BuyOneGetOneHalf discount1, TwoForThree discount2)
        {
                _discount1 = discount1;
            _discount2 = discount2;
        }
        public int CalculateTotal(List<SpecialDeal> specialDeals)
        {
            var sum = 0;
            

            _discount1.ApplyDiscount(ref scannedItems, ref specialDeals, ref sum);

            Console.WriteLine(sum);


            _discount2.ApplyDiscount(ref scannedItems, ref specialDeals, ref sum);
            scannedItems.Clear();
            return sum;
        }

        public void ScanItem(GroseryItem item)
        {
            scannedItems.Add(item);
        }

        public double ConvertToAws(int totalInClouds)
        {
            return totalInClouds / 100.0;
        }
    }
}
