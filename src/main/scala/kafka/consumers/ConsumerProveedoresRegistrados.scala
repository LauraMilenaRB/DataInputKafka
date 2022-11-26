package eci.edu.co
package kafka.consumers

import kafka.Constants.TOPIC_LIST
import kafka.consumers.LogicConsumer.{streamTopology}
import kafka.Constants.{SERVER_NAME,IP}
import com.typesafe.scalalogging.Logger
import org.apache.kafka.streams.KafkaStreams

import java.util.Properties

object ConsumerProveedoresRegistrados extends App {

  val topicName = "qmzu-gj57"
  val pathData = TOPIC_LIST.get(topicName)
  val LOGGER = Logger(s"Consumer $topicName, URL: $pathData")
  LOGGER.info(s"Starting Consumer $topicName, URL: $pathData")

  val kafkaStreamProps: Properties = {
    val props = new Properties()
    props.put(SERVER_NAME, IP)
    props.put("application.id", "consumer-stream-sample")
    props.put("auto.offset.reset", "latest")
    props
  }
  val streams = new KafkaStreams(streamTopology(topicName), kafkaStreamProps)
  Runtime.getRuntime.addShutdownHook(new Thread(() => streams.close()))
  streams.start()
}
