package org.socialmedia.utils

import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.socialmedia.models._
import com.google.cloud.pubsub.v1.Publisher
import com.google.protobuf.ByteString
import com.google.pubsub.v1.PubsubMessage
import com.google.pubsub.v1.TopicName
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit


object DataProducer {

  implicit val formats = DefaultFormats

  @throws[IOException]
  @throws[ExecutionException]
  @throws[InterruptedException]
  def publisherExample(topicId: String, message: String): Unit = {
    val projectId = "social-network-bi"
    val topicName = TopicName.of(projectId, topicId)
    var publisher: Publisher = null

    try { // Create a publisher instance with default settings bound to the topic
      publisher = Publisher.newBuilder(topicName).build
      val data = ByteString.copyFromUtf8(message)
      val pubsubMessage = PubsubMessage.newBuilder.setData(data).build
      // Once published, returns a server-assigned message id (unique within the topic)
      val messageIdFuture = publisher.publish(pubsubMessage)
      val messageId = messageIdFuture.get
      System.out.println("Published message ID: " + messageId)
    } finally if (publisher != null) { // When finished with the publisher, shutdown to free up resources.
      publisher.shutdown()
      publisher.awaitTermination(1, TimeUnit.MINUTES)
    }
  }

  def publishToConsole(message: String): Unit = {
    println(message+"\n")
  }

  def sendToPubSub[T](message: T): Unit = {
    message match {
      case msg: User =>
        println(s"User ${msg.userId} has been created")
        publisherExample("users", write(msg.asInstanceOf[User]))
      case msg: FriendRequest =>
        println(s"Friend request sent of ${msg.requesterId} to ${msg.receiverId}")
        publishToConsole(write(msg.asInstanceOf[FriendRequest]))
      case msg: FriendRequestAccepted =>
        println(s"The FriendRequest of ${msg.requesterId} to ${msg.accepterId} has been accepted")
        publishToConsole(write(msg.asInstanceOf[FriendRequestAccepted]))
      case msg: PicturePost =>
        println(s"The Picture ${msg.pictureId} has been shared")
        publishToConsole(write(msg.asInstanceOf[PicturePost]))
      case msg: LikedPicture =>
        println(s"The Picture ${msg.pictureId} has been liked")
        publishToConsole(write(msg.asInstanceOf[LikedPicture]))
      case msg: VideoPost =>
        println(s"The Video ${msg.videoId} has been shared")
        publishToConsole(write(msg.asInstanceOf[VideoPost]))
      case msg: LikedVideo =>
        println(s"The Video ${msg.videoId} has been liked")
        publishToConsole(write(msg.asInstanceOf[LikedVideo]))
    }

  }
}
