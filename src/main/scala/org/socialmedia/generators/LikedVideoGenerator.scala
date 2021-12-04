package org.socialmedia.generators

import org.socialmedia.models.LikedVideo
import org.socialmedia.generators.DateGenerator.generateRandomNumber

object LikedVideoGenerator {

  def makeVideoViewingDuration: Int = generateRandomNumber(10, 300)

  def apply(userId: Int, videoId: Int): LikedVideo = {
    LikedVideo(
      videoId = videoId,
      userId = userId,
      userTimeSpent = makeVideoViewingDuration,
      timestamp = System.currentTimeMillis / 1000
    )
  }
}
