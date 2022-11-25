### Motivation

[Apache Qpid](https://qpid.apache.org/index.html) is an AMQP broker. Tests have been made, and Streamsets Data Collector’s (SDC) standard stages RabbitMQ and VMS are not capable of integrating with Apache Qpid. Another approach, which is discussed below, is by using Groovy evaluator. 

Java-based [VMS client](https://qpid.apache.org/components/jms/index.html) library can be integrated into SDC to read messages from Qpid queues or send messages to Qpid.

### Qpid Java VMS Client
Current SDC versions are supported for Java8 only. That makes it impossible to use the latest Qpid VMS client libraries, as from version 1.0.0 Java11 is required to build them. As an alternative, the [latest pre-1.0.0 version libraries](https://mvnrepository.com/artifact/org.apache.qpid/qpid-jms-client/0.61.0) can be used. One can either manually pick and download qpid-jms-client-0.61.0.jar and related files from MVN Repository using the link above, or use the following list:
```
https://repo1.maven.org/maven2/io/netty/netty-resolver/4.1.72.Final/netty-resolver-4.1.72.Final.jar
https://repo1.maven.org/maven2/io/netty/netty-buffer/4.1.72.Final/netty-buffer-4.1.72.Final.jar
https://repo1.maven.org/maven2/io/netty/netty-common/4.1.72.Final/netty-common-4.1.72.Final.jar
https://repo1.maven.org/maven2/io/netty/netty-handler/4.1.72.Final/netty-handler-4.1.72.Final.jar
https://repo1.maven.org/maven2/io/netty/netty-codec/4.1.72.Final/netty-codec-4.1.72.Final.jar
https://repo1.maven.org/maven2/io/netty/netty-transport/4.1.72.Final/netty-transport-4.1.72.Final.jar
https://repo1.maven.org/maven2/io/netty/netty-transport-native-epoll/4.1.72.Final/netty-transport-native-epoll-4.1.72.Final.jar
https://repo1.maven.org/maven2/io/netty/netty-transport-native-kqueue/4.1.72.Final/netty-transport-native-kqueue-4.1.72.Final.jar
https://repo1.maven.org/maven2/io/netty/netty-codec-http/4.1.72.Final/netty-codec-http-4.1.72.Final.jar
https://repo1.maven.org/maven2/org/apache/geronimo/specs/geronimo-jms_2.0_spec/1.0-alpha-2/geronimo-jms_2.0_spec-1.0-alpha-2.jar
https://repo1.maven.org/maven2/org/apache/qpid/proton-j/0.33.10/proton-j-0.33.10.jar
https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar
https://repo1.maven.org/maven2/org/apache/qpid/qpid-jms-client/0.61.0/qpid-jms-client-0.61.0.jar
```
