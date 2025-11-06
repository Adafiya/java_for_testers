package ru.stqa;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReverseChecks {

  @Test
  void testSqrt() {
    var input = 4.0;
    var result = Math.sqrt(input);
    var reverse = result * result;
    Assertions.assertEquals(input, reverse, 0.001); //в конце задаем желаемую погрешность
  }

  //Тест сортировки списка
  @Test
  void testSort() {
    var input = new ArrayList<>(List.of(3, 7, 4, 9, 0));
    input.sort(Integer::compareTo); //сортировка списка
    for (int i = 0; i < input.size() - 1; i++) {
      Assertions.assertTrue(input.get(i) <= input.get(i + 1));
    }
  }
}
