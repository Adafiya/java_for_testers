package ru.stqa.geometry;

public class Triangle {

//Периметр треугольника
    static void printPerimeterTriangle(double a, double b, double c){
      System.out.println(String.format("Периметр треугольника со сторонами %f, %f, %f = %f", a, b, c, getP(a, b, c)));
    }

  public static double getP(double a, double b, double c) {
    return a + b + c;
  }

  //Площадь треугольника
    static void printAreaTriangle(double a, double b, double c){
      System.out.println(String.format("Площадь треугольника со сторонами %f, %f, %f = %f", a, b, c, getS(a, b, c)));
    }

  public static double getS(double a, double b, double c) {
    double p = getP(a, b, c) / 2;
    double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));
    return Math.round(s);
  }
}


