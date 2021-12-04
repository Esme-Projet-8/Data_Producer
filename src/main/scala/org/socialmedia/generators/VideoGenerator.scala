package org.socialmedia.generators

import org.socialmedia.models.VideoPost
import org.socialmedia.generators.DateGenerator.generateRandomNumber

object VideoGenerator {

  def makeVideoDuration: Int = generateRandomNumber(10, 300)

  def apply(userId: Int, videoId: Int): VideoPost = {
    VideoPost(
      videoId = videoId,
      publisherId = userId,
      timestamp = System.currentTimeMillis / 1000,
      videoDuration = makeVideoDuration
    )
  }
}
