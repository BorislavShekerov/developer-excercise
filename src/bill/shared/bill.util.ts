import { ItemCreateDto } from "src/item/dto/item.create.dto";
import { ValueType } from "src/item/enum/value.type.enum";
import { InputDealCreateDto } from "src/inputdeal/dto/inputdeal.create.dto";
import { TotalBill } from "../dto/total.bill.dto";
import { flatten } from "@nestjs/common";
import { DiscountType } from "../enum/discount.type.enum";

const ZERO_VALUE = 0;
const ONE_VALUE = 1;
const TWO_VALUE = 2;
const THREE_VALUE = 3;
const TEN_PERCENTAGE_DISCOUNT = 0.1;
const ONE_HUNDRED_VALUE = 100;

export const calculateTotalBill = (items: ItemCreateDto[]): number => {
  let awsBill: number = ZERO_VALUE;
  let cloudBill: number = ZERO_VALUE;
  let totalBill: number = ZERO_VALUE;

  items.forEach(item => {
    if (item.valueType === ValueType.CLOUD) {
      cloudBill += item.value;
    }
    else if (item.valueType === ValueType.AWS) {
      awsBill += item.value;
    }
  });

  cloudBill = convertBillTOdecimalPartOFAws(cloudBill);
  totalBill = sumOfAwsAndCloudBills(awsBill, cloudBill);

  return totalBill;
};

export const calculateTotalBillWithDiscounts = (
  items: ItemCreateDto[],
  inputDeals: InputDealCreateDto[]
): number => {
  let totalBill: number = ZERO_VALUE;
  let awsBill: number = ZERO_VALUE;
  let cloudBill: number = ZERO_VALUE;
  let billPair = new Map<ValueType, number>();
  let bill: TotalBill;

  if (containsInputDeal(inputDeals, DiscountType.TWO_FOR_THREE) && items.length > TWO_VALUE) {
    bill = calculate2Of3(items, inputDeals);
    billPair = bill.billPair;
    items = bill.remainderOfItems;
  }
  if (containsInputDeal(inputDeals, DiscountType.BUY_ONE_GET_ONE_HALF) && items.length > ONE_VALUE) {
    bill = calculateBuyOneGetSecondHalfPrice(items, inputDeals);
    billPair.set(ValueType.CLOUD, billPair.get(ValueType.CLOUD) + bill.billPair.get(ValueType.CLOUD));
    billPair.set(ValueType.AWS, billPair.get(ValueType.AWS) + bill.billPair.get(ValueType.AWS));
    items = bill.remainderOfItems;
  }

  cloudBill = billPair.get(ValueType.CLOUD) !== undefined ? billPair.get(ValueType.CLOUD) : ZERO_VALUE;
  awsBill = billPair.get(ValueType.AWS) !== undefined ? billPair.get(ValueType.AWS) : ZERO_VALUE;

  cloudBill = convertBillTOdecimalPartOFAws(cloudBill);
  totalBill = sumOfAwsAndCloudBills(awsBill, cloudBill);
  totalBill += calculateTotalBill(items);

  if (containsInputDeal(inputDeals, DiscountType.LOYALTY_CARD_TEN_PERCENTAGE)) {
    totalBill -= (totalBill * TEN_PERCENTAGE_DISCOUNT)
  }

  return totalBill;
};

const calculate2Of3 = (items: ItemCreateDto[], inputDeals: InputDealCreateDto[]): TotalBill => {
  let awsCloudPairBill = new Map<ValueType, number>();
  const totalBill = new TotalBill();
  let smallestCloudValue: number;
  let smalllestAwsValue: number;

  const inputDealsItems: ItemCreateDto[] = flattenInputDeals(inputDeals, DiscountType.TWO_FOR_THREE);
  let intersectionOfItems: ItemCreateDto[] = findIntersectionOfItems(items, inputDealsItems);

  if (intersectionOfItems.length > THREE_VALUE) {
    intersectionOfItems = intersectionOfItems.slice(ZERO_VALUE, THREE_VALUE);
  }

  items = spliceItems(intersectionOfItems, items);
  totalBill.remainderOfItems = items;
  
  smallestCloudValue = findSmallestValue(intersectionOfItems, ValueType.CLOUD);
  smalllestAwsValue = findSmallestValue(intersectionOfItems, ValueType.AWS);
  
  awsCloudPairBill = sumAwsAndCloudValues(intersectionOfItems);
  
  awsCloudPairBill.set(ValueType.CLOUD, awsCloudPairBill.get(ValueType.CLOUD) - checkSmallestValue(smallestCloudValue));
  awsCloudPairBill.set(ValueType.AWS, awsCloudPairBill.get(ValueType.AWS) - checkSmallestValue(smalllestAwsValue));

  totalBill.billPair = awsCloudPairBill;

  return totalBill;
};

