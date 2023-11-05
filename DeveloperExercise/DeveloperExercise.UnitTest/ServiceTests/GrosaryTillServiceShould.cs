using Core.Discounts;
using Core.Interfaces;
using Core.Services;
using Data.Enums;
using Data.Models;
using Moq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace DeveloperExercise.UnitTest.ServiceTests
{
    public class GrosaryTillServiceShould
    {
        [Fact]
        public void ReturnTheCorrectSum()
        {
            //Arrange
            var scanned = new List<GroseryItem>();
            var specialDeals = new List<SpecialDeal>();
            var discount1 = new Mock<IDiscount>();
            var sum = 0;
            discount1.Setup(p => p.ApplyDiscount(ref scanned,ref specialDeals,ref sum));
            var service = new GrosaryTillService();

            //Act
           

            //Assert
            Assert.Equal(199,sum);
        }
    }
}
