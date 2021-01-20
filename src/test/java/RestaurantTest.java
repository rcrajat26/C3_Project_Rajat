import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        restaurant= getTestRestaurant("Megha darshini", "Dharwad", LocalTime.of(10, 30),
                LocalTime.of(4, 30),"Poori",45,"Pani puri",35);
        restaurant = Mockito.spy(restaurant);
        LocalTime currTime = restaurant.openingTime.plus(20, ChronoUnit.MINUTES);
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currTime);
        Assertions.assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        restaurant= getTestRestaurant("Megha darshini", "Dharwad", LocalTime.of(10, 30),
                LocalTime.of(4, 30),"Poori",45,"Pani puri",35);
        restaurant = Mockito.spy(restaurant);
        LocalTime currTime = restaurant.closingTime.plus(20, ChronoUnit.MINUTES);
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currTime);

        Assertions.assertFalse(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = getTestRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime,
                "Sweet corn soup",119,"Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = getTestRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime,
                "Sweet corn soup",119,"Vegetable lasagne", 269);


        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = getTestRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime,
                "Sweet corn soup",119,"Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    private Restaurant getTestRestaurant(String name, String place, LocalTime startTime, LocalTime endTime,
                                         String item1,int price1,String item2, int price2) {
        Restaurant restaurant =  new Restaurant(name, place, startTime, endTime);

        restaurant.addToMenu(item1,price1);
        restaurant.addToMenu(item2, price2);
        return restaurant;
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //<<<<<<<<<<<<<<<<<<<<<<<ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /*
    * As a user I should be able to view the order value
    * Parameter to be passed - list of names of items
    * Return value should be - amount of the order as int
    * if the item list is empty the sum should be zero
    * for right input sum should be displayed
    */

    public void for_two_items_poori_and_paniPuri_the_order_value_should_be_80() {
        restaurant= getTestRestaurant("Megha darshini", "Dharwad", LocalTime.of(10, 30),
                LocalTime.of(4, 30),"Poori",45,"Pani puri",35);
        restaurant = Mockito.spy(restaurant);

        Assertions.assertEquals(0,restaurant.getOrderValue("Poori","Pani puri"));
    }

    public void for_no_items_the_order_value_should_be_0() {
        restaurant= getTestRestaurant("Megha darshini", "Dharwad", LocalTime.of(10, 30),
                LocalTime.of(4, 30),"Poori",45,"Pani puri",35);
        restaurant = Mockito.spy(restaurant);

        Assertions.assertEquals(0,restaurant.getOrderValue());
    }

    public void for_one_item_poori_the_order_value_should_be_45() {
        restaurant= getTestRestaurant("Megha darshini", "Dharwad", LocalTime.of(10, 30),
                LocalTime.of(4, 30),"Poori",45,"Pani puri",35);
        restaurant = Mockito.spy(restaurant);

        Assertions.assertEquals(0,restaurant.getOrderValue("Poori"));
    }
}