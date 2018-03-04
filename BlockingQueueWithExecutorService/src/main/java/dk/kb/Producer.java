package dk.kb;

public interface Producer {

    public default String produce(int i) throws InterruptedException {

        //impl this to return path to image
        Thread.sleep(10);
        return i + "";
    }
}
