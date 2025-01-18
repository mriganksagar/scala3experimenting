package dean_wampler_book


/* 
    Enum Gives a definition or way to create ADTs.

    An example to show usage of Enum, 
    we will create a enum for week

    Ordinal - number assigned from 0
    toString - gives its name

    Enum can also take value parameters same as class
 */

enum Week {
    case Mon, Tue, Wed, Thu, Fri, Sat, Sun 
}

enum WeekDay(val fullname: String) {
    case Mon extends WeekDay("Monday")
    case Tue extends WeekDay("Tuesday")
    case Wed extends WeekDay("Wednesday")
    case Thu extends WeekDay("Thursday")
    case Fri extends WeekDay("Friday")
    case Sat extends WeekDay("Saturday")
    case Sun extends WeekDay("Sunday")
}
object EnumerationDemo extends App{
    
    println( Week.Fri.toString())
    println( Week.Fri.ordinal)


    println( WeekDay.Thu.toString)
    println( WeekDay.Sun.fullname)
    println(WeekDay.Wed.ordinal)
}


/* 
    Enum is a syntax sugar over sealed abstract classes or heirarchies

    Sealed keyword restrict extending trait or class beyond the file


    lets create a algebraic data type, a Tree
    Tree that has either a branch or a leaf, a branch will have two branched trees
 */

// Implementation through sealed 
object SealedADT{
    sealed trait Tree[T]

    final case class Branch[T](left: Tree[T], right: Tree[T]) extends Tree[T]
    final case class Leaf[T](value: T) extends Tree[T]
}


object EnumADT {

    enum Tree[T]{
        case Branch(left: Tree[T], right: Tree[T])
        case Leaf(value: T)
    }
}


object ADTsDemo extends App{
    val tree1 = {
        import SealedADT._
        Branch(Leaf(1), Branch(Leaf(2), Branch(Branch(Leaf(3), Leaf(4)), Leaf(5))))
    }

    val tree2 = {
        import SealedADT._
        Branch(Leaf(1), Branch(Leaf(2), Branch(Branch(Leaf(3), Leaf(4)), Leaf(5))))
    }

    println(tree1)
    println(tree2)
}