package ImageProcessing;

import java.awt.image.BufferedImage;

//Интерфейс всех трансформаций изображений
public interface Transformation {
    void apply(BufferedImage image, int width, int height);
}
