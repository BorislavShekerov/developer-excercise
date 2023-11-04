using Core.Dtos.Response;
using Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Core.Interfaces
{
    public interface IDiscount
    {
        public void ApplyDiscount(ref List<GroseryItem> scannedItems, ref List<SpecialDeal> specialDeals, ref int sum);
    }
}
