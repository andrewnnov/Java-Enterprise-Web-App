package ru.javawebinar.topjava;

import org.junit.Test;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserMealsUtilTest {

   UserMealsUtil userMealsUtil = new UserMealsUtil();

    @Test
    public void whenWeHaveOnlyBreakfastThenOneNote() {


        List<UserMeal> mealList = new ArrayList<>();
        UserMeal userMeal1 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500);
        UserMeal userMeal2 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000);
        UserMeal userMeal3 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500);

        UserMeal userMeal4 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000);
        UserMeal userMeal5 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500);
        UserMeal userMeal6 = new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510);

        mealList.add(userMeal1);
        mealList.add(userMeal2);
        mealList.add(userMeal3);
        mealList.add(userMeal4);
        mealList.add(userMeal5);
        mealList.add(userMeal6);

        List<UserMealWithExceed> result = userMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);



        assertEquals("Завтрак", result.get(0).getDescription());
        assertEquals(500, result.get(0).getCalories());

        result.remove(0);

        assertEquals("Завтрак", result.get(0).getDescription());
        assertEquals(1000, result.get(0).getCalories());











    }

}
