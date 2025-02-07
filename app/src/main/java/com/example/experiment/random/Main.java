package com.example.experiment.random;

public class Main {
    public static void main(String[] args) {
        int x = 2;
        int y = 3;
        int [] a = new int[1];
        a[0] = 5;
        int z = x* (a[0]);
        System.out.println(z);
    }


    public int[] productExceptSelf(int[] nums) {
        int [] arr = new int[nums.length];
        int product = 1;
        for (int num : nums) {
            product *= num;
        }
        for(int i=0; i<nums.length; i++) {
            nums[i] = product/(nums[i]);
        }
        return nums;
    }
}
