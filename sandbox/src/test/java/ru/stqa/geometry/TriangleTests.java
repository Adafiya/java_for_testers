package ru.stqa.geometry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TriangleTests {


  @Test
    //Тест на площадь треугольника
  void canAreaTriangle() {
    var s = new Triangle(5.0, 5.0, 5.0);
    Assertions.assertEquals(10.83, s.getS());
  }

  @Test
    //Тест на периметр треугольника
  void canPerimeterTriangle() {
    var s = new Triangle(5.0, 5.0, 5.0);
    Assertions.assertEquals(15.0, s.getP());
  }

  @Test
    //Тест на треугольник с отрицательной стороной
  void cannotCreateTriangleWithNegativeSide() {
    try {
      new Triangle(-5.0, 5.0, 5.0);
      Assertions.fail();
    } catch (IllegalArgumentException exception) {
      //OK
    }
  }

  @Test
    //Тест на нарушение неравенства треугольника (сумма двух любых сторон должна быть не меньше третьей стороны)
  void cannotCreateTriangleViolationInequality() {
    try {
      new Triangle(25.0, 5.0, 5.0);
      Assertions.fail();
    } catch (IllegalArgumentException exception) {
      //OK
    }
  }
}
