package cz.uhk.fim.ase.router;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Main {

    final public static String DEFAULT_ADDRESS = "127.0.0.1";
    final public static String DEFAULT_INCOMING_PORT = "13337";
    final public static String DEFAULT_OUTCOMING_PORT = "14447";
    final public static int DEFAULT_THREADS = 4;

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            Options options = createCommandLineOptions();
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar target/router.jar", options);
                return;
            }

            String address = line.hasOption("a") ? line.getOptionValue("a") : DEFAULT_ADDRESS;
            String incomingPort = line.hasOption("i") ? line.getOptionValue("i") : DEFAULT_INCOMING_PORT;
            String outcomingPort = line.hasOption("o") ? line.getOptionValue("o") : DEFAULT_OUTCOMING_PORT;

            int threads = DEFAULT_THREADS;
            if (line.hasOption("t")) {
                try {
                    threads = line.hasOption("t") ? Integer.parseInt(line.getOptionValue("t")) : DEFAULT_THREADS;
                } catch (NumberFormatException e) {
                    logger.error("Threads count must be integer, using default value: " + threads);
                }
            }

            logger.info("Using address " + address + ":" + incomingPort + " for incoming communication");
            logger.info("Using address " + address + ":" + outcomingPort + " for outcoming communication");
            logger.info("Using " + threads + " threads");

            Server server = new Server(address, incomingPort, outcomingPort, threads);
            server.start();
        } catch (ParseException e) {
            logger.error("Command line parser failed", e);
        }
    }

    private static Options createCommandLineOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "Help");
        options.addOption("i", "incoming-port", true, "Server port for incoming communication");
        options.addOption("o", "outcoming-port", true, "Server port for outcoming communication");
        options.addOption("a", "address", true, "Server address");
        options.addOption("t", "threads", true, "Count of threads for communication layer (ZeroMQ I/O threads)");
        return options;
    }
}
