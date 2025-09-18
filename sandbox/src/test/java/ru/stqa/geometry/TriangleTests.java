package ru.stqa.geometry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TriangleTests {

  @Test
    //Тест на площадь треугольника
  void canAreaTriangle(){
    Assertions.assertEquals(11.0, Triangle.getS(5.0, 5.0,5.0));
  }

  @Test
    //Тест на периметр треугольника
  void canPerimeterTriangle(){
    Assertions.assertEquals(15.0, Triangle.getP(5.0, 5.0,5.0));
  }
}
