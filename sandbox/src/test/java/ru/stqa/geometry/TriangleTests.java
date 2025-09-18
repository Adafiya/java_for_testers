package ru.stqa.geometry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TriangleTests {


  @Test
    //Тест на площадь треугольника
  void canAreaTriangle() {
    var s = new Triangle(5.0, 5.0, 5.0);
    Assertions.assertEquals(11.0, s.getS());
  }

  @Test
    //Тест на периметр треугольника
  void canPerimeterTriangle() {
    var s = new Triangle(5.0, 5.0, 5.0);
    Assertions.assertEquals(15.0, s.getP());
  }
}
