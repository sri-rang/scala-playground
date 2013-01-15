// Scala 2.10.0 introduces `futures`

package net.srirangan.scalaplayground.futures

// import future, maps, for, ExecutionContent

import scala.concurrent._

// futures will spawn threads in the global context thread pool

import ExecutionContext.Implicits.global

import scala.util.Random

object Main extends App {

  // `future` blocks asynchronously execute in a different thread and return a `val`
  // asynchronous results can be trapped via `onSuccess`, `onFailure` and `onComplete` callbacks

  val sayHello = future {
    Thread.sleep(1000)
    "hello"
  }

  sayHello onSuccess {
    case message => Console.println(s"He said '$message'")
  }

  Console.println("Waiting..")

  Thread.sleep(2000)

  val tryDivideByZero = future {
    Thread.sleep(1000)
    1 / 0
  }

  tryDivideByZero onFailure {
    case e: ArithmeticException => Console.println(s"Don't be silly!")
  }

  Console.println("Try dividing by zero..")

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

  // Sometimes we need recover from an exception with a value
  // lets `recover` from an exception with a fallback val 'Infinity'

  val tryDivideByZeroAgain = future {
    Thread.sleep(1000)
    1 / 0
  } recover {
    case e: ArithmeticException => "Infinity"
  }

  tryDivideByZeroAgain onSuccess {
    case e => Console.println(e)
  }

  tryDivideByZeroAgain onFailure {
    case e => Console.println(e)
  }

  Console.println("Try dividing by zero, recover from exception..")

  Thread.sleep(2000)

  // Or maybe future f1 must fallback to future f2?

  val f1 = future {
    Thread.sleep(500)
    1 / 0
  }

  val f2 = future {
    Thread.sleep(500)
    "Infinity"
  }

  f1 fallbackTo f2 onSuccess {
    case v => Console.println(v)
  }

  Console.println("Try dividing by zero, fallback to another future..")

  Thread.sleep(2000)

  // the for {} construct lets us execute multiple futures in parallel
  // to serially execute futures in specific orders, we use `andThen`
  // andThen ensures execution orders in what would otherwise be random

  val whamBamThankYouMaam = future {
    Thread.sleep(500)
    Console.println("Wham!")
  } andThen {
    case _ => {
      Thread.sleep(500)
      Console.println("Bam!")
    }
  } andThen {
    case _ => {
      Thread.sleep(500)
      Console.println("Thank you ma'am!")
    }
  }

  Console.println("Will you score?")

  Thread.sleep(2000)

  // `promises` can be used to compose type safe futures

  val willYouMarryMe = promise[Boolean]

  willYouMarryMe.future onSuccess {
    case yes => Console.println("Yes! :D")
  }

  willYouMarryMe.future onFailure {
    case no => Console.println("No :(")
  }

  future {
    Thread.sleep(1000)
    if (new Random().nextBoolean())
      willYouMarryMe success true // type safe, try passing non boolean value here and compile
    else
      willYouMarryMe failure new Exception
  }

  Console.println("Will you marry me?")

  Thread.sleep(2000)

  // Two asynchronous blocks `tryComplete` a promise

  val whoWonTheRace = promise[String]

  whoWonTheRace.future onSuccess {
    case name => Console.println(name + " wins")
  }

  future {
    Thread.sleep(new Random().nextInt(500))
    whoWonTheRace trySuccess "x"
  }

  future {
    Thread.sleep(new Random().nextInt(500))
    whoWonTheRace trySuccess "y"
  }

  Console.println("Who won the race?")

  Thread.sleep(1000)

}
