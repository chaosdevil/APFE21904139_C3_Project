import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    @Mock
    Restaurant restaurant;

    @InjectMocks
    RestaurantService service = new RestaurantService();

    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    public void initSpy() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("18:00:00");
        restaurant = Mockito.spy(new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime));
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Shrimp salad", 199);
        restaurant.addToMenu("Chinese noodles", 50);
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {

        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("13:00:00"));

        assertTrue(restaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {

        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("09:00:00"));

        assertFalse(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void getTotalPrice_should_return_correct_price() {

        assertEquals(119+269+199+50, restaurant.getTotalPrice());

    }

    @Test
    public void getMenu_should_be_not_null() {

        assertNotNull(restaurant.getMenu());

    }

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1, restaurant.getMenu().size());

    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1, restaurant.getMenu().size());

    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        
        assertThrows(itemNotFoundException.class, () -> restaurant.removeFromMenu("French fries"));

    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // <<<<<<<<<<<<<<<<<<<<<<GETTOTALCOST>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void test_getMenu_not_null() {

        assertNotNull(restaurant.getMenu());

    }

    @Test
    public void test_return_correct_getTotalPrice() {

        assertEquals(119+269+199+50, restaurant.getTotalPrice());

    }

    @Test
    public void test_return_correct_totalPrice_with_selected_item_names() throws itemNotFoundException {

        assertEquals(119+199, restaurant.getTotalCostOrder("Sweet corn soup", "Shrimp salad"));
        assertEquals(119+50, restaurant.getTotalCostOrder("Sweet corn soup", "Chinese noodles"));
        assertEquals(199+269, restaurant.getTotalCostOrder("Shrimp salad", "Vegetable lasagne"));
        assertEquals(199+269+50, restaurant.getTotalCostOrder("Shrimp salad", "Vegetable lasagne", "Chinese noodles"));

        String[] items = {"Shrimp salad", "Vegetable lasagne", "Chinese noodles"};
        assertEquals(199+269+50, restaurant.getTotalCostOrder(items));

    }

    @Test
    public void test_getTotalCostOrder_throws_itemNotFoundException() {

        assertThrows(itemNotFoundException.class, () -> restaurant.getTotalCostOrder("Shrimp salad", "Vegetable lasagne", "Thai noodles"));
        assertThrows(itemNotFoundException.class, () -> restaurant.getTotalCostOrder("Pizza", "Chinese noodles"));

    }
    // <<<<<<<<<<<<<<<<<<<<<<GETTOTALCOST>>>>>>>>>>>>>>>>>>>>>
}