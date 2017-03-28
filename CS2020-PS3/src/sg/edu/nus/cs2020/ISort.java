package sg.edu.nus.cs2020;


public interface ISort {

    /**
     * sort
     * @param <T> type of object to be sorted; must be Comparable to T
     * @param array items to sort
     */
    public <T extends Comparable<T>> void sort(T[] array);

}