const calculateBuyOneGetSecondHalfPrice = (items: ItemCreateDto[], inputDeals: InputDealCreateDto[]): TotalBill => {
  let awsCloudPairBill = new Map<ValueType, number>();
  const totalBill = new TotalBill();

  const inputDealsItems: ItemCreateDto[] = flattenInputDeals(inputDeals, DiscountType.BUY_ONE_GET_ONE_HALF);
  let intersectionOfItems: ItemCreateDto[] = findIntersectionOfItems(items, inputDealsItems);
  intersectionOfItems[ONE_VALUE].value = intersectionOfItems[ONE_VALUE].value / TWO_VALUE;

  awsCloudPairBill = sumAwsAndCloudValues(intersectionOfItems);

  items = spliceItems(intersectionOfItems, items);
  totalBill.remainderOfItems = items;
  totalBill.billPair = awsCloudPairBill;
  
  return totalBill;
};

const sumAwsAndCloudValues = (intersectionOfItems: ItemCreateDto[]): Map<ValueType, number> => {
  const awsCloudPairBill = new Map<ValueType, number>();
  awsCloudPairBill.set(ValueType.CLOUD, ZERO_VALUE);
  awsCloudPairBill.set(ValueType.AWS, ZERO_VALUE);

  intersectionOfItems.forEach(item => {
    if (item.valueType === ValueType.CLOUD) {
      awsCloudPairBill.set(ValueType.CLOUD, awsCloudPairBill.get(ValueType.CLOUD) + item.value);
    }
    else if (item.valueType === ValueType.AWS) {
      awsCloudPairBill.set(ValueType.AWS, awsCloudPairBill.get(ValueType.AWS) + item.value);
    }
  });

  return awsCloudPairBill;
};

const containsInputDeal = (inputDeals: InputDealCreateDto[], nameOfDeal: string): boolean => {
  return inputDeals.some(inputDeal => inputDeal.nameOfDeal === nameOfDeal);
};

const convertBillTOdecimalPartOFAws = (cloudBill: number) => {
  if (cloudBill !== ZERO_VALUE) {
    cloudBill /= ONE_HUNDRED_VALUE;
  }
  return cloudBill;
};

const sumOfAwsAndCloudBills = (awsBill: number, cloudBill: number): number => {
  return awsBill + cloudBill;
};

const flattenInputDeals = (inputDeals: InputDealCreateDto[], typeOfDiscount: DiscountType): ItemCreateDto[] => {
  return flatten(inputDeals.filter(inputDeal => inputDeal.nameOfDeal === typeOfDiscount)
    .map(inputDeal => inputDeal.items));
};

const findIntersectionOfItems = (items: ItemCreateDto[], inputDealsItems: ItemCreateDto[]): ItemCreateDto[] => {
  return items.filter((item => inputDealsItems.some(inputDeal => inputDeal.name === item.name)));
};

const spliceItems = (intersectionOfItems: ItemCreateDto[], items: ItemCreateDto[]): ItemCreateDto[] =>{
  let splicedItems = [...items];
  intersectionOfItems.forEach(item => {
    const indexOfElement = splicedItems.indexOf(item);
    splicedItems.splice(indexOfElement, ONE_VALUE);
  });
  return splicedItems;
};

const findSmallestValue = (intersectionOfItems: ItemCreateDto[], valueType: ValueType) => {
  return Math.min(...intersectionOfItems.filter(item => item.valueType === valueType).map(item => item.value));
};

const checkSmallestValue = (smallestValue: number): number => {
  return isFinite(smallestValue) ? smallestValue : ZERO_VALUE;
};

export const composeTotalBill = (totalBill: number): string => {
  let composedTotalBill: string;
  let awsBill: number = Math.floor(totalBill);
  let cloudBill: number = parseInt((parseFloat((totalBill % 1).toFixed(2)) * 100).toFixed(0));
  
  if (cloudBill !== 0 && awsBill !== 0) {
    composedTotalBill = `${awsBill} aws and ${cloudBill} clouds`;
  }
  else if (cloudBill === 0 && awsBill !== 0) {
    composedTotalBill = `${awsBill} aws`;
  }
  else if (cloudBill !== 0 && awsBill === 0) {
    composedTotalBill = `${cloudBill} clouds`;
  }
 
  return composedTotalBill;
};

