package com.kunlab.sort;

import java.util.Arrays;

/**
 * 插入排序
 * 1.从第一个元素开始，第一个元素可以认为被排序了
 * 2.取出下一个元素，在已经排序的元素中从后往前扫描
 * 3.如果该元素（已排序）> 新元素，将该元素移到下一个位置
 * 4.重复步骤3，直到找到已排序的元素小于或等于新元素的位置，将新元素插入到该位置
 * 5.重复步骤2~4
 *
 * Created by 2206391776@qq.com on 2019/7/18
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] array = {4,3,2,1};

        for(int i = 0; i< array.length -1; i++) {
            int current = array[i+1];
            int preIndex = i;
            while(preIndex >= 0 && current < array[preIndex]) {
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }
            array[preIndex+1] = current;
        }

        System.out.println(Arrays.toString(array));
    }

}
