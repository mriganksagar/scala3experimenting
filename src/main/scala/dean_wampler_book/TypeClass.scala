package dean_wampler_book


trait Semigroup[T]{
    extension(a:T)
        infix def combine(b: T): T
        def <+> (b: T): T = combine(b)
}

trait Monoid[T] extends Semigroup[T]:
    /* 
        no need to make extension method,
        should stay on given instance level rather extending object of T
     */
    def Unit: T 

given additiveIntMonoid: Monoid[Int] with
    def Unit: Int = 0
    extension (a: Int) def combine(b: Int): Int = a + b

given multiplicativeIntMonoid: Monoid[Int] with
    def Unit: Int = 1
    extension (a: Int) def combine(b: Int): Int = a*b

given concatenativeStringMonoid: Monoid[String] with
    def Unit: String = ""
    extension (a: String) def combine(b: String): String = a + b

def reduceAList[T](l : List[T])(using monoid: Monoid[T]):T = l.fold(monoid.Unit)(_<+>_)

object TypeClassDemo extends App {

    val data = List(1,2,3,-4,-1,67,32)

    /* 
        as i have two given instances of type Monoid[Int] in scope
        next time shows error if given instance not passed explicitly
     */
    println(reduceAList(data)(using additiveIntMonoid))
    
    val words = List("acha", "to", "ham", "chalte", "hai")

    println(reduceAList(words))
    println(reduceAList(List("aalo")))
}
