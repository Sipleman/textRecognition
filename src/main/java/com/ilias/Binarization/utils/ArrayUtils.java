package com.ilias.Binarization.utils;

public class ArrayUtils {
    public static int[][] getReverseArray(int[][] arr){
        int height = arr.length;
        int width = arr[0].length;
        int out[][] = new int[width][height];

        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                out[i][j] = arr[j][i];
            }
        }
        return out;
    }
}
