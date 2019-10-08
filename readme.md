This is a simple example of using Spark Streaming to consume messages from Kafka Topic.
In order to make it work you should run your Kafka Broker locally or define a remote one in Scala sources
and produce some messages to 'messages' topic.

As a result you will get a console message shows how many records were consumed by Spark Stream 
for the last 10 seconds.
