package ImageProcessing;

import java.awt.image.BufferedImage;

//Трансформация замещает каждый пиксель изображения близжайшим цветом из некоторого ColorList
public class Simplification implements Transformation{
    private ColorList colorList;

    public Simplification(ColorList colorList) {
        this.colorList = colorList;
    }

    @Override
    public void apply(BufferedImage image, int width, int height) {
        if(colorList == null || colorList.size() == 0) {
            return;
        }

        int[][] new_img = new int[width][height];

        int i, j;
        for(i = 0; i < width; i++) {
            for(j = 0; j < height; j++) {
                new_img[i][j] = applyLocal(image, i, j).toInt();
            }
        }
        for(i = 0; i < width; i++) {
            for(j = 0; j < height; j++) {
                image.setRGB(i, j, new_img[i][j]);
            }
        }
    }

    private Color applyLocal(BufferedImage image, int x, int y) {
        return colorList.getClosestTo(new Color(image.getRGB(x, y)));
    }
}
