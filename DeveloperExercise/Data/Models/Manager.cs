using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Data.Models
{
    public class Manager
    {
        [Key]
        public Guid Id { get; set; } = new Guid();
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public  string Email { get; set; }
        public byte[] PasswordHash { get; set; }
        public byte[] PasswordSalt { get; set; }
    }
}
