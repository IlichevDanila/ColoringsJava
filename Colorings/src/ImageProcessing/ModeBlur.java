package ImageProcessing;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

//Трансформация заменяет каждый пиксель на моду набора пикселей из некоторой его окрестности
public class ModeBlur implements Transformation{
    private int x_size, y_size;

    public ModeBlur(int x_size, int y_size) {
        //Разрешены только неченые размеры (для установления точного центра окрестности)
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
        //Определяем какая часть фильтра пересекается с изображением при совмещении его центра с (x, y)
        int x_center = x_size / 2;
        int y_center = y_size / 2;
        int left = Math.max(0, x_center - x);
        int right = Math.min(x_size, width - x + x_center);
        int bottom = Math.max(0, y_center - y);
        int top = Math.min(y_size, height - y + y_center);

        //Заполняем таблицу частот
        HashMap<Integer, Integer> colorsFreq = new HashMap<>();
        int pixel;
        int i, j;
        for(i = left; i < right; i++) {
            for(j = bottom; j < top; j++) {
                pixel = image.getRGB(x + i - x_center, y + j - y_center);
                if(!colorsFreq.containsKey(pixel)) {
                    colorsFreq.put(pixel, 1);
                }
                else {
                    colorsFreq.put(pixel, colorsFreq.get(pixel) + 1);
                }
            }
        }

        //Выбираем самый частый цвет
        int result = 0;
        int maxFreq = 0;
        for(Map.Entry<Integer, Integer> entry: colorsFreq.entrySet()) {
            if(entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                result = entry.getKey();
            }
        }
        return new Color(result);
    }
}
