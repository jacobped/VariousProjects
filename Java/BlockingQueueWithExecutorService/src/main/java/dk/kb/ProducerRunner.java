package dk.kb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ProducerRunner implements Callable<List<Future<String>>> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    int spamAmount;
    ExecutorService multiExecutors;
    private Producer producer;

    ProducerRunner(int spamAmount, ExecutorService multiExecutor, Producer producer) {
        this.spamAmount = spamAmount;
        this.multiExecutors = multiExecutor;
        this.producer = producer;
    }

    @Override
    public List<Future<String>> call() throws Exception {
        List<Future<String>> futs = new ArrayList<>();

        for (int i = 0; i < spamAmount; i++) {

            String imagePath = producer.produce(i);
            logger.info(imagePath);

            Consumer<String> callable = new Consumer<>(imagePath);

            Future<String> fut = multiExecutors.submit(callable);

            futs.add(fut);
        }
        return futs;
    }
}
