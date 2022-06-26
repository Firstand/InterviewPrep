package com.yonyou;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class SpringcloudServiceProviderApplicationTests {

    @Test
    void contextLoads() {
        System.out.print("wqe");
    }

    private static double [] nums = new double[1_0000_0000];
    private static Random r  =  new Random();
    private static double  result = 0.0,result1 = 0.0,result2 = 0.0;

    static {
        for (int i =0;i < nums.length ; i++){
            nums[i] = r.nextDouble();
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        System.out.print("耗时：" + (System.currentTimeMillis() - startTime));
    }

    public void toCalculate(){

    }

    static class ThreadTest extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("ThreadName:"+super.getName()+",循环："+i);
            }
        }
    }
}
