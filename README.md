# Developer Exercise

This exercise is designed to demonstrate a candidate's abilities across a range of competencies in software development with Node + Javascript/Typescript (extra points for types).

## Instructions

1. Fork the repository
2. Implement a solution of the `Requirements` (feel free to use any helper packages/frameworks that you think think will be of help)
3. Share the forked repository

## "Business" Requirements

- Create a basic Groceries Shop till which can `scan` fruits and vegetables of different types, producing a numeric result/bill in the end. Assume the currency is called `aws` and there is `100 cloud ('c' for short)` in 1 aws
- Apart from simply adding the value of each product, the till should contain logic for the following special deals:
  - `2 for 3` - for a given selection of items (customer buys 3 items but only pays for the value of 2 of them, the cheapest one is free). In case there are more than 3 items that are included in the `2 for 3` deal, the first 3 items are included.
    Example Deal ["banana", "orange", "tomato"], example items scanned ["banana", "orange", "orange", "tomato"] - the tomato is not included in the discount
  - `buy 1 get 1 half price` - for a given selection of items (if the customer buys a given product under such offer, they receive a 50% reduction in the price of a second item of the same type)
  - `Loyalty Card 10% discount` - given a customer own a Loyalty Card they get a 10% discount of they total bill
- The till should be `programmable`:
  - The list of items supported by the till should be extensible i.e. it should be possible to add a new item with a given "value", "name"
  - Once a new item is added to the till, the administrator should be able to add it to any of the 3 special deals defined above

### Example:

Input groceries:

- "apple" - 50c
- "banana" - 40c
- "tomato" - 30c
- "potato" - 26c

Input deals:
`2 for 3` - ["apple", "banana", "tomato"]
`buy 1 get 1 half price` - potato

Example scanned customer basket: "apple", "banana", "banana", "potato", "tomato", "banana", "potato"

Expected Output: `1 aws and 99 clouds`

## Grading

You will be scored on the following:

- Code cleanliness and ease of understandability
- Code reusability
- Code modularity (i.e. ease of extension)
- Documentation

## Demonstrable concepts

You are free to make your solution to this exercise as simple or as complicated as you want based off the above criteria. At least three (3) of the below concepts _must_ be demonstrated in your implementation, but we recommend implementing as many as possible to adequately demonstrate your abilities.

- Caching layer
- Database storage
- Object-oriented patterns
- Containerization
- Unit testing
- Integration testing
- RESTful API
- User interface or Application CLI
