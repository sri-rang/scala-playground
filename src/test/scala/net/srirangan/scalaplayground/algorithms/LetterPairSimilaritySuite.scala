package net.srirangan.scalaplayground.algorithms

import org.scalatest.{FunSuite, BeforeAndAfter}

class LetterPairSimilaritySuite extends FunSuite with BeforeAndAfter {

  test("'France' and 'Republic of France'") {
    val score: Double = LetterPairSimilarity.compareStrings("France", "Republic of France")
    assert(score === 0.5555555555555556)
  }

  test("'French Republic' and 'Republic of France'") {
    val score: Double = LetterPairSimilarity.compareStrings("French Republic", "Republic of France")
    assert(score === 0.8)
  }

  test("'French Republic' and 'Republic of Cuba'") {
    val score: Double = LetterPairSimilarity.compareStrings("French Republic", "Republic of Cuba")
    assert(score === 0.782608695652174)
  }

}
