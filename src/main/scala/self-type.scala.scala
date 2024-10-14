import javax.security.auth.Subject
import java.util.Observer

object selfTypeDemonstration extends App{

    abstract class SubjectObserver{
        type S <: Subject
        type O <: Observer
    
        trait Subject { self: S =>
            private var observers = List.empty[O]
            def addObserver(observer: O) = observers:+=observer
            def notifyAllObservers() = observers foreach {_.receiveUpdate(this)}
        }
        
        trait Observer {
            def receiveUpdate(subject: S): Unit
        }
    }


    case class Button(label: String) {
        def click():Unit = {println(s"button with $label")}
        def rightClick(): Unit = {println(s"button with $label right clicked")}
    }

    object ButtonSubjectObserver extends SubjectObserver {
        type S = ObservableButton
        type O = ButtonClickObserver

        class ObservableButton(label:String) extends Button(label) with Subject{
            override def click() = {
                super.click()
                notifyAllObservers()
            }   
        }

        class ButtonClickObserver extends Observer{
            val clicks = scala.collection.mutable.HashMap.empty[String, Int]

            def receiveUpdate(button: ObservableButton): Unit = {
                val count = clicks.getOrElse(button.label, 0) +1
                clicks.update(button.label, count)
            }
        }

    }

    val bb = new ButtonSubjectObserver.ObservableButton("blue button")
    val clickObserver = new ButtonSubjectObserver.ButtonClickObserver()

}