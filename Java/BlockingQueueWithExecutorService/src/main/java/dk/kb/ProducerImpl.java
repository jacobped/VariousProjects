package dk.kb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerImpl implements Producer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public String produce(int i) throws InterruptedException {
        logger.info("Preprocessed: {}", i);

        //impl this to return path to image
        Thread.sleep(10);
        return i + "";
    }
}
