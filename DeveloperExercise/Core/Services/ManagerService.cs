using Core.Dtos.Request;
using Core.Dtos.Response;
using Core.Interfaces;
using Data.Enums;
using Data.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace Core.Services
{
    public class ManagerService : IManagerService
    {
        private readonly DbContext _context;
        public ManagerService(DbContext context)
        {
            _context = context;
        }

        public async Task<ItemDtoRes> AddItem(ItemDtoReq item)
        {
            GroseryItem entity = new GroseryItem()
            {
                Name = item.Name,
                PriceInClouds = item.Price,

            };
            await _context.AddAsync(entity);
            await _context.SaveChangesAsync();
            return new ItemDtoRes
            {
                Name=entity.Name,
                Price=entity.PriceInClouds,
            };

        }

        public async Task<ItemDtoRes> AddItemToDeal(string itemName, int dealType)
        {
            var deal = await _context.Set<SpecialDeal>().Where(x => x.DealType == (DealTypes)dealType).FirstOrDefaultAsync();
            GroseryItem entity = await _context.Set<GroseryItem>().Where(x => x.Name == itemName).FirstOrDefaultAsync();
            if (entity==null)
            {
                throw new ArgumentException("Item with this name doesn't exist!");
            }
            entity.SpecialDealId = deal.Id;
            
            _context.Update<GroseryItem>(entity);
            await _context.SaveChangesAsync();
            return new ItemDtoRes
            {
                Name = entity.Name,
                Price = entity.PriceInClouds,
                DealId = entity.SpecialDealId.ToString(),
            };

          
        }

        public async Task<ManagerResDto> Login(ManagerLogInDto manager)
        {
            var managerEntity = await _context.Set<Manager>().Where(x => x.Email == manager.Email).FirstOrDefaultAsync();
            if (managerEntity==null) 
            {
                throw new ArgumentException("Company not found");
            }
            if (!VerifyPassword(manager.Password,managerEntity.PasswordHash,managerEntity.PasswordSalt))
            {
                throw new ArgumentException("Wrong password");
            }
            return new ManagerResDto
            {
                FirstName=managerEntity.FirstName,
                LastName=managerEntity.LastName,
                Email=managerEntity.Email,
            };
        }

        public async Task<ManagerResDto> Register(ManagerRegisterDto manger)
        {
            Manager managerEntity = new Manager()
            {
                FirstName = manger.FirstName,
                LastName = manger.LastName,
                Email = manger.Email,
            };
            CreatePasswordHash(manger.Password, out byte[] passwordHash, out byte[] passwordSalt);
            managerEntity.PasswordSalt = passwordSalt;
            managerEntity.PasswordHash= passwordHash;
            await _context.AddAsync<Manager>(managerEntity);
            await _context.SaveChangesAsync();
            var createdManager = await _context.Set<Manager>()
                .Where(x => x.Email == manger.Email).FirstOrDefaultAsync();
            return new ManagerResDto
            {
                FirstName=createdManager.FirstName, 
                LastName=createdManager.LastName,
                Email=createdManager.Email,
            };

        }
        private void CreatePasswordHash(string password, out byte[] passwordHash, out byte[] passwordSalt)
        {
            using (var hmac = new HMACSHA512())
            {
                passwordSalt = hmac.Key;
                passwordHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
            }
        }
        private bool VerifyPassword(string password, byte[] passwordHash, byte[] passwordSalt)
        {
            using (var hmac = new HMACSHA512(passwordSalt))
            {
                var computedHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
                return computedHash.SequenceEqual(passwordHash);
            }
        }
    }
}
