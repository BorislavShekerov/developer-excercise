package com.exercise.cloudruid.utils.mappers;

import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.utils.dtos.GroceriesInOutDto;
import com.exercise.cloudruid.utils.exceptions.InvalidPriceFormatException;
import org.springframework.stereotype.Component;

@Component
public class GroceriesMapper {

    public Groceries dtoToGroceries(GroceriesInOutDto dto) {
        String[] price = dto.getPrice().split(" ");
        Groceries groceries = new Groceries();
        groceries.setName(dto.getName());
        if (price[1].equals("aws"))
            groceries.setPrice((int) (Double.parseDouble(price[0]) * 100));
        else if (price[1].equals("c"))
            groceries.setPrice(Integer.parseInt(price[0]));
        else throw new InvalidPriceFormatException("Price format should be the following: 1,34 aws or 50 c");
        return groceries;
    }

    public GroceriesInOutDto groceriesToDto(Groceries item) {
        return new GroceriesInOutDto(item.getName(), item.getPrice() / 100 +" aws");
    }
}
