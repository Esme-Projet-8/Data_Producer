package org.socialmedia.utils

import org.socialmedia.generators.DateGenerator.generateRandomNumber

import scala.util.Random

object RandomData {

  def generatePauseContentCreation(minPause: Int, maxPause: Int): Int = generateRandomNumber(minPause, maxPause)

  def getRandomUserId(potentialIds: List[Int]): Int = {
    if(potentialIds.isEmpty) 0
    else new Random().shuffle(potentialIds).head
  }

}
