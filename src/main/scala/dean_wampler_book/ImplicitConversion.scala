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
object ImplicitConversionDemo extends App {

    given Conversion[Double, Dollar] = d => Dollar(d)
    given Conversion[Double, Percentage] = d => Percentage(d) 
    val arunsSalary = Salary(10000.00, 12.3)

    given Conversion[Int, Dollar] = i => Dollar(i)
    val aloksSalary = Salary(9000, 9.2)

    println(arunsSalary)
    println(aloksSalary)
}

// complex number case class
case class ComplexNumber(r: Int, i: Int ){
    def + (other: ComplexNumber) = ComplexNumber(r + other.r, i + other.i)
}

object ComplexNumber{
    given fromTuple: Conversion[(Int, Int), ComplexNumber] = (a, b) => ComplexNumber(a,b)  
}

object ImplicitConversionDemo2 extends App {
    ComplexNumber(1, -5) + (4,5)

    /*
        Below line won't work without this given import though above line worked without
        WHY ? 
        i think in first cases scala would look for Converion[(Int, Int), ComplexNumber]
        in companion of Conversion,  A, and B and also their supertypes' companions

        by this logic it should find given in object ComplexNumber
        but i think with (4,5) + ComplexNumber(5,5),
        scala doesn't make guesses for conversion (cause conversion has pitfalls)
        
        but it only looks for methods or extension method instead of converting
        
        WHY Importing ComplexNumber.given Fixes It ?
        By importing ComplexNumber.given, we bring the fromTuple conversion into the local scope,
        making it available as a potential candidate for the extension method search
     */
    import ComplexNumber.given
    (4, 5) + ComplexNumber(1, -5)
}
