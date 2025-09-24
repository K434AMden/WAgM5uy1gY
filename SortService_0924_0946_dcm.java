// 代码生成时间: 2025-09-24 09:46:19
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * SortService provides a REST API to sort a list of integers using different algorithms.
 */
@Path("/sort")
public class SortService {

    // Define the list of integers to sort
    private List<Integer> numbers = Arrays.asList(5, 3, 8, 4, 2);

    /**
     * Returns the sorted list of integers using the bubble sort algorithm.
     *
     * @return a JSON formatted string with the sorted list.
     */
    @GET
    @Path("/bubble")
    @Produces(MediaType.APPLICATION_JSON)
    public String bubbleSort() {
        try {
            // Perform bubble sort
            int n = numbers.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (numbers.get(j) > numbers.get(j + 1)) {
                        // Swap the elements
                        Integer temp = numbers.get(j);
                        numbers.set(j, numbers.get(j + 1));
                        numbers.set(j + 1, temp);
                    }
                }
            }
            return Arrays.toString(numbers.toArray());
        } catch (Exception e) {
            // Handle any exceptions that occur during sorting
            return "Error during sorting: " + e.getMessage();
        }
    }

    /**
     * Returns the sorted list of integers using the quick sort algorithm.
     *
     * @return a JSON formatted string with the sorted list.
     */
    @GET
    @Path("/quick")
    @Produces(MediaType.APPLICATION_JSON)
    public String quickSort() {
        try {
            // Perform quick sort
            quickSort(numbers, 0, numbers.size() - 1);
            return Arrays.toString(numbers.toArray());
        } catch (Exception e) {
            // Handle any exceptions that occur during sorting
            return "Error during sorting: " + e.getMessage();
        }
    }

    /**
     * The recursive method for quick sort.
     *
     * @param list the list to sort.
     * @param low the starting index.
     * @param high the ending index.
     */
    private void quickSort(List<Integer> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSort(list, low, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, high);
        }
    }

    /**
     * The partition method for quick sort.
     *
     * @param list the list to partition.
     * @param low the starting index.
     * @param high the ending index.
     * @return the pivot index.
     */
    private int partition(List<Integer> list, int low, int high) {
        int pivot = list.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (list.get(j) < pivot) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    /**
     * Swaps two elements in the list.
     *
     * @param list the list containing the elements.
     * @param i the index of the first element.
     * @param j the index of the second element.
     */
    private void swap(List<Integer> list, int i, int j) {
        Integer temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
