package ru.msk.java.spark

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

object StreamingSpark extends App{

  // Spark related
  val sparkConfig = new SparkConf().setMaster("local[*]").setAppName("SparkKafkaStreamTest")
  val sparkStreamingContext = new StreamingContext(sparkConfig, Seconds(10))
  sparkStreamingContext.sparkContext.setLogLevel("ERROR")

  // Setting up an access to Kafka
  val kafkaConfig = Map[String, Object](
    "client.dns.lookup" -> "resolve_canonical_bootstrap_servers_only",
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "kafkaSparkTestGroup",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  // Kafka topic to consume
  val kafkaTopics = Array("messages")

  // Creating Kafka Stream
  val kafkaRawStream: InputDStream[ConsumerRecord[String, String]] =
    KafkaUtils.createDirectStream[String, String](
      sparkStreamingContext,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](kafkaTopics, kafkaConfig)
    )

  val recordsCount: DStream[Long] = kafkaRawStream.count() // returns another DStream
  recordsCount.print()

  sparkStreamingContext.start() // start the computation
  sparkStreamingContext.awaitTermination() // await termination

}
