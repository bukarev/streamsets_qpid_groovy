import javax.jms.*;
import javax.naming.Context;
import java.util.concurrent.ConcurrentHashMap;

// Establish a connection with AMQP destination
Hashtable<Object, Object> env = new Hashtable<Object, Object>();

env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");

env.put("amqp.saslMechanisms", "PLAIN");
env.put("connectionfactory.myUri", "amqps://<amqp-destination-address:5672>?transport.trustStorePassword=<password>&transport.trustStoreLocation=</path/to/truststore/truststore.jks>&transport.verifyHost=<false>&transport.keyStorePassword=<password>&transport.keyStoreLocation=/path/to/truststore/keystore.jks&transport.keyAlias=<your-alias>");

env.put("queue.myQueue", "<destination-queue-name>");

javax.naming.Context context = new javax.naming.InitialContext(env);

ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("myUri");
Connection connection = connectionFactory.createConnection("<amqp-broker-user-name>","<amqp-broker-user-password>");

connection.start();

Session session = connection.createSession();
Destination destination = (Destination) context.lookup("myQueue");
MessageProducer messageProducer = session.createProducer(destination);

// Iterate through incoming records and send out the contents of amqpOut field
// It's assumed the amqpOut field is STRING
records = sdc.records

for (record in records) {
    try {

        TextMessage message = session.createTextMessage(record.value['<amqpOut>']);
        messageProducer.send(message);
        
        // Write a record to the processor output
        sdc.output.write(record)
    } catch (e) {
        // Write a record to the error pipeline
        sdc.log.error(e.toString(), e)
        sdc.error.write(record, e.toString())
    }
}

// Close the connection
  connection.close();
  context.close();
