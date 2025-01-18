object TypeClassesDemonstration {
    trait Semigroup[T]{
        extension(t:T){
            def combine(other: T): T
            def <+>(other: T): T = t.combine(other)
        }
    }

    trait Monoid[T](val unit: T) extends Semigroup[T]

    given StringMonoid: Monoid[String]("") with {
        extension(s: String){
            def combine(other: String): String = s + other 
        }
    }

    given IntegerMonoid: Monoid[Int](0) with {
        extension(s: Int){
            def combine(other: Int):Int = s + other
        }
    }
    
}
