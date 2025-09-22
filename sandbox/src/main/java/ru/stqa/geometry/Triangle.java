package ru.stqa.geometry;

public class Triangle {

  public double a, b, c;

  //Конструктор
  public Triangle(double a, double b, double c) {
    this.a = a;
    this.b = b;
    this.c = c;

    if ((a < 0) || (b < 0) || (c < 0) || ((a + b) < c) || ((b + c) < a) || ((c + a) < b)) {
      throw new IllegalArgumentException("Треугольник не может быть создан");
    }
  }

  //Периметр треугольника
  static void printPerimeterTriangle(Triangle s) {
    System.out.println(
        String.format("Периметр треугольника со сторонами %f, %f, %f = %f", s.a, s.b, s.c,
            s.getP()));
  }

  public double getP() {
    return this.a + this.b + this.c;
  }

  //Площадь треугольника
  static void printAreaTriangle(Triangle s) {
    System.out.println(
        String.format("Площадь треугольника со сторонами %f, %f, %f = %f", s.a, s.b, s.c,
            s.getS()));
  }


  public double getS() {
    double p = (this.a + this.b + this.c) / 2;
    double s = Math.sqrt(p * (p - this.a) * (p - this.b) * (p - this.c));
    return Math.round(s * 100.0) / 100.0;

  }
}


