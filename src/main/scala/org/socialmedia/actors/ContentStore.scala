package org.socialmedia.actors

import akka.actor.Actor
import org.socialmedia.utils.DataPublisher.{sendToPubSub}
import org.socialmedia.actors.ContentStore.{AddPicture, AddVideo, LikePicture, LikeVideo}
import org.socialmedia.generators.DateGenerator.generateRandomNumber
import org.socialmedia.generators.{LikedPictureGenerator, LikedVideoGenerator, PictureGenerator, VideoGenerator}
import org.socialmedia.models.{User}


object ContentStore {
  case class AddPicture(user: User)
  case class AddVideo(user: User)
  case class LikePicture(user: User)
  case class LikeVideo(user: User)
}

class ContentStore extends Actor {
  var latestPictureIndex = 0
  var latestVideoIndex = 0

  override def receive: Receive = {
    case AddPicture(user) =>
      latestPictureIndex = latestPictureIndex + 1
      val picture = PictureGenerator(user.userId, latestPictureIndex)
      sendToPubSub(picture)

    case AddVideo(user) =>
      latestVideoIndex = latestVideoIndex + 1
      val video = VideoGenerator(user.userId, latestVideoIndex)
      sendToPubSub(video)

    case LikePicture(user) =>
      if (latestVideoIndex > 0) {
        //println(s"Pick a picture ID between 1 and $latestPictureIndex")
        val randomPicIndex = generateRandomNumber(1, latestPictureIndex)
        val likedPicture = LikedPictureGenerator(user.userId, randomPicIndex)
        sendToPubSub(likedPicture)
      }

    case LikeVideo(user) =>
      if (latestVideoIndex > 0) {
        //println(s"Pick a video ID between 1 and $latestVideoIndex")
        val randomVideoIndex = generateRandomNumber(1, latestVideoIndex)
        val likedVideo = LikedVideoGenerator(user.userId, randomVideoIndex)
        sendToPubSub(likedVideo)
      }
  }
}
