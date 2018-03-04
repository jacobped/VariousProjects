package dk.kb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class Consumer<String> implements Callable<String> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String imagePath;

    Consumer(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String call() throws Exception {
        logger.info("Tesseracted: {}", imagePath);
        Thread.sleep(250);

        return imagePath;
    }
}
