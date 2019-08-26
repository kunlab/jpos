package com.kunlab.sort;

import java.util.Arrays;

/**
 * Created by 2206391776@qq.com on 2019/8/26
 */
public class QuickSort {


    public static void quickSort(int[] array, int low, int high) {
        int i,j,temp, t;
        if(low > high)
            return;

        i = low;
        j = high;
        temp = array[low]; //基准位

        while(i < j) {

            //从右向左，寻找比基准位小的
            while(temp <= array[j] && i<j)
                j--;

            //从左向右，寻找比基准位大的
            while(temp >= array[i] && i<j)
                i++;

            if(i<j) { //左右交换
                t = array[j];
                array[j] = array[i];
                array[i] = t;
            }
        }

        //基准位替换
        array[low] = array[i];
        array[i] = temp;

        //递归调用左半数组
        quickSort(array, low, j-1);

        //递归调用右半数组
        quickSort(array, j+1, high);
    }


    public static void main(String[] args) {
        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
        quickSort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
    }

}
