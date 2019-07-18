package com.kunlab.sort;

import java.util.Arrays;

/**
 * 选择排序
 * 1.在未排序序列中找到最小（大）元素，存放到排序序列的起始位置
 * 2.再从剩余未排序序列中继续寻找最小（大）元素，放到已排序序列的末尾
 * 3.以此类推，直到所有元素均排序完毕
 *
 * Created by 2206391776@qq.com on 2019/7/18
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] array = {1,3,4,2};

        for(int i=0; i < array.length; i++) {

            int minIndex = i;

            for(int j=i; j<array.length; j++) {
                if (array[j] < array[minIndex]) {  //找到最小的数
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }

        System.out.println(Arrays.toString(array));
    }
}
