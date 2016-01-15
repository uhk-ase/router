package cz.uhk.fim.ase.router;

import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Server {

    private static Logger logger = LoggerFactory.getLogger(Server.class);

    private String address;
    private String incomingPort;
    private String outcomingPort;
    private int threads;

    public Server(String address, String incomingPort, String outcomingPort, int threads) {
        this.address = address;
        this.incomingPort = incomingPort;
        this.outcomingPort = outcomingPort;
        this.threads = threads;
    }

    public void start() {
        logger.debug("Starting server for routing");

        ZMQ.Context context = ZMQ.context(threads);

        ZMQ.Socket subscriberSocket = context.socket(ZMQ.ROUTER);
        subscriberSocket.bind("tcp://" + address + ":" + incomingPort);

        ZMQ.Socket publisherSocket = context.socket(ZMQ.PUB);
        publisherSocket.bind("tcp://" + address + ":" + outcomingPort);

        logger.debug("Server for routing successfully started");
        while (!Thread.currentThread().isInterrupted()) {
            byte[] bytes = subscriberSocket.recv(0);
            publisherSocket.send(bytes);
        }
        logger.debug("Server for routing shutdown");
    }
}
