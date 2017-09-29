package com.xz.test.test;

/**
 * 二分查找法
 * 
 * @author Administrator
 *
 */
public class BinarySearch {

	public static void main(String[] args) {
		int[] arr = new int[] { 2, 4, 6, 7, 9, 12, 34, 55, 77, 88 };
		int index = search(arr, 88);
		System.out.println(index);
	}

	private static int search(int[] arr, int des) {
		int start = 0;
		int end = arr.length - 1;
		while (start <= end) {
			int middle = (start + end) / 2;
			if (des == arr[middle]) {
				return middle;
			} else if (des < arr[middle]) {
				end = middle - 1;
			} else {
				start = middle + 1;
			}
		}
		return -1;
	}
}