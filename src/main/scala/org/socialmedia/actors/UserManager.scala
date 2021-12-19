package org.socialmedia.actors

import akka.actor.{Actor, ActorRef, Timers}
import akka.pattern.ask
import akka.util.Timeout
import org.socialmedia.actors.ContentStore.{AddPicture, AddVideo, LikePicture, LikeVideo}
import org.socialmedia.actors.FriendStore.SendFriendRequest
import org.socialmedia.actors.UserManager.{ContentGeneration, CreateUser, FriendRequest}
import org.socialmedia.actors.UserStore.AddUser
import org.socialmedia.configuration.AppConfiguration.timersConf
import org.socialmedia.generators.DateGenerator.generateRandomNumber
import org.socialmedia.models.User
import org.socialmedia.utils.RandomData.generatePauseContentCreation
import scala.concurrent.duration._
import scala.concurrent.Await
import scala.collection.mutable.ListBuffer


object UserManager {
  case class CreateUser(userStoreRef: ActorRef, contentStoreRef: ActorRef, friendStoreRef: ActorRef)
  case object ContentGeneration
  case object FriendRequest
}

class UserManager extends Actor with Timers {

  implicit val timeout = Timeout(10 seconds)

  val PostPictureAction = 1
  val PostVideoAction = 2
  val LikePictureAction = 3
  val LikeVideoAction = 4
  val SendFriendRequestAction = 5

  var requestSentToIds = ListBuffer[Int]()

  override def receive: Receive = {
    case CreateUser(userStoreRef, contentStoreRef, friendStoreRef) =>
      val userFuture = userStoreRef ? AddUser
      val user = Await.result(userFuture, timeout.duration).asInstanceOf[User]
      context.become(createContent(user, userStoreRef, contentStoreRef, friendStoreRef), true)
      self ! ContentGeneration
      self ! FriendRequest
  }

  def getContentAction(): Int = generateRandomNumber(1,4)

  def createContent(user: User, userStoreRef: ActorRef, contentStoreRef: ActorRef,
                    friendStoreRef: ActorRef): Receive = {
    case ContentGeneration =>
      getContentAction match {
        case PostPictureAction => contentStoreRef ! AddPicture(user)
        case PostVideoAction => contentStoreRef ! AddVideo(user)
        case LikePictureAction => contentStoreRef ! LikePicture(user)
        case LikeVideoAction => contentStoreRef ! LikeVideo(user)
      }
      val contentPauseDuration = generatePauseContentCreation(timersConf.content.minPause,
        timersConf.content.maxPause)
      timers.startSingleTimer("content_creation_timer", ContentGeneration, contentPauseDuration seconds)

    case FriendRequest =>
      val receiverIdFuture = friendStoreRef ? SendFriendRequest(user.userId, requestSentToIds.toList, userStoreRef)
      val receiverId = Await.result(receiverIdFuture, timeout.duration).asInstanceOf[Int]
      if (receiverId != 0) requestSentToIds += receiverId

      val friendRequestPauseDuration = generatePauseContentCreation(timersConf.friendRequest.minPause,
        timersConf.friendRequest.maxPause)
      timers.startSingleTimer("content_creation_timer", FriendRequest, friendRequestPauseDuration seconds
    )

  }
}
