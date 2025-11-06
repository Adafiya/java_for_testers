package ru.stqa.geometry;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Geometry {

  public static void main(String[] args) {
    Supplier<Triangle> randomTriangle = () -> new Triangle(new Random().nextDouble(10), 5.0, 6.0);
    var triangles = Stream.generate(randomTriangle).limit(5);
    // var triangles = Stream.of(new Triangle(5.0, 6.0, 7.0), new Triangle(5.0, 5.0, 5.0), new Triangle(1.0, 3.0, 4.0));
    //for (Triangle triangle : triangles) {
    //  Triangle.printAreaTriangle(triangle);
    //}

    //Consumer<Triangle> print = (triangle) -> {Triangle.printAreaTriangle(triangle);};//(параметры) -> {тело функции}
    //triangles.forEach(print);
    //triangles.forEach(Triangle::printAreaTriangle); //упрощенная строчка Consumer (2 строчки выше)
    triangles.peek(Triangle::printAreaTriangle).forEach(Triangle::printPerimeterTriangle);
    /*
    Типы forEach:
    Consumer - принимает, не возвращает
    Producer - не принимает, возвращает
    Функция (без всего(?)) - принимает и возвращает
     */

    Triangle.printPerimeterTriangle(new Triangle(5.0, 6.0, 7.0));
    Triangle.printAreaTriangle(new Triangle(5.0, 5.0, 5.0));
  }

}
