using Core.Dtos.Request;
using Core.Dtos.Response;
using Core.Interfaces;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Net;
using System.Security.Claims;

namespace DeveloperExerciseApi.Controllers
{
    public class ManagerController : Controller
    {
        private readonly IConfiguration _configuration;
        private readonly IManagerService _managerService;
        public ManagerController(IConfiguration configuration, IManagerService managerService)
        {
            _configuration = configuration;
            _managerService = managerService;
        }
        [HttpPost("Register")]
        [ProducesResponseType(typeof(ManagerResDto), StatusCodes.Status200OK)]
        public async Task<IActionResult> Register(ManagerRegisterDto man)
        {
            ManagerResDto result = new ManagerResDto();
            try
            {
                result = await _managerService.Register(man);
            }
            catch (Exception)
            {

                return BadRequest();
            }
            result.Token = CreateToken(result);
            return Ok(result);
        }
        [HttpPost("login")]
        [ProducesResponseType(typeof(ManagerResDto), StatusCodes.Status200OK)]
        public async Task<IActionResult> Login(ManagerLogInDto man)
        {
            ManagerResDto result = new ManagerResDto();
            try
            {
                result = await _managerService.Login(man);
            }
            catch (Exception)
            {

                return BadRequest();
            }
            result.Token = CreateToken(result);
            return Ok(result);
        }
        [HttpPost("addItem")]
        [ProducesResponseType(typeof(ItemDtoRes), StatusCodes.Status200OK)]
        [Authorize(Roles = "Manager")]
        public async Task<IActionResult> AddItem(ItemDtoReq item)
        {
            ItemDtoRes result=new ItemDtoRes();
            try
            {
                 result = await _managerService.AddItem(item);
            }
            catch (Exception)
            {

                return BadRequest();
            }
            return Ok(result);
        }
        [HttpPost("addItemToDeal")]
        [ProducesResponseType(typeof(ItemDtoRes), StatusCodes.Status200OK)]
         [Authorize(Roles = "Manager")]
        public async Task<IActionResult> AddItemToDeal(string itemName,int dealType)
        {
            ItemDtoRes result=new ItemDtoRes();
            try
            {
                result = await _managerService.AddItemToDeal(itemName, dealType);
            }
            catch (Exception)
            {

                BadRequest();
            }
            return Ok(result);  
        }
        private string CreateToken(ManagerResDto client)
        {
            List<Claim> claims = new List<Claim>
            {
                new Claim(ClaimTypes.Role, client.Role)
            };

            var key = new SymmetricSecurityKey(System.Text.Encoding.UTF8.GetBytes(_configuration.GetSection("AppSettings:Token").Value));

            var credentials = new SigningCredentials(key, SecurityAlgorithms.HmacSha512Signature);

            var token = new JwtSecurityToken(
                claims: claims,
                expires: DateTime.Now.AddDays(2),
                signingCredentials: credentials);

            var jwt = new JwtSecurityTokenHandler().WriteToken(token);

            return jwt;
        }
    }
}
