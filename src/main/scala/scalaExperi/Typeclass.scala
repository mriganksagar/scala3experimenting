package scalaExperi

/*
    I will be creating a typeclass to provide toJason method

    I will be using given instance to keep some state as well beside methods

    indentation will be kept in given instance
 */

trait ToJason[A]:
  extension (a: A) def toJason: String
  var indentation = "  " // 2 spaces
  def changeIndentationTo4 = indentation = "    " // change indentation to 4
  def changeIndentationTo2 = indentation = "  "

case class Person(name: String, age: Int)

given ToJason[Person] with
  extension (a: Person) def toJason = s"""|${a.getClass.toString()} {
                                            |${indentation} name : ${a.name}
                                            |${indentation} age : ${a.age} 
                                            |}""".stripMargin

object DemoTypeClass extends App {
  val p = Person("alok", 43)
  println(p.toJason) // indentation is 2 spaces now

  summon[ToJason[Person]].changeIndentationTo4

  println(p.toJason) // indentation will be 4 spaces now
  // just to prove that the indentation will be changed for a new Person as well
  val p2 = Person("aditya", 38) // 4 spaces
  println(p2.toJason)
}
