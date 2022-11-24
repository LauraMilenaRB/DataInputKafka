package eci.edu.co
package kafka.consumers

import com.typesafe.scalalogging.Logger
import org.apache.kafka.streams.scala.StreamsBuilder
import kafka.Constants.{BUCKET_NAME_STAGING,SERVER_NAME,IP}
import servicesAWS.{ServiceAwsGlue, ServiceBucketAws}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.serialization.Serdes._
import java.util.Properties

object LogicConsumer {

  val kafkaStreamProps: Properties = {
    val props = new Properties()
    props.put(SERVER_NAME, IP)
    props.put("application.id", "consumer-stream-sample")
    props.put("auto.offset.reset", "latest")
    props
  }

  val LOGGER = Logger("LogicConsumer")
  LOGGER.info("Starting LogicConsumer")

  def streamTopology(topicName : String) = {
    val topic = topicName
    val topic2 = topic.replace("-","")+".py"
    val streamsBuilder = new StreamsBuilder()
    LOGGER.info(s"Init stream : $topic")
    streamsBuilder
      .stream[String, String](topic)
      .foreach((key, value) =>
      {LOGGER.info(s"Read message with topic: $topic, key: $key, value: $value")
        ServiceBucketAws.uploadFileBucket(BUCKET_NAME_STAGING,key,value) match {
          case Right(value) => LOGGER.info(value)
            ServiceAwsGlue.createStartRunJobPython("JOB-"+key,"s3://raw-zone-eci/"+topic,"s3://job-aws-glue/scripts/"+topic2) match {
              case Right(value) => LOGGER.info(value)
              case Left(e) => LOGGER.info(e.toString)
            }
          case Left(e) => LOGGER.info(e.toString)
        }
      }
      )
    streamsBuilder.build()
  }
}
