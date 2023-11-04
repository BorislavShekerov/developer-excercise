using Core.Dtos.Request;
using Core.Dtos.Response;
using Core.Interfaces;
using Data.Models;
using Microsoft.AspNetCore.Mvc;

namespace DeveloperExerciseApi.Controllers
{
    public class CustomerController : Controller
    {
        private readonly IClientService _clientService;
        private readonly IGrosaryTillService _groaryTillService;
        public CustomerController(IClientService clientService, IGrosaryTillService groaryTillService)
        {
                _clientService = clientService;
            _groaryTillService= groaryTillService;
        }
        [HttpGet("getAllItems")]
        [ProducesResponseType(typeof(List<ItemDtoRes>),StatusCodes.Status200OK)]
        public async Task<IActionResult> GetAllItems()
        {
            var result=new List<ItemDtoRes>();
            try
            {
                result = await _clientService.GetAllItems();
            }
            catch (Exception)
            {

                return BadRequest();
            }
            return Ok(result);
        }
        [HttpPost("scanItems")]
        [ProducesResponseType(typeof(ItemDtoRes),StatusCodes.Status200OK)]
        public async Task<IActionResult> ScanItem(ItemDtoReq item)
        {
            GroseryItem entity = new GroseryItem
            {
                Name = item.Name,
                PriceInClouds = item.Price,
            };
              _groaryTillService.ScanItem(entity);
            return Ok(entity);
        }
        [HttpGet("calculateTotal")]
        public async Task<IActionResult> CalculateTotal()
        {
            var result=0;
            try
            {
                var deals=await _clientService.GetAllDeals();
               result = _groaryTillService.CalculateTotal(deals);
              
            }
            catch (Exception)
            {

                return BadRequest();
            }
            return Ok(new {Total= _groaryTillService.ConvertToAws(result) });
        }
    }
}
