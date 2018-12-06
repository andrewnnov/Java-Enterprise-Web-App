package ru.javawebinar.topjava.util;

//HomeWork 00

/*Реализовать метод UserMealsUtil.getFilteredWithExceeded:
        -  должны возвращаться только записи между startTime и endTime
        -  поле UserMealWithExceed.exceed должно показывать,
        превышает ли сумма калорий за весь день параметра метода caloriesPerDay

        Т.е UserMealWithExceed - это запись одной еды, но поле exceeded будет одинаково для всех записей за этот день.

        - Проверьте результат выполнения ДЗ (можно проверить логику в http://topjava.herokuapp.com , список еды)
        - Оцените Time complexity вашего алгоритма, если он O(N*N)- попробуйте сделать O(N).

*/

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceeded.forEach(m-> System.out.println(m));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            //getOrDefault if map don't have values we except default - 0
            caloriesSumPerDate.put(mealDate, caloriesSumPerDate.getOrDefault(mealDate, 0) + meal.getCalories());
        }

        //test
        List<UserMealWithExceed> mealExceeded = new ArrayList<>();
        for (UserMeal meal: mealList) {
            LocalDateTime dateTime = meal.getDateTime();
            if(TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                mealExceeded.add(new UserMealWithExceed(dateTime, meal.getDescription(), meal.getCalories(),
                        caloriesSumPerDate.get(dateTime.toLocalDate()) > caloriesPerDay));
            }

        }
        return mealExceeded;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList,
                                                                          LocalTime startTime,
                                                                          LocalTime endTime,
                                                                          int caloriesPerDay){
        Map<LocalDate, Integer> caloriesSumByDay = mealList.stream().collect(Collectors
                .groupingBy(um -> um.getDateTime().toLocalDate(), Collectors.summingInt(um -> um.getCalories())));


        return mealList.stream().filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um-> new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(),
                        caloriesSumByDay.get(um.getDateTime().toLocalDate())> caloriesPerDay))
                .collect(Collectors.toList());

    }
}
