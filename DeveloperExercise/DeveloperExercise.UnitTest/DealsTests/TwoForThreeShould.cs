using Core.Discounts;
using Data.Enums;
using Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection.Metadata.Ecma335;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace DeveloperExercise.UnitTest.DealsTests
{
    public class TwoForThreeShould
    {
        [Fact]
        public void ReturnTheCorrectSum()
        {
            //Arrange
            var deal = new TwoForThree();
            var sum = 0;
            List<GroseryItem> scannedItems = new List<GroseryItem>
            { new GroseryItem { Name="apple",PriceInClouds=50},
              new GroseryItem { Name="banana",PriceInClouds=40},
             new GroseryItem { Name="banana",PriceInClouds=40},
             new GroseryItem { Name="tomato",PriceInClouds=30},
             new GroseryItem { Name="banana",PriceInClouds=40},
             };
            List<SpecialDeal> specialDeals = new List<SpecialDeal> {
            new SpecialDeal{ DealType=DealTypes.BuyOneGetOneHalfPrice,
                Items=new List<GroseryItem>{ new GroseryItem { Name = "potato", PriceInClouds = 26 } } },
            new SpecialDeal{DealType=DealTypes.TwoForThree,
                Items=new List<GroseryItem>
                   {new GroseryItem { Name="apple",PriceInClouds=50},
                    new GroseryItem { Name="banana",PriceInClouds=40},
                    new GroseryItem { Name="tomato",PriceInClouds=30}}}
            };
            //Act
            var result=deal.ApplyDiscount( scannedItems,  specialDeals,  sum);
            //Assert

            Assert.Equal(160, result);

        }
    }
}
