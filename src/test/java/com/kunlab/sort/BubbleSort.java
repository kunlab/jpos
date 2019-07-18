package com.kunlab.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * 1.比较相邻的元素，如果第一个比第二个大，交换他们2个。
 * 2.从开始第一对到结尾最后一对，重复1中的工作，这样最后的元素会是最大的数。
 * 3.针对所有的元素重复以上的步骤，除了最后一个
 *
 * Created by 2206391776@qq.com on 2019/7/18
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {1,3,4,2};

        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < (array.length-1) - i; j++) {
                if(array[j+1] < array[j]) {
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }

        System.out.println(Arrays.toString(array));
    }
}
