package scala3features



/* 
    Scala 3 provides an Opaque type

    https://docs.scala-lang.org/scala3/book/types-opaque-types.html

    opaque types are a feature introduced in Scala 3 that allows you to define a type alias with strong encapsulation.
    They let you create a new type that is distinct from its underlying implementation type,
    even though the compiler treats them similarly within their defining scope (object or package).

 */


/* 
    Example: Create an Opaque type

    UserId is same as String but only inside MyDomain,
    any method from string will not be exposed by UserId type outside MyDomain

    an Object is used with apply method
    and extension methods to give functionality 
*/
object MyDomain {
    opaque type UserId = String
    
    object UserId{
        def apply(value: String): UserId = value 
    }
    
    extension (v: UserId) def validate: Boolean = v.startsWith("uu")
} 
    
    
object OpaqueTypeDemo extends App{

    import scala3features.MyDomain.UserId

    val x: UserId = UserId("aaloo")
    println(x.validate)
}

/* 
    A real example for opaque type is IArray (from scala 3) which is an immutable implementation for Array
    to minimise runtime overhead we use opaque type instead of a wrapper
 */