using Core.Dtos.Response;
using Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Core.Interfaces
{
    public interface IClientService
    {
        Task<List<ItemDtoRes>> GetAllItems();
        Task<List<SpecialDeal>> GetAllDeals();
        
    }
}
