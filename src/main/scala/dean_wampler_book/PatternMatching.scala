package dean_wampler_book

object PatternMatchingDemo {

  /*
        Demonstrating pattern matching inside for comprehensions or for loops
   */

  def demoForComprehensions: Unit = {
    val data = Seq(1 -> "one", 2 -> "two", 3 -> "three", 4.0, "five")
    // collect is a combination of map and filter
    val dataParse = data.collect { case (a, b) =>
      (b, a)
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

  /*
        Pattern matching's problems:

        Type erasure, nested types are deleted before runtime and can't be matched

        in below example, List[Int] will be matched as List[String] (it comes before in match order)
        because List type is known but inner type is not
   */

  def demoTypeErasure: Unit = {
    def matchSequence[T](seq: Seq[T]): Seq[String] = seq map {
      case i: Int           => s"Int: $i"
      case s: String        => s"String: $s"
      case ls: List[String] => s"List[String]: $ls"
      case li: List[Int]    => s"List[Int]: $li" // warning unreachable case
      case other            => s"Other: $other"
    }
    
    val aSeq = Seq(1, 2, 3, 4, "five", List(1, 2))

    println(matchSequence(aSeq))
  }

  def main(args: Array[String]): Unit = demoTypeErasure

}
