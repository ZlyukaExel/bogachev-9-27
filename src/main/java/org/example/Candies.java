package org.example;

import java.util.ArrayList;
import java.util.List;

class Candy {
    String name;
    double price;

    public Candy(String name, double pricePerKg) {
        this.name = name;
        this.price = pricePerKg;
    }
}

public class Candies {
    public static String Count(List<Candy> candies, double money) {
        sortList(candies);

        List<Candy> chosenCandies = new ArrayList<>();
        double moneySpent = 0;

        for (Candy candy : candies) {
            if (moneySpent + candy.price <= money) {
                chosenCandies.add(candy);
                moneySpent += candy.price;
            }
        }

        if (chosenCandies.isEmpty()){
            return "Поднакопи-ка лучше денег и приходи в другой раз...";
        }

        StringBuilder result = new StringBuilder().append("Ты купил: ");
        for (int i = 0; i < chosenCandies.size(); i++) {
            Candy candy = chosenCandies.get(i);
            result.append(candy.name).append(" за ").append(candy.price).append(" рублей");
            if (i != chosenCandies.size() - 1)
                result.append(", ");
        }
        result.append(". Твой остаток: ").append(money - moneySpent);

        System.out.println(result);
        return result.toString();
    }

    private static void sortList(List<Candy> candies) {
        for (int i = 0; i < candies.size() - 1; i++) {
            for (int j = 0; j < candies.size() - 1 - i; j++) {
                if (candies.get(j).price < candies.get(j + 1).price) {
                    Candy temp = candies.get(j);
                    candies.set(j, candies.get(j + 1));
                    candies.set(j + 1, temp);
                }
            }
        }
    }
}
