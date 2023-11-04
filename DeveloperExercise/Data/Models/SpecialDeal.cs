using Data.Enums;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Data.Models
{
    public class SpecialDeal
    {
        [Key]
        public Guid Id { get; set; } = new Guid();   
        
        public virtual List<GroseryItem> Items { get; set; }=new List<GroseryItem>();
        public DealTypes DealType { get; set; }
    }
}
