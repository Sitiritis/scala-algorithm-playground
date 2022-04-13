package io.sitiritis.amazon

package object mergepackages {

//  val tests = List(
//    Vector(20, 13, 8, 9) -> 50,
//    Vector(2, 9, 10, 3, 7) -> 21,
//    Vector(3, 2, 9, 10, 9, 8, 8, 8, 8, 7, 6, 6, 6, 6, 6, 6) -> 24,
//    Vector(24, 9, 8, 8, 8, 8, 7, 6, 6, 6, 6, 6, 6) -> 24,
//  )

  def findGreatestMerge(weights: Vector[Int]): Int = {
    var totalSum = 0
    var currentSubseqSum = 0
    var prevSubseqSum = 0
    var max = 0
    var i = 0

    while (i < weights.length) {
      if (i > 0) {
        if (weights(i - 1) >= weights(i)) {
          if (currentSubseqSum > prevSubseqSum) {
            max = totalSum
          }
          prevSubseqSum = currentSubseqSum
          currentSubseqSum = weights(i)
        } else {
          currentSubseqSum += weights(i)
        }
      } else {
        currentSubseqSum += weights(i)
      }

      totalSum += weights(i)
      i += 1
    }

    if (currentSubseqSum > prevSubseqSum) {
      max = totalSum
    }

    max
  }

}
