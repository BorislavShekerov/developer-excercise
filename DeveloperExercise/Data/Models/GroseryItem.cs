using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Data.Models
{
    public class GroseryItem
    {
        [Key]
        public Guid Id { get; set; }=new Guid();
        [MaxLength(30)]
        public string Name { get; set; }
        public int PriceInClouds { get; set; }
    }
}
