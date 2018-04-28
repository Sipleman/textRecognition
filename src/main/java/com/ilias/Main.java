package com.ilias;

import com.ilias.Binarization.BradleyMethod;
import com.ilias.Binarization.ImageBinarizator;
import net.sourceforge.tess4j.Tesseract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{
        File image = new File("booksRu.jpg");
//        Tesseract tessInst = new Tesseract();

        ImageBinarizator method = new BradleyMethod(image);
        BufferedImage img = method.binarizeImageToBufferedImage();
        saveImageToFile("output.png", img, "png");

    }
    private static void saveImageToFile(String path, BufferedImage img, String type) throws IOException{
        File file = new File(path);
        ImageIO.write(img, type, file);
    }
}
