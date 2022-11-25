import javax.jms.*;
import javax.naming.Context;
import java.util.concurrent.ConcurrentHashMap;
import com.streamsets.pipeline.api.Field;
import com.streamsets.pipeline.stage.origin.scripting.ScriptingOriginBindings;
import com.streamsets.pipeline.stage.util.scripting.ScriptRecord;


  Hashtable<Object, Object> env = new Hashtable<Object, Object>();

  env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");

  env.put("amqp.saslMechanisms", "PLAIN");

  env.put("connectionfactory.myUri", "amqps://<amqp-destination-address:5672>?transport.trustStorePassword=<password>&transport.trustStoreLocation=</path/to/truststore/truststore.jks>&transport.verifyHost=<false>&transport.keyStorePassword=<password>&transport.keyStoreLocation=</path/to/truststore/keystore.jks>&transport.keyAlias=<your-alias>");

env.put("queue.myQueue", "<destination-queue-name>");

  javax.naming.Context context = new javax.naming.InitialContext(env);

  ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("myUri");
  connectionFactory.createConnection("<amqp-broker-user-name>","<amqp-broker-user-password>");
  connection.start();

Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
//  Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
  Destination destination = (Destination) context.lookup("myQueue");
  MessageConsumer messageConsumer = session.createConsumer(destination);
  Object listener = new ConsumerMessageListener("streamsets1", sdc);
  messageConsumer.setMessageListener(listener);

  while (listener.hasNext) {
            if (listener.sdc.isStopped()) {
              listener.hasNext = false;
            }
  }
  
  connection.close();
  context.close();
  if (listener.cur_batch.size() + listener.cur_batch.errorCount() + listener.cur_batch.eventCount() > 0) {
      listener.cur_batch.process(listener.entityName, listener.offset.toString())
  }

public class ConsumerMessageListener implements MessageListener {
    private String consumerName;
    public String entityName;
    private ScriptingOriginBindings sdc;
    public int offset;
    private String prefix;
    public ScriptingOriginBindings.PushSourceScriptBatch cur_batch;
    public Boolean hasNext;

  public ConsumerMessageListener(String consumerName, ScriptingOriginBindings sdc) {
        this.consumerName = consumerName;
        this.sdc = sdc;
        this.entityName = '';
        this.offset = 0;


        if (this.sdc.userParams.containsKey('recordPrefix')) {
            this.prefix = this.sdc.userParams.get('recordPrefix')
        } else {
            this.prefix = ''
        }
          this.hasNext = true;
          this.cur_batch = this.sdc.createBatch();

    } // end of constructor
 
    public void onMessage(Message message) {
      
        ScriptRecord record;
      
        TextMessage textMessage = (TextMessage) message;
        try {
          
        this.offset = this.offset + 1;

        record = this.sdc.createRecord('Qpid data' + this.offset.toString());
        record.value = ['<amqpIn>':textMessage.getText()];  
        this.cur_batch.add(record);

          if (this.cur_batch.size() >= this.sdc.batchSize) {
            // blocks until all records are written to all destinations
            // (or failure) and updates offset
            // in accordance with delivery guarantee
            this.cur_batch.process(this.entityName, this.offset.toString());
            this.cur_batch = this.sdc.createBatch();
              if (this.sdc.isStopped()) {
                  this.hasNext = false;
              }
         }
                   
          } catch (Exception e) {
          cur_batch.addError(record, e.toString())
          cur_batch.process(this.entityName, this.offset.toString());
          this.hasNext = false
        }
    }
 
}
