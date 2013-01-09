// Scala 2.10.0 introduces `futures`

package net.srirangan.scalaplayground.futures

// Import future, maps, for, ExecutionContent
import scala.concurrent._

// futures will spawn threads in the global context's thread pool
import ExecutionContext.Implicits.global

import scala.util.Random

object Main extends App {

  // `future` blocks asynchronously execute in a different thread and return a `val`
  // asynchronous results can be trapped via `onSuccess`, `onFailure` and `onComplete` callbacks

  Console.println("Waiting..")

  val sayHello = future {
    Thread.sleep(1000)
    "hello"
  }

  sayHello onSuccess {
    case message => Console.println(s"He said '$message'")
  }

  Thread.sleep(2000)

  Console.println("Try dividing by zero")

  val tryDivideByZero = future {
    Thread.sleep(1000)
    1 / 0
  }

  tryDivideByZero onFailure {
    case e: ArithmeticException => Console.println(s"Don't be silly!")
  }

  Thread.sleep(2000)

  // if one future depends on the result of another
  // you could nest callbacks
  // or use `map` to "chain" them
  // `map` returns a future which can be trapped via callbacks

  val firstLove = future {
    Thread.sleep(500)
    "i love you"
  }

  val thenBetray = firstLove map {
    case loveLetter => {
      Console.println(loveLetter)
      Thread.sleep(500)
      "not really"
    }
  }

  thenBetray onSuccess {
    case partingWords => Console.println(partingWords)
  }

  Thread.sleep(2000)

  // Multiple inter-dependent futures can create nested callback hell
  // This is avoided with the `for` construct
  // `for` can "chain" multiple futures, and "apply" another `future` on their results

  val calculateMyScore = future {
    Thread.sleep(500)
    new Random().nextInt(10)
  }

  val calculateYourScore = future {
    Thread.sleep(500)
    new Random().nextInt(10)
  }

  val doIWin = for {
    myScore <- calculateMyScore
    yourScore <- calculateYourScore
  } yield myScore > yourScore


  doIWin onSuccess {
    case b: Boolean => Console.println(if (b) "yes" else "no")
  }

  Console.println("Do I win?")

  Thread.sleep(2000)

}
