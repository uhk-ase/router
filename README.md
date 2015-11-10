# ASE Router #

Router for Agent Simulation Environment. 

Simple proxy for broadcast messages - used for distribution of yellow pages, time synchronisation, ...

### Requirements ###

- Java 7 or newer
- Maven

### Compile ###

    mvn package

### Running ###

	java -jar target/router.jar

### Parameters ###

	 -a,--address <arg>          Server address
	 -h,--help                   Help
	 -i,--incoming-port <arg>    Server port for incoming communication
	 -o,--outcoming-port <arg>   Server port for outcoming communication
	 -t,--threads <arg>          Count of threads for communication layer (ZeroMQ I/O threads)

Complete list of parameter can be also obtained via help parameter:

	java -jar target/router.jar --help