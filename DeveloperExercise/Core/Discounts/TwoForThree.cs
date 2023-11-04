using Core.Dtos.Response;
using Core.Interfaces;
using Data.Enums;
using Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Core.Discounts
{
    public class TwoForThree : IDiscount
    {
        public void ApplyDiscount(ref List<GroseryItem> scannedItems, ref List<SpecialDeal> specialDeals, ref int sum)
        {

            var prev2 = new List<GroseryItem>();
            var deal2 = specialDeals.FirstOrDefault(x => x.DealType == (DealTypes)Enum.Parse(typeof(DealTypes), "TwoForThree"));
            for (int i = 0; i < scannedItems.Count; i++)
            {
                if (deal2 != null && deal2.Items[0].Name==scannedItems[i].Name)
                {
                    prev2.Add(scannedItems[i]);
                }
                else
                {
                    sum += scannedItems[i].PriceInClouds;
                }
                if (prev2.Count == 3)
                {
                    var prevsum = prev2.Sum(x => x.PriceInClouds);
                    var minsum = prev2.Min(x => x.PriceInClouds);
                    sum += (prevsum - minsum);
                    prev2.Clear();
                }
            }
            if (prev2.Count != 0)
            {
                foreach (var item in prev2)
                {
                    sum += item.PriceInClouds;
                }
            }

        }
    }
}
