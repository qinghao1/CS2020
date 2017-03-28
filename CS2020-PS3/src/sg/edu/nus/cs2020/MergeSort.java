package sg.edu.nus.cs2020;

import java.lang.*;
import java.util.Arrays;

public class MergeSort {
	private static double[] merge(double[] a1, double[] a2) {
		int size = a1.length + a2.length;
		double[] ans = new double[size];
		int i1, i2;
		i1 = i2 = 0;
		for (int i = 0; i < size; i++) {
			if (i1 < a1.length && i2 < a2.length) {
				if (Math.floor(a1[i1]) < Math.floor(a2[i2])) {
					ans[i] = a1[i1++];
				} else {
					ans[i] = a2[i2++];
				}
			} else {
				ans[i] = i1 < a1.length ? a1[i1++] : a2[i2++];
			}

		}
		System.out.println(Arrays.toString(ans));
		return ans;
	}
	private static double[] mergesort(double[] arr) {
		if (arr.length == 1) {
			return arr;
		} else {
			//why does Arrays.copyOfRange work this way?? it makes no sense
			int middle = arr.length / 2;
			double[] left = Arrays.copyOfRange(arr, 0, middle);
			double[] right = Arrays.copyOfRange(arr, middle, arr.length);
			return merge(mergesort(left), mergesort(right));
		}
	}
	public static void sort(double[] arr) {
		double[] sorted = mergesort(arr);
		for (int i = 0; i < arr.length; i++) {
			arr[i] = sorted[i];
		}
	}
}
