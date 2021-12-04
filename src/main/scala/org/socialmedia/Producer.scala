package org.socialmedia

import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.scalalogging.Logger
import org.socialmedia.actors.{ContentStore, FriendStore, UserManager, UserStore}
import org.socialmedia.actors.UserManager.CreateUser
import org.socialmedia.generators.DateGenerator.generateTimeBeforeCreate
import org.socialmedia.configuration.AppConfiguration.dataGenConf

import scala.concurrent.duration._

object Producer {

  case class UserActorConfig(ref: ActorRef, name: String, timeBeforeCreate: Int)

  val system = ActorSystem("socialmedia_data_generator")
  import system.dispatcher

  /* Create a UserManager actor ref for each users*/
  def makeUserActorConfig(totalUsers: Int): List[UserActorConfig] = {
    (1 to totalUsers).toList.map { userId =>
      val userActorName = s"user_$userId"
      val userActor = system.actorOf(Props[UserManager], userActorName)
      UserActorConfig(userActor, userActorName, generateTimeBeforeCreate(dataGenConf.maxHoursOffsetSignUp))
    }
  }

  def main(args: Array[String]): Unit = {
   val usersConfig = makeUserActorConfig(dataGenConf.maxUsers)

    val userStoreRef = system.actorOf(Props[UserStore], "user_store")
    val contentStoreRef = system.actorOf(Props[ContentStore], "content_store")
    val friendStoreRef = system.actorOf(Props[FriendStore], "friend_store")

    usersConfig.foreach { user =>
      system.scheduler.scheduleOnce(user.timeBeforeCreate seconds,
        user.ref, CreateUser(userStoreRef, contentStoreRef, friendStoreRef))
    }
  }
}
