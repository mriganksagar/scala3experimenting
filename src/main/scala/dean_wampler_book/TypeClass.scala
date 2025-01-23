package dean_wampler_book

trait Semigroup[T] {
  extension (a: T)
    infix def combine(b: T): T
    def <+>(b: T): T = combine(b)
}

sealed trait Monoid[T] extends Semigroup[T]:
  /*
        no need to make extension method,
        should stay on given instance level rather extending object of T
   */
  def Unit: T

object Monoid {
  given additiveIntMonoid: Monoid[Int] with
    def Unit: Int = 0
    extension (a: Int) infix def combine(b: Int): Int = a + b

  given multiplicativeIntMonoid: Monoid[Int] with
    def Unit: Int = 1
    extension (a: Int) infix def combine(b: Int): Int = a * b

  given concatenativeStringMonoid: Monoid[String] with
    def Unit: String = ""
    extension (a: String) infix def combine(b: String): String = a + b

  /*
        creating a "conditional given instance" conditional on another given instance
        the beauty of conditional given instances is that it is recursive
   */
  given listMonoid[T: Monoid]: Monoid[List[T]] with
    def Unit: List[T] = Nil
    extension (l1: List[T])
      infix def combine(l2: List[T]): List[T] = (l1, l2) match
        case (e_l1 :: t_l1, e_l2 :: t_l2) =>
          (e_l1 <+> e_l2) :: (t_l1 combine t_l2)
        case (e_l1 :: t_l1, Nil) =>
          (e_l1 <+> summon[Monoid[T]].Unit) :: (t_l1 combine Nil)
        case (Nil, e_l2 :: t_l2) =>
          (summon[Monoid[T]].Unit <+> e_l2) :: (Nil combine t_l2)
        case (Nil, Nil) => Nil
}

def reduceAList[T](l: List[T])(using monoid: Monoid[T]): T =
  l.fold(monoid.Unit)(_ <+> _)

object TypeClassDemo extends App {
  import Monoid.given

  val data = List(1, 2, 3, -4, -1, 67, 32)

  /*
        as i have two given instances of type Monoid[Int] in scope
        next time shows error if given instance not passed explicitly
   */
  println(reduceAList(data)(using Monoid.additiveIntMonoid))

  val words = List("acha", "to", "ham", "chalte", "hai")

  println(reduceAList(words))
  println(reduceAList(List("aalo")))

  val l1 = List("aloo", "kachalu", "bhaalo")

  val l2 = List("peela", "neela", "kaala", "saved")

  println(l1 combine l2)

  val l3 = List(1, 10, 4, 18, -14, 0)
  val l4 = List(10, 23, 3, 32)

  /*
        I can call given instance like this too
        summon[Monoid[List[Int]]](using listMonoid(using multiplicativeIntMonoid))

        as given of Monoid[List[Int]] can't be created by compiler due to
        ambiguity , having two Monoid[Int] in scope
   */
  given listMulIntMonoid: Monoid[List[Int]] = listMonoid(using
    multiplicativeIntMonoid
  )
  println(l3 <+> l4)
  println(listMulIntMonoid.combine(l3)(l4))
  println(
    summon[Monoid[List[Int]]](using listMonoid(using additiveIntMonoid))
      .combine(l3)(l4)
  )
  println(multiplicativeIntMonoid.combine(10)(10))
}


object TypeClassDemo2 extends App{

    // the initialisation is lazy won't be created unless accessed
    given additiveIntMonoid: Monoid[Int] with {
      println("abc")
      def Unit: Int = 0
      extension (a: Int) infix def combine(b: Int): Int = a + b
    }

    // println(7 <+> 5)
}