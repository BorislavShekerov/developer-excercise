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
        public void CalculateTotalReturnTheCorrectSum()
        {
            //Arrange
            var scanned = new List<GroseryItem>();
            var specialDeals = new List<SpecialDeal>();
            var discount1 = new Mock<BuyOneGetOneHalf>();
            var sum = 0;
            discount1.Setup(p => p.ApplyDiscount( scanned, specialDeals, sum)).Returns(39);
            var discount2=new Mock<TwoForThree>();
            discount2.Setup(p => p.ApplyDiscount(scanned, specialDeals, 39)).Returns(199);
            var service = new GrosaryTillService(discount1.Object,discount2.Object);

            //Act
            var result=service.CalculateTotal(new List<SpecialDeal>());

            //Assert
            Assert.Equal(199,result);
        }
        [Fact]
        public void ScanItemsWorkCorrectly()
        {
            //Arrange
            var discount1 = new Mock<BuyOneGetOneHalf>();
            var discount2 = new Mock<TwoForThree>();
            var service = new GrosaryTillService(discount1.Object,discount2.Object);

            //Act
            service.ScanItem(new GroseryItem { Name = "banana", PriceInClouds = 40 });
            //Assert
            Assert.Equal(1,service.scannedCount);
        }
        [Fact]
        public void ConvertToAwsWorksCorrectly()
        {
            //Arrange
            var discount1 = new Mock<BuyOneGetOneHalf>();
            var discount2 = new Mock<TwoForThree>();
            var service = new GrosaryTillService(discount1.Object, discount2.Object);

            //Act
            var result = service.ConvertToAws(199);
            //Assert
            Assert.Equal(1.99,result);
        }
    }
}
