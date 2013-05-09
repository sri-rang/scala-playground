(1 to 10)

// Multiply each by 3
(1 to 10).map(_ * 3)

// flatMap flattens the result
(1 to 10).map(n => List(n, n * 2))


(1 to 10).flatMap(n => List(n, n * 2))

// Filter out odds
(1 to 10).filter(_ % 2 == 0)
// Collect multiples of 3 and 4
(1 to 10).collect({
  case n if n % 3 == 0 => n
  case n if n % 5 == 0 => n
})

// Add two collections
(1 to 5) ++ (6 to 10)


// Size
List().isEmpty
(1 to 3).nonEmpty
List(1, 2, 3, 4, 5).size
List().hasDefiniteSize
// Element retrieval
(1 to 10).head
(1 to 10).tail
(1 to 10).headOption
(1 to 10).lastOption
(1 to 25).find(_ == 12)
//Sub-collection retrieval
(1 to 10).tail
(1 to 10).init
(1 to 10).slice(3, 7)
(1 to 10).take(5)
(1 to 10).drop(5)
(1 to 10).filter(_ % 2 == 0)
(1 to 10).filterNot(_ % 2 == 0)
(1 to 10).withFilter(_ % 2 == 0)


// Sub-division
(1 to 10).splitAt(7)

(1 to 10).span(_ % 2 == 0)

(1 to 10).partition(_ > 7)



(1 to 10).groupBy[String](n => n match {
  case n if n % 2 == 0 => "even"
  case _ => "odd"
})


// Element tests
(1 to 25).exists(_ == 24)
(1 to 25).forall(_ > -1)
(1 to 25).count(_ > 20)
// Simple reductions
(1 to 10).sum
(1 to 5).product
List(5, 3, 1, 7).max
List(5, 3, 1, 7).min
