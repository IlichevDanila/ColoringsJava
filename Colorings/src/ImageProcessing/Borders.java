package ImageProcessing;

import java.awt.image.BufferedImage;

//Заменяет каждый пиксель на черный, если он является граничным, или на белый в противном случае
//Пиксель считается граничным, если его значение отличается хотя бы от одного
//соседнего пикселя (или если этот пиксель на границе изображения)
public class Borders implements Transformation{
    @Override
    public void apply(BufferedImage image, int width, int height) {
        int[][] new_img = new int[width][height];

        int i, j;
        for(i = 0; i < width; i++) {
            for(j = 0; j < height; j++) {
                new_img[i][j] = applyLocal(image, i, j, width, height).toInt();
            }
        }
        for(i = 0; i < width; i++) {
            for(j = 0; j < height; j++) {
                image.setRGB(i, j, new_img[i][j]);
            }
        }
    }

    private Color applyLocal(BufferedImage image, int x, int y, int width, int height) {
        if(x == 0 || x == width - 1 || y == 0 || y == height - 1) {
            return Color.Black;
        }

        int pixel = image.getRGB(x, y);
        if(pixel != image.getRGB(x - 1, y)) {
            return Color.Black;
        }
        if(pixel != image.getRGB(x + 1, y)) {
            return Color.Black;
        }
        if(pixel != image.getRGB(x , y - 1)) {
            return Color.Black;
        }
        if(pixel != image.getRGB(x, y + 1)) {
            return Color.Black;
        }

        return Color.White;
    }
}
