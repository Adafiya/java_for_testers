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

  @Test
    //Тест на сравнение одинаковых треугольников
  void testEqualityIdenticalTriangles(){
    var s1 = new Triangle(5.0,6.0,7.0);
    var s2 = new Triangle(5.0,6.0,7.0);
    Assertions.assertTrue(s1.equals(s2));
  }

  @Test
    //Тест на сравнение треугольников с равными сторонами
  void testEqualityTrianglesWithEqualSides(){
    var s1 = new Triangle(5.0,6.0,7.0);
    var s2 = new Triangle(7.0,5.0,6.0);
    Assertions.assertTrue(s1.equals(s2));
  }

@Test
  //Тест треугольника с не равными сторонами
  void testNotEquality(){
    var s1 = new Triangle(5.0,6.0,7.0);
    var s2 = new Triangle(6.0,6.0,7.0);
    Assertions.assertNotEquals(s1,s2);
  }
}
