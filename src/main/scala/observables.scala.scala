trait Observer[State]{
    def receiveUpdate(state: State): Unit
}

trait Subject[State]{
    private var observers: Vector[Observer[State]] = Vector.empty[Observer[State]]
    def addObserver(observer: Observer[State]) = observers.synchronized{
        observers :+= observer
    }
    def notifyObservers(state: State) = 
        observers.foreach( _.receiveUpdate(state))

    // garbage code
    def click() = println("shitty sa print")
}

trait Clickable{
    def click() = println("we are logging that something is clicked")
}

class Button{
    def click(): Unit = updateUI()
    protected def updateUI(): Unit  = println("Ui update ho rha hai ")
}

class ObservableButton extends Button with Subject[Button]:
    override def click() = 
        super.click()
        notifyObservers(this)

object Main extends App{
    val mybutton = new ObservableButton
    mybutton.click()
}