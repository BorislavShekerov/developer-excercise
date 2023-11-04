﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Core.Dtos.Response
{
    public class ManagerResDto
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public  string Email { get; set; } 
        public string Token { get; set; }
        public string Role { get; set; } = "Manager";
    }
}
