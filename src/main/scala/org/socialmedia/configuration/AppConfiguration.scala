package org.socialmedia.configuration

import com.typesafe.config.ConfigFactory

object AppConfiguration {

  case class KafkaConf(kafkaBrokerUrl: String, schemaRegistryUrl: String)

  case class Timer(minPause: Int, maxPause: Int)
  case class Timers(actions: Timer, friendRequestAccepted: Timer)
  case class DataGenConf(maxUsers: Int, maxHoursOffsetSignUp: Double)

  private val configFile = ConfigFactory.load()

  val kafkaConf = KafkaConf(
    kafkaBrokerUrl = configFile.getString("kafka.kafkaBrokerUrl"),
    schemaRegistryUrl = configFile.getString("kafka.schemaRegistryUrl")
  )

  val dataGenConf = DataGenConf(
    maxUsers = configFile.getInt("dataGenerator.maxUsers"),
    maxHoursOffsetSignUp = configFile.getDouble("dataGenerator.maxHoursOffsetSignUp")
  )

  val timersConf = Timers(
    actions = Timer(
       configFile.getInt("dataGenerator.timers.actions.minPause"),
      configFile.getInt("dataGenerator.timers.actions.maxPause")
    ),
    friendRequestAccepted = Timer(
      configFile.getInt("dataGenerator.timers.friendRequestAccepted.minPause"),
      configFile.getInt("dataGenerator.timers.friendRequestAccepted.maxPause")
    )
  )
}
