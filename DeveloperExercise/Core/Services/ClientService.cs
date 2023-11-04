using Core.Discounts;
using Core.Dtos.Request;
using Core.Dtos.Response;
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
    public class ClientService : IClientService
    {
        private readonly DbContext _dbContext;
        
        public ClientService(DbContext dbContext)
        {
            _dbContext = dbContext;
           
        }

        public async Task<List<SpecialDeal>> GetAllDeals()
        {
            var result=await _dbContext.Set<SpecialDeal>().Include(x=>x.Items).ToListAsync();
            return result;
        }

        public async Task<List<ItemDtoRes>> GetAllItems()
        {
            var result = new List<ItemDtoRes>();
            var items=await _dbContext.Set<GroseryItem>().ToListAsync();
            foreach (var item in items)
            {
                result.Add(new ItemDtoRes {Name=item.Name,
                Price=item.PriceInClouds,
                DealId=item.SpecialDealId.ToString()});
            }
            return result;
        }
       
    }
}
