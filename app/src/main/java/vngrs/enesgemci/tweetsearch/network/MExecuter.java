package vngrs.enesgemci.tweetsearch.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class MExecuter {

    private static MExecuter instance;
    private ExecutorService executor;

    public static MExecuter getInstance() {
        if (instance == null) {
            instance = new MExecuter();
        }

        return instance;
    }

    private MExecuter() {
        createExecuter();
    }

    private void createExecuter() {
        executor = Executors.newCachedThreadPool(Thread::new);
    }

    public void execute(Runnable runnable) {
        if (executor == null) {
            createExecuter();
        }

        executor.execute(runnable);
    }

    public void destroy() {
        try {
            if (executor != null) {
                executor.shutdown();
            }

            executor = null;
            instance = null;
        } catch (Exception e) {
        }
    }
}