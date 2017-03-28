package sg.edu.nus.cs2020;

import java.util.*;

public class QuickSelect {
    /**
     * Returns kth smallest element in int array in O(n) time
     * @param arr the array
     * @param k position of element
     * @return the element
     */
    static int select(int[] arr, int k) {
        try {
            if (k > arr.length || k < 1) throw new IllegalArgumentException("k is wrong!");
            int[] tempArr = Arrays.copyOf(arr, arr.length);
            while (true) {
                int index = (int) (Math.random() * tempArr.length);
                int position = partition(tempArr, index); //index of pivot element in sorted array
                if (position == k - 1) {
                    //pivot is the element we want
                    return tempArr[position];
                }
                if (position + 1 > k) {
                    // pivot position is greater than k
                    tempArr = Arrays.copyOfRange(tempArr, 0, position);
                } else {
                    //pivot position is smaller than k
                    k -= position + 1;
                    tempArr = Arrays.copyOfRange(tempArr, position + 1, tempArr.length);
                }
            }
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Paritions array into two using arr[index] as pivot, returns the position of
     * pivot in sorted array
     * @param arr the array
     * @param index the index
     * @return position of pivot
     */
     static int partition(int[] arr, int index) {
         try {
             if (index >= arr.length || index < 0) throw new IllegalArgumentException("Partition index out of bounds");
             int pivot = arr[index];
             int low = 0;
             int high = arr.length - 1;
             while (low < high) {
                 while (arr[high] > pivot) high--;
                 while (arr[low] < pivot) low++;
                 swap(arr, low, high);
             }
            return low;
         }
         catch (IllegalArgumentException e) {
             e.printStackTrace();
             return 0;
         }
    }

    /**
     * Swaps arr[i] and arr[j]
     * @param arr
     * @param i
     * @param j
     */
     static void swap(int[] arr, int i, int j) {
        try {
            if (i >= arr.length || j >= arr.length) throw new IllegalArgumentException("Wrong i and j");
            if (i == j) return;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}