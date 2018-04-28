package com.ilias.Binarization;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

import com.ilias.Binarization.utils.ArrayUtils;
import com.ilias.Binarization.utils.ImageArrayShifting;


public class BradleyMethod implements ImageBinarizator{
    BufferedImage innerImage;

    int[][] integralImage;

    public BradleyMethod(BufferedImage innerImage) {
        this.innerImage = innerImage;
    }

    public BradleyMethod(String filePath) {
        File file = new File(filePath);
        try {
            innerImage = ImageIO.read(file);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read input image");
        }
    }
    public BradleyMethod(File file) {
        try {
            innerImage = ImageIO.read(file);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read input image");
        }
    }

    public int[][] binarizeImageToArray() {
        return new int[0][];
    }

    public BufferedImage binarizeImageToBufferedImage() {
        int w = innerImage.getWidth();
        int h = innerImage.getHeight();
        BufferedImage integralImage = new BufferedImage(w, h, innerImage.getType());
        int intImg[][] = ImageArrayShifting.getArrayFromImage(integralImage);
        intImg = ArrayUtils.getReverseArray(intImg);

        int lumArray[][] = toGrayImage(innerImage);
//        int innerImg[][] = ImageArrayShifting.getArrayFromImage(innerImage);
//        innerImg = getReverse(innerImg);
//
//        innerImg = convertToPositiveRGB(innerImg);

//        BufferedImage out = new BufferedImage(w, h, in.getType());
        int out[][] = new int[w][h];
        final int S = w/8;
        int s = S/2;
        final float t = 0.15f;

        for(int i=0; i<w; i++){
            int sum = 0;
            for(int j=0; j<h; j++){
                sum = sum + lumArray[i][j];
                if(i==0){
                    intImg[i][j] = sum;
                }else{
                    intImg[i][j] = intImg[i-1][j]+sum;
                }
            }
        }
//        BufferedImage ims = ImageArrayShifting.getImageFromArray(intImg);
//        File outPutFiles = new File("integralImage.png");
//        try {
//            ImageIO.write(ims, "png", outPutFiles);
//        }catch (Exception e){
//
//        }

//        integralImage = intImg;

        int x1 = 0;
        int x2 = 0;
        int y1 = 0;
        int y2 = 0;

        for(int i=0; i<w; i++){
            for(int j=0; j<h; j++){
                x1 = i - s;
                x2 = i + s;
                y1 = j - s;
                y2 = j + s;

                if (x1 < 0) {
                    x1 = 0;
                }
                if (x2 >= w) {
                    x2 = w-1;
                }
                if (y1 < 0) {
                    y1 = 0;
                }
                if (y2 >= h) {
                    y2 = h-1;
                }


//                if (x1 == 0)
//                    x1 = 1;
//                if (y1 == 0)
//                    y1 = 1;
//                if (x2 == 0)
//                    x2 = 1;
//                if (y2 == 0)
//                    y2 = 1;

                int count = (x2 - x1) * (y2 - y1);

                int sum = intImg[x2][y2] - intImg[x2][y1]
                        - intImg[x1][y2] + intImg[x1][y1];

                if(lumArray[i][j]*count < (sum*(100-15)/100)){
                    out[i][j] = 0;
                }else{
                    out[i][j] = 255;
                }
            }
        }

        BufferedImage im = ImageArrayShifting.getImageFromArray(out);
//        BufferedImage result = new BufferedImage(ims.getWidth(), ims.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        RescaleOp rescaleOp = new RescaleOp(50.0f, 80, null);
        rescaleOp.filter(im, im);  // Source and destination are the same.

//        File outPutFile = new File("out.png");
//        try {
//            ImageIO.write(im, "png", outPutFile);
//        }catch (Exception e){
//
//        }
        return im;

    }

    private static int[][] toGrayImage(BufferedImage in){
        int grayArray[][] = new int[in.getWidth()][in.getHeight()];
        //TODO: optimize "getRGB" method
        for(int i=0; i<in.getWidth(); i++)
            for(int j=0;j<in.getHeight();j++){
                Color tmp = new Color(in.getRGB(i, j));
                int luminance =  Math.round(tmp.getRed() * 0.2126f + tmp.getGreen() * 0.7152f + tmp.getBlue() * 0.0722f) ;
                grayArray[i][j] = luminance;
            }
        return grayArray;
    }
}
