// class MyLazyList[T](val head: T, t: MyLazyList[T]) {
//     lazy val tail = t
// }

trait HasNext[T]:
    extension (a: T) def next: T

object HasNext {
    given HasNext[Int] with {
        extension (i: Int) def next: Int = i + 1
    }
}

/* 
    https://docs.scala-lang.org/scala3/reference/other-new-features/creator-applications.html
    Due to Universal apply methods,
    we can initialise classes without new keyword
    (including classes coming from java and scala 2 as well )
 */

class MyLazyList[T](val head: T, t: => MyLazyList[T]) {
    lazy val tail = t
}
object MyLazyList {
//     override def apply[T](h: T, t: => MyLazyList[T]) = new MyLazyList()
    def from[T: HasNext](a: T): MyLazyList[T] = MyLazyList(a, from(a.next))
}

object DemoMyLazyList extends App {

    val mlzl = MyLazyList.from(4)
    println(mlzl.tail.tail.tail.tail.head)
}

