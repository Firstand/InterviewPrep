package com.yonyou;

import org.apache.commons.lang.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class SpringcloudServiceProviderApplication9092Tests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        Integer[] nums = {33, 14, 38, 6, 1, 5, 89, 3};
        List<Integer> numList = Arrays.asList(nums);
        long startTime = System.currentTimeMillis();
        printSingleColor("排序后结果：", 31, 2, Arrays.toString(hillSort(nums)));
        printSingleColor("排序耗时：", 31, 2, (System.currentTimeMillis() - startTime) + "ms");
    }

    /**
     * 直接插入排序 自己实现的
     *
     * @param nums 待排序数组
     * @return 排序后数组
     */
    public static Integer[] directInsertionSort(Integer[] nums) {
        int temp;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[i]) {
                    temp = nums[j];
                    nums[j] = nums[i];
                    nums[i] = temp;
                }
            }
        }
        return nums;
    }

    /**
     * 希尔排序 自己实现的
     *
     * @param nums 待排序数组
     * @return 排序后数组
     */
    public static Integer[] hillSort(Integer[] nums) {
        if (nums == null || nums.length <= 0) {
            return nums;
        }
        int intervalNumber = nums.length / 2;
        int temp;
        while (intervalNumber != 0) {
            for (int i = 0; i < intervalNumber; i++) {
                for (int j = 0; j < nums.length/intervalNumber; j++) {
                    if (nums[j] > nums[j + intervalNumber]) {
                        temp = nums[j];
                        nums[j] = nums[j + intervalNumber];
                        nums[j + intervalNumber] = temp;
                        //System.out.print(intervalNumber + "：" + Arrays.toString(nums) + " " + nums[i] + "<-->" + nums[i + intervalNumber] + " flag：" + flag + "\n");
                    }
                }
            }
            intervalNumber = intervalNumber / 2;
        }
        return nums;
    }

    /**
     * 希尔排序优化 网上的
     *
     * @param arr 待排序数组
     * @return 排序后数组
     */
    public static Integer[] hillSort2(Integer[] arr) {
        int temp;
        for (int group = arr.length / 2; group > 0; group /= 2) {
            //对每一次遍历进行插入排序
            for (int i = group; i < arr.length; i++) {
                temp = arr[i];
                int j = i;
                if (arr[j] < arr[j - group]) {
                    while (j - group >= 0 && temp < arr[j - group]) {
                        arr[j] = arr[j - group];
                        j -= group;
                    }
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }


    /**
     * @param pattern 前面的图案 such as "=============="
     * @param code    颜色代号：背景颜色代号(41-46)；前景色代号(31-36)
     * @param n       数字+m：1加粗；3斜体；4下划线
     * @param content 要打印的内容
     */
    public static void printSingleColor(String pattern, int code, int n, String content) {
        System.out.format("%s\33[%d;%dm%s%n", pattern, code, n, content);
    }

}
