using Core.Discounts;
using Data.Enums;
using Data.Models;
using Xunit;

namespace DeveloperExercise.UnitTest.DealsTests
{

    public class BuyOneGetOneHalfShould
    {
        [Fact]
        public void ReturnTheCorrectSum()
        {
            //Arrange
            var deal = new BuyOneGetOneHalf();
            var sum = 0;
            List<GroseryItem> scannedItems = new List<GroseryItem>
            { new GroseryItem { Name="apple",PriceInClouds=50},
              new GroseryItem { Name="banana",PriceInClouds=40},
             new GroseryItem { Name="banana",PriceInClouds=40},
             new GroseryItem { Name="potato",PriceInClouds=26},
             new GroseryItem { Name="tomato",PriceInClouds=30},
             new GroseryItem { Name="banana",PriceInClouds=40},
             new GroseryItem { Name="potato",PriceInClouds=26}};
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
            deal.ApplyDiscount(ref scannedItems, ref specialDeals, ref sum);
            //Assert
            Assert.Equal(39, sum);
        }

    }
}