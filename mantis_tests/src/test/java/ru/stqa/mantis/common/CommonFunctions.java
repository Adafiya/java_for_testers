package ru.stqa.mantis.common;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonFunctions {

  //Сгенерировать рандомный String в n символов
  public static String randomString(int n) {
    var rnd = new Random();
    Supplier<Integer> randomNumbers = () -> rnd.nextInt(26);
    var result = Stream.generate(randomNumbers)
        .limit(n)
        .map(i -> 'a' + i) //передаем i и получаем a+i
        .map(Character::toString) //преобразуем символ в строку
        .collect(Collectors.joining()); //собираем строчки вместе
    return result;
  }
}
