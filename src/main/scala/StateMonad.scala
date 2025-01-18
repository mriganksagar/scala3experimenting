// implementation of State from talk SCALAZ STATE MONAD
// https://www.youtube.com/watch?v=Jg3Uv_YWJqI&t=2s
import java.time.LocalDateTime

trait State[S, +A] {
    def run(initial: S): (S, A)

    def map[B](f: A => B): State[S, B] = 
        State { s =>
            val (s1, a) = run(s)
            (s1, f(a))   
        }

    def flatMap[B](f: A => State[S, B]): State[S, B] =
        State { s =>
            val (s1, a) = run(s)
            f(a).run(s1)
        }
}

object State {
    def apply[S, A](f: S => (S, A)): State[S, A] = 
        new State[S, A] {
            def run(initial: S): (S, A) = f(initial)
        }
}


// lets refactor code
// GOAL: use State here too
case class FollowerStats(followers: Int, following: Int)

case class Timestamped(followerstats: FollowerStats, timestamp: LocalDateTime)

class Cache(cache: Map[String, FollowerStats], hit: Int, miss: Int) {
    def get(username:String) = cache.get(username)
}

case class Counter(i:Int){
    def increment = Counter(i+1)
    def decrement = Counter(i-1)
}


def myeffect(s: String) = {
    println(s"effecting $s")
}
def foos2(u: String) = {
    val x = myeffect("foos2")

    State[Counter,Int]{(c)=> (c.increment, 3) }
}


def foos1(u:String) = State[Counter,Int] {(c) =>
    (c.increment , 4)
}


def combinedfoos = {
    for{
        a <- foos1("alok")
        b <- foos2("aditya")
    } yield a 
}


@main
def main() = {
    val x = combinedfoos
    x.run(Counter(0))
}
// // case class Cache(followers: Map[String, FollowerStats], following: Map[String, FollowerStats])
// def followerStats(username: String, cache: Cache): (Cache, FollowerStats) = {
//     val (newCache, ofs) = checkCache(username)(cache)
//     ofs match{
//         case Some(fs) => (newCache, fs) 
//         case None => retrieve(username)(newCache)
//     }
// }


// def followerStats2(username: String)(cache: Cache) = 
//     State(checkCache(username)).flatMap{
//         case Some(ofs) => State {s => (s, ofs)} 
//         case None => State{retrieve(username)}
//     }.run(cache)


// def checkCache(username: String)(cache: Cache ): (Cache, Option[FollowerStats]) = 
// def retrieve(username: String)(cache: Cache ): (Cache, FollowerStats) = ???