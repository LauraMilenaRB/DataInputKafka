package eci.edu.co
package kafka.producers

import com.typesafe.scalalogging.Logger
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import servicesAWS.ServiceBucketAws
import kafka.Constants.{API_URL, BUCKET_NAME_RAW, BUCKET_NAME_STAGING, IP, SERVER_NAME}

import java.sql.Timestamp
import scala.io.Source
import java.util.Properties

object DataProducer extends App {


  val LOGGER = Logger("Producer")
  LOGGER.info("Starting Producer")

  val pattern = "(updatedAt\":\"[0-9-A-Z:.]{24}|dateModified\":\"[0-9-A-Z:.+]{25})".r

  val kafkaProducerProps: Properties = {
    val props = new Properties()
    props.put(SERVER_NAME, IP)
    props.put("key.serializer", classOf[StringSerializer].getName)
    props.put("value.serializer", classOf[StringSerializer].getName)
    props.put("linger.ms",10)
    props.put("retries",3)
    props.put("enable.idempotence",true)
    props
  }


  val producer = new KafkaProducer[String, String](kafkaProducerProps)
  for(i <- 0 to 5){
    producerMessage(API_URL.keys.toList(args(0).toInt),producer)
  }

  producer.close()

  private def producerMessage(url: String, producer:  KafkaProducer[String, String] ) = {
    LOGGER.info("Validate date update")
    val htmlSource = Source.fromURL(url)

    val ListOfDates = pattern.findAllIn(htmlSource.mkString).distinct.toList
      .map(s => s.split(":\"").toList.last.substring(0,10)).distinct


    lazy val value_topic: String = API_URL.get(url).getOrElse("")
    lazy val topic : String = value_topic.split("/").last.replace(".json","")
    lazy val key : String = (topic+"." + new Timestamp(java.util.Calendar.getInstance.getTime.getTime)).replace(" ","")

    ListOfDates.size match {
      case 1 => (ServiceBucketAws.createBucket(BUCKET_NAME_STAGING),ServiceBucketAws.createBucket(BUCKET_NAME_RAW)) match {
          case (Right(x1),Right(x2)) => LOGGER.info(s"$x1 and $x2")
            producer.send(new ProducerRecord[String, String](topic, key, value_topic))
            LOGGER.info(s"Producer message with topic: $topic, key: $key, value: $value_topic")
          case (Left(e1),Left(e2)) => (e1.toString,e2.toString) match {
            case ("java.lang.Exception: Already Exist Bucket","java.lang.Exception: Already Exist Bucket") => producer.send(new ProducerRecord[String, String](topic, key, value_topic)).get()
              LOGGER.info(s"Producer message with topic: $topic, key: $key, value: $value_topic")
            case (_,_) => LOGGER.info("error")
          }
        }
    }
  }
}
