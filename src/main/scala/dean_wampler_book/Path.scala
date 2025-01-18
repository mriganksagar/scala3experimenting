package dean_wampler_book

import java.io.File
import scala.annotation.targetName


case class Path(value: String, separator: String = Path.defaultSeparator){ 
    def file = File(value)

    override def toString: String = file.getPath

    @targetName("concat") def / (node: String): Path = copy(value + separator + node)

    infix def append(node:String): Path = /(node)

    def appendWithoutInfix(node: String) = /(node)
}


object Path {
  val defaultSeparator = sys.props("file.separator")
}

object PathDemo extends App{

    val p1 = Path("one")
    val p2 = p1/"two"

    /* 
        Target name can be used by java to call the method, but not scala
     */
    // val fail1 = p1 concat "two"

    val p3 = p1/"twoo"/"three"

    val append1 = p1 append "four"
    //val append2 = p1 appendWithoutInfix "five" // shows warning to make it infix 
    println(append1)
}