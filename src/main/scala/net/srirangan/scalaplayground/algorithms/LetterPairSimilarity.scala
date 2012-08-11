package net.srirangan.scalaplayground.algorithms

/**
 * An implementation of a string similarity algorithm using the LetterPairSimilarity method.
 * Based on Simon White's work at http://www.catalysoft.com/articles/StrikeAMatch.html
 */

object LetterPairSimilarity {

  def compareStrings(string1: String, string2: String): Double = {
    val pairs1: List[String] = wordLetterPairs(string1)
    val pairs2: List[String] = wordLetterPairs(string2)
    var intersection = 0
    val union = pairs1.length + pairs2.length
    pairs1.foreach((pair1: String) => {
      pairs2.foreach((pair2: String) => {
        if (pair1 == pair2) {
          intersection += 1
          pairs2 diff pair2
        }
      })
    })
    (2.0 * intersection) / union
  }

  private def wordLetterPairs(string: String): List[String] = {
    var allPairs: List[String] = List()
    val words: Array[String] = string.split("\\s")
    words.foreach((word: String) => {
      val pairs: List[String] = letterPairs(word)
      pairs.foreach((pair: String) => {
        allPairs ::= pair
      })
    })
    allPairs
  }

  private def letterPairs(string: String): List[String] = {
    val numberOfPairs: Int = string.length - 1
    var pairs: List[String] = List()
    for (i <- 0 to numberOfPairs - 1) {
      pairs ::= string.substring(i, i + 2)
    }
    pairs
  }

}
