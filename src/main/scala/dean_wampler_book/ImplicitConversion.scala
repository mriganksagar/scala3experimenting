package dean_wampler_book

import scala.concurrent.Future

/* 
    Typeclasses allows us to extend the functionality with given instances with extension methods
    But - 
    We might still need to convert types from one type to another

    Below is an example 
    I can't initialise salary without creating Dollar and Percentage
    But I want it to happen implicitly
 */

case class Dollar(amount: Double)

case class Percentage(value: Double)

case class Salary(gross: Dollar, bonus: Percentage)
object ImplicitConversion extends App {

    given Conversion[Double, Dollar] = d => Dollar(d)
    given Conversion[Double, Percentage] = d => Percentage(d) 
    val arunsSalary = Salary(10000.00, 12.3)

    given Conversion[Int, Dollar] = i => Dollar(i)
    val aloksSalary = Salary(9000, 9.2)

    println(arunsSalary)
    println(aloksSalary)
}
