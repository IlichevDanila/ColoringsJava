package ImageProcessing;

import java.awt.image.BufferedImage;

//Фильтр смазывания
//Замещает каждую компоненту цвета пикселя на среднее значение соответствующей компоненты
//всех пикселей некоторой окрестности
public class Blur implements Transformation{
    private int x_size, y_size;

    public Blur(int x_size, int y_size) {
        //Разрешены только положительные неченые размеры (для установления точного центра окрестности)
        this.x_size = 2*(Math.max(0, x_size) / 2) + 1;
        this.y_size = 2*(Math.max(0, y_size) / 2) + 1;
    }

    @Override
    public void apply(BufferedImage image, int width, int height) {
        if(x_size == 0 || y_size == 0) {
            return;
        }

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
        //Определяем какая часть окрестности пересекается с изображением при совмещении её центра с (x, y)
        int x_center = x_size / 2;
        int y_center = y_size / 2;
        int left = Math.max(0, x_center - x);
        int right = Math.min(x_size, width - x + x_center);
        int bottom = Math.max(0, y_center - y);
        int top = Math.min(y_size, height - y + y_center);

        int r = 0;
        int g = 0;
        int b = 0;

        //Вычисляем среднее компонент по окрестности
        int i, j;
        Color pixel;
        for(i = left; i < right; i++) {
            for(j = bottom; j < top; j++) {
                pixel = new Color(image.getRGB(x + i - x_center, y + j - y_center));
                r += pixel.r();
                g += pixel.g();
                b += pixel.b();
            }
        }

        int area_square = (top - bottom) * (right - left);
        r /= area_square;
        g /= area_square;
        b /= area_square;
        return new Color(r, g, b);
    }
}
