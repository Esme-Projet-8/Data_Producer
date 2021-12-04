package org.socialmedia.actors

import akka.actor.Actor
import com.typesafe.scalalogging.Logger
import org.socialmedia.utils.DataPublisher.sendToPubSub
import org.socialmedia.generators.UserGenerator
import org.socialmedia.actors.UserStore.{AddUser, GetNumUsers}


object UserStore {
  case object AddUser
  case object GetNumUsers
}

class UserStore extends Actor {
  var maxUserId = 0

  override def receive: Receive = {
    case AddUser =>
      maxUserId = maxUserId + 1
      val user = UserGenerator(maxUserId)
      sendToPubSub(user)
      sender() ! user

    case GetNumUsers => sender() ! maxUserId
  }
}
