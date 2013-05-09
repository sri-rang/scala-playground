package net.srirangan.scalaplayground.collections

object CollectionsApp extends App {

  (1 to 100).filter(n => {
    n match {
      case 2 => true
      case 5 => true
      case _ => false
    }
  })

}
