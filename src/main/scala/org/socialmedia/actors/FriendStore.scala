package org.socialmedia.actors

import akka.actor.{Actor, ActorRef, Timers}
import akka.pattern.ask
import akka.util.Timeout
import org.socialmedia.utils.DataPublisher.sendToPubSub
import org.socialmedia.actors.FriendStore.{AcceptFriendRequest, SendFriendRequest}
import org.socialmedia.actors.UserStore.GetNumUsers
import org.socialmedia.configuration.AppConfiguration.timersConf
import org.socialmedia.generators.DateGenerator.generateRandomNumber
import org.socialmedia.generators.{FriendRequestAcceptedGenerator, FriendRequestGenerator}
import org.socialmedia.utils.RandomData.{generatePauseContentCreation, getRandomUserId}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt


object FriendStore {
  case class SendFriendRequest(userId: Int, requestSentToIds: List[Int], userStoreRef: ActorRef)
  case class AcceptFriendRequest(requestedId: Int, accepterId: Int)
}

class FriendStore extends Actor with Timers {

  implicit val timeout = Timeout(10 seconds)

  def willRequestBeAccepted(): Boolean = generateRandomNumber(1, 2) match {
    case 1 => true
    case 2 => false
  }

  override def receive: Receive = {
    case SendFriendRequest(userId, requestSentToIds, userStoreRef) =>
      /* Make a list of all ids of the users who are not yet friends with the requester  */
      val potentialFriendsFuture = userStoreRef ? GetNumUsers
      val maxNumUsers = Await.result(potentialFriendsFuture, timeout.duration).asInstanceOf[Int]
      val newPotentialFriends = (1 to maxNumUsers).toList
        .filter(user => !requestSentToIds.contains(user))

      val newFriendId = getRandomUserId(newPotentialFriends)

      if(newFriendId != 0) {
        val friendRequest = FriendRequestGenerator(userId, newFriendId)
        sendToPubSub(friendRequest)
        sender() ! newFriendId
      }
      sender() ! 0

      if(willRequestBeAccepted) {
        val pauseDuration = generatePauseContentCreation(timersConf.friendRequestAccepted.minPause,
          timersConf.friendRequestAccepted.maxPause)
        timers.startSingleTimer("friend_request_accepted_timer",
          AcceptFriendRequest(userId, newFriendId), pauseDuration seconds)
      }

    case AcceptFriendRequest(requestedId, accepterId) =>
      val acceptedFriendRequest = FriendRequestAcceptedGenerator(requestedId, accepterId)
      sendToPubSub(acceptedFriendRequest)
  }
}
