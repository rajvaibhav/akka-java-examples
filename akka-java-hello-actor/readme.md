To run this example use: java -jar target/akka-java-hello-actor-0.1.0.jar


-------- Details -------




1. Entry point: MainApplication.java
	- Creates an ActorSystem
	- Creates an actor from HelloActor
	- Demonstrates usage of tell (fire and forget) and ask (if response is required)
2. configuration file: application.conf
	- Defines configuration for the actor system
3. HelloActor.java
	- Defines an Actor whose behavior is defined in onReceive(Object msg) methods.
4. HelloRequest.java
	- Request message for HelloActor
5. HelloResponse.java
	- Response message from HelloActor
6. pom.xml
	- Dependencies and build settings
