function removeA(arr) {
    var what, a = arguments, L = a.length, ax;
    while (L > 1 && arr.length) {
        what = a[--L];
        while ((ax = arr.indexOf(what)) !== -1) {
            arr.splice(ax, 1);
        }
    }
    return arr;
}

const vueApp = Vue.createApp({
    data() {
        return {
            groceries: [],
            twoForThree: [],
            halfPriceOnSecond: []
        }
    },

    computed: {
        tillTotal() {
            let total = 0;
            for (let i = 0; i < this.groceries.length; i++) {
                total += this.groceries[i].price;
            }
            return total;
        }
    },

    methods: {
        addGrocery(groceryName, price) {
            this.groceries.push({
                name: groceryName,
                price: price
            })
            this.filterTwoForThree(groceryName);
            this.filterAddHalfPriceOnSecond(groceryName);
        },

        addTwoForThree(groceryName) {
            if (this.twoForThree.includes(groceryName)) {
                removeA(this.twoForThree, groceryName);
            } else {
                this.twoForThree.push(groceryName)
            }
            this.filterTwoForThree(groceryName);
        },

        addHalfPriceOnSecond(groceryName) {
            if (this.halfPriceOnSecond.includes(groceryName)) {
                removeA(this.halfPriceOnSecond, groceryName);
            } else {
                this.halfPriceOnSecond.push(groceryName)
            }
            this.filterAddHalfPriceOnSecond(groceryName);
        },

        filterAddHalfPriceOnSecond(groceryName) {
            let checker = true;
            let checker2 = true;
            let counter = 0;
            let counter2 = 0;
            let price = 0;
            let price2 = 0;
            if (this.halfPriceOnSecond.includes(groceryName)) {
                for (let i = 0; i < this.groceries.length; i++) {

                    if (this.groceries[i].name == groceryName) {
                        if (checker) {
                            price = this.groceries[i].price;
                            checker = false;
                        }

                        counter++;
                        if (counter == 2) {
                            this.groceries[i].price = Math.floor(price / 2);
                            checker = true;
                            counter = 0;
                        }
                    }
                }
            } else {
                for (let i = 0; i < this.groceries.length; i++) {
                    if (this.groceries[i].name == groceryName) {
                        if (checker2) {
                            price2 = this.groceries[i].price;
                            checker2 = false;
                        }
                        counter2++;
                        if (counter2 == 2) {
                            this.groceries[i].price = price2;
                            counter2 = 0;
                        }
                    }
                }
            }
        },

        filterTwoForThree(groceryName) {
            let checker = true;
            let price = 0;
            let counter2 = 0;
            let counter = 0;

            if (this.twoForThree.includes(groceryName)) {
                for (let i = 0; i < this.groceries.length; i++) {
                    if (this.groceries[i].name == groceryName) {
                        counter++;
                        if (counter == 3) {
                            this.groceries[i].price = 0;
                            counter = 0;
                        }
                    }
                }
            } else {
                for (let i = 0; i < this.groceries.length; i++) {

                    if (this.groceries[i].name == groceryName) {
                        if (checker) {
                            price = this.groceries[i].price;
                            checker = false;
                        }
                        counter2++;
                        if (counter2 == 3) {
                            this.groceries[i].price = price;
                            counter2 = 0;
                        }
                    }
                }
            }
        }

    }
});

vueApp.mount('#app')