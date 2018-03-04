package dk.kb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        // write your code here

        ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
        ExecutorService multiExecutor = Executors.newFixedThreadPool(4);


        Producer producer = new ProducerImpl();

        ProducerRunner producerRunner = new ProducerRunner(200, multiExecutor, producer);
        Future<List<Future<String>>> megaFuts = singleExecutor.submit(producerRunner);
        logger.info("Submitted producer to thread pool");
        singleExecutor.shutdown();

        List<String> results;
        try {
            List<Future<String>> list = megaFuts.get();
            logger.info("ProducerImpl done");
            results = list.stream()
                    .map(stringFuture -> getFutureString(stringFuture))
                    .collect(Collectors.toList());
            logger.info("Consumers done");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info("Shutting down consumer thread pool");
        multiExecutor.shutdown();
        logger.info("Results are {}", results);
    }

    private static String getFutureString(Future<String> stringFuture) {
        try {
            return stringFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
