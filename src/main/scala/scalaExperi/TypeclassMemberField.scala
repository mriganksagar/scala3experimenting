package scalaExperi

/*
    I will be creating a typeclass to provide toJason method

    In given instance, I keep some data or state beside methods
    and modify this state to see if new state is reflected in objects using a single typeclass instance

    on changing state, it will be reflected
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

object DemoTypeClassMemberField extends App {
  val p = Person("alok", 43)
  println(p.toJason) // indentation is 2 spaces now

  summon[ToJason[Person]].changeIndentationTo4

  println(p.toJason) // indentation will be 4 spaces now
  // just to prove that the indentation will be changed for a new Person as well
  val p2 = Person("aditya", 38) // 4 spaces
  println(p2.toJason)
}
