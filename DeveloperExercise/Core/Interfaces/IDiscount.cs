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
        public int ApplyDiscount( List<GroseryItem> scannedItems,  List<SpecialDeal> specialDeals,  int sum);
    }
}
