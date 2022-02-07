package org.socialmedia.utils

import com.google.cloud.pubsub.v1.Publisher
import com.google.protobuf.ByteString
import com.google.pubsub.v1.{PubsubMessage, TopicName}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.slf4j.LoggerFactory
import org.socialmedia.models._
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

object DataPublisher {

  val logger = LoggerFactory.getLogger("DataPublisher")
  implicit val formats = DefaultFormats

  @throws[IOException]
  @throws[ExecutionException]
  @throws[InterruptedException]
  def publishToPubSub(topicId: String, message: String, logMsg: String): Unit = {
    val projectId = "projet-esme-plateforme-bi"
    val topicName = TopicName.of(projectId, topicId)
    var publisher: Publisher = null

    try { // Create a publisher instance with default settings bound to the topic
      publisher = Publisher.newBuilder(topicName).build
      val data = ByteString.copyFromUtf8(message)
      val pubsubMessage = PubsubMessage.newBuilder.setData(data).build
      // Once published, returns a server-assigned message id (unique within the topic)
      val messageIdFuture = publisher.publish(pubsubMessage)
      val messageId = messageIdFuture.get
      logger.info(s"message: ${messageId} (${logMsg}) has sent to PubSub")
    } finally if (publisher != null) { // When finished with the publisher, shutdown to free up resources.
      publisher.shutdown()
      publisher.awaitTermination(1, TimeUnit.MINUTES)
    }
  }

  def sendToPubSub[T](message: T): Unit = {
    message match {
      case msg: User =>
        val logMsg = s"User ${msg.userId} has been created"
        publishToPubSub("user", write(msg.asInstanceOf[User]), logMsg)
      case msg: FriendRequest =>
        val logMsg = s"Friend request sent from User ${msg.requesterId} to ${msg.receiverId}"
        publishToPubSub("friend-request", write(msg.asInstanceOf[FriendRequest]), logMsg)
      case msg: FriendRequestAccepted =>
        val logMsg = s"Accepted FriendRequest from User ${msg.requesterId} to ${msg.accepterId}"
        publishToPubSub("friend-request-accepted", write(msg.asInstanceOf[FriendRequestAccepted]), logMsg)
      case msg: PicturePost =>
        val logMsg = s"The Picture ${msg.pictureId} shared by User ${msg.publisherId}"
        publishToPubSub("post-picture", write(msg.asInstanceOf[PicturePost]), logMsg)
      case msg: LikedPicture =>
        val logMsg = s"The Picture ${msg.pictureId} liked by User ${msg.userId}"
        publishToPubSub("like-picture", write(msg.asInstanceOf[LikedPicture]), logMsg)
      case msg: VideoPost =>
        val logMsg = s"The Video ${msg.videoId} shared by User ${msg.publisherId}"
        publishToPubSub("post-video", write(msg.asInstanceOf[VideoPost]), logMsg)
      case msg: LikedVideo =>
        val logMsg = s"The Video ${msg.videoId} liked by User ${msg.userId}"
        publishToPubSub("like-video", write(msg.asInstanceOf[LikedVideo]), logMsg)
    }

  }
}
