package org.socialmedia.generators

import org.scalatest.flatspec.AnyFlatSpec
import org.socialmedia.generators.DateGenerator.generateRandomNumber

class DateGeneratorTest extends AnyFlatSpec {
  "The random number generator" should "return 100 random values between in the min and the max bound" in {
    val minBound = 1
    val maxBound = 5
    val valuesOutOfRange = List.range(1, 100)
      .map(_ => generateRandomNumber(minBound, maxBound))
      .filter(number => number < minBound || number > maxBound)
    assert(valuesOutOfRange.length == 0)
  }
}
