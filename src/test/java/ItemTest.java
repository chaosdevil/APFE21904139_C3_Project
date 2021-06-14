<<<<<<< HEAD
public class ItemTest {
=======
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItemTest {

    Restaurant restaurant;
    RestaurantService service;

    @Test
    public void test_getMenu_not_null() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        RestaurantService service = new RestaurantService();
        Restaurant restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Shrimp salad", 199);

        assertNotNull(restaurant.getMenu());


    }

    @Test
    public void test_return_correct_getTotalPrice() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        RestaurantService service = new RestaurantService();
        Restaurant restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Shrimp salad", 199);

        assertEquals(119+269+199, restaurant.getTotalPrice());
    }

    @Test
    public void test_return_correct_totalPrice_with_selected_item_names() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        RestaurantService service = new RestaurantService();
        Restaurant restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Shrimp salad", 199);

        List<String> itemNames = new ArrayList<>();
        itemNames.add("Sweet corn soup");
        itemNames.add("Shrimp salad");

        assertEquals(119+199, restaurant.getTotalCostOrder(itemNames));
    }
>>>>>>> 5610636f2bfb1720cd639ee089a93681177fec67
}
