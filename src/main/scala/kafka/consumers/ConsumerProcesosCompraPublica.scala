package eci.edu.co
package kafka.consumers

import kafka.Constants.{API_URL, TOPIC_LIST}
import kafka.consumers.LogicConsumer.{kafkaStreamProps, streamTopology}
import com.typesafe.scalalogging.Logger
import org.apache.kafka.streams.KafkaStreams

object ConsumerProcesosCompraPublica extends App {

  val topicName = "f789-7hwg"
  val pathData = TOPIC_LIST.get(topicName)
  val LOGGER = Logger(s"Consumer $topicName, URL: $pathData")
  LOGGER.info(s"Starting Consumer $topicName, URL: $pathData")

  val streams = new KafkaStreams(streamTopology(topicName), kafkaStreamProps)
  Runtime.getRuntime.addShutdownHook(new Thread(() => streams.close()))
  streams.start()
}