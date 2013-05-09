package net.srirangan.scalaplayground.levels

import util.Random

object A1 extends App {

  Console.println("hello a1")

  Console.println(14 + 7)

  def print_me(content: String) = Console.println(content)

  print_me("meeeeeeeeeeeee!")

  if (Random.nextBoolean) print_me("true") else print_me("false")

  for (i <- 5 until 10) print_me(i.toString)

  for (i <- 5 to 10) print_me(i.toString)

  (5 to 10).foreach(n => print_me(n.toString))

  class Person(val name: String, val age: Int) {
    override def toString = name + "," + age
  }

  val people = List(
    new Person("a", 10),
    new Person("b", 11),
    new Person("c", 12),
    new Person("d", 13),
    new Person("e", 14)
  )

  for (person <- people) print_me(person.toString)

}
