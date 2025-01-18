package dean_wampler_book


object PatternMatchingDemo {

    /* 
        Demonstrating pattern matching inside for comprehensions or for loops
     */

    def demoForComprehensions: Unit = {
        val data = Seq(1 -> "one", 2 -> "two", 3 -> "three", 4.0, "five")
        // collect is a combination of map and filter
        val dataParse = data.collect{
            case (a, b) => (b, a)
        }

        // we can do same with for comprehensions
        // un matched case acts as a filter
        val dataParse2 = for {
            case (a, b) <- data
        } yield (b, a)
        println(dataParse)

        // we can use case match with for do loop as well

        for {
            case (a, b) <- data
        } do println(s"$a element is $b")
    }

    def main(args: Array[String]): Unit =  demoForComprehensions
    
}