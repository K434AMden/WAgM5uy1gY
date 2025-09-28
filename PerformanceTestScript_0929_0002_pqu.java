// 代码生成时间: 2025-09-29 00:02:55
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Performance test script using JERSEY framework
 */
public class PerformanceTestScript extends JerseyTest {

    @Override
    protected Application configure() {
        // Return your JAX-RS application here
        return new MyApplication();
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Test
    public void testPerformance() throws InterruptedException {
        // Define the number of concurrent requests
        final int numberOfRequests = 100;
        int successfulRequests = 0;
        long startTime = System.currentTimeMillis();

        // Submit tasks to executor service
        for (int i = 0; i < numberOfRequests; i++) {
            executorService.submit(() -> {
                try {
                    Response response = target("api/resource")
                            .request()
                            .post(Entity.text("test"));
                    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                        successfulRequests++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // Shutdown the executor service and wait for tasks to complete
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();

        // Calculate and print performance metrics
        System.out.println("Total successful requests: " + successfulRequests);
        System.out.println("Total time taken: " + (endTime - startTime) + " ms");
    }

    @Override
    protected void tearDown() throws Exception {
        // Clean up resources if necessary
        executorService.shutdownNow();
        super.tearDown();
    }
}
