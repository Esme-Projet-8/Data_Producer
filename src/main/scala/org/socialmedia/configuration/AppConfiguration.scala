package org.socialmedia.configuration

import com.typesafe.config.ConfigFactory

object AppConfiguration {

  case class Timer(minPause: Int, maxPause: Int)
  case class Timers(content: Timer, friendRequest: Timer)
  case class DataGenConf(maxUsers: Int, maxHoursOffsetSignUp: Double)

  private val configFile = ConfigFactory.load()

  val dataGenConf = DataGenConf(
    maxUsers = configFile.getInt("dataGenerator.maxUsers"),
    maxHoursOffsetSignUp = configFile.getDouble("dataGenerator.maxHoursOffsetSignUp")
  )

  val timersConf = Timers(
    content = Timer(
       configFile.getInt("dataGenerator.timers.content.minPause"),
      configFile.getInt("dataGenerator.timers.content.maxPause")
    ),
    friendRequest = Timer(
      configFile.getInt("dataGenerator.timers.friendRequest.minPause"),
      configFile.getInt("dataGenerator.timers.friendRequest.maxPause")
    )
  )
}
