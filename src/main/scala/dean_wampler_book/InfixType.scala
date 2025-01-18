package dean_wampler_book

import scala.annotation.targetName

case class <+>[A, B](a: A, b: B)

infix case class tie[A, B](a: A, b: B)

object InfixTypeDemo extends App {
  // this won't work , <+> not a method of Int
//   val ab1: Int <+> String = 1 <+> "one"
  val ab2: Int <+> String = <+>(1, "one") // will work
  // this won't work too
//   val ab3: Int tie String = tie 1, "one"

  /*
        one thing to notice
        infix allows us to create type using infix notation if not an instance

        Int tie String means class Tie[Int, String]
   */
  val ab4: Int tie String = tie(1, "one")
}
