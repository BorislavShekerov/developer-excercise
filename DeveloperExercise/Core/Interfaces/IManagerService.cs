using Core.Dtos.Request;
using Core.Dtos.Response;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Core.Interfaces
{
    public interface IManagerService
    {
        Task<ManagerResDto> Register(ManagerRegisterDto manger);
        Task<ManagerResDto> Login(ManagerLogInDto manager);
    }
}
