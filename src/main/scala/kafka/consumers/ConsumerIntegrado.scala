package eci.edu.co
package kafka.consumers

import kafka.Constants.{TOPIC_LIST,API_URL}
import kafka.consumers.LogicConsumer.{streamTopology,kafkaStreamProps}
import com.typesafe.scalalogging.Logger
import org.apache.kafka.streams.KafkaStreams

object ConsumerIntegrado extends App {

  val topicName = "rpmr-utcd"
  val pathData = TOPIC_LIST.get(topicName)
  val LOGGER = Logger(s"Consumer $topicName, URL: $pathData")
  LOGGER.info(s"Starting Consumer $topicName, URL: $pathData")

  val streams = new KafkaStreams(streamTopology(topicName), kafkaStreamProps)
  Runtime.getRuntime.addShutdownHook(new Thread(() => streams.close()))
  streams.start()

}
