package com.ilias.Binarization;

import java.awt.image.BufferedImage;

public interface ImageBinarizator {
    int[][] binarizeImageToArray();
    BufferedImage binarizeImageToBufferedImage();
}
