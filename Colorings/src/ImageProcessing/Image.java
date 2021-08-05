package ImageProcessing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//Класс-обертка для BufferedImage для инкапсуляции работы с файлами и использования Transformation
public class Image {
    private BufferedImage image;
    int width, height;

    public Image(String filepath) throws IOException {
        image = ImageIO.read(new File(filepath));
        width = image.getWidth();
        height = image.getHeight();
    }

   public BufferedImage getBufferedImage() {
        return image;
   }

   public void applyTransformation(Transformation transformation) {
        transformation.apply(image, width, height);
    }

    public void applyTransformationStack(TransformationStack stack) {stack.apply(image, width, height);}

    public void save(String filepath) throws IOException{
        ImageIO.write(image, "jpg", new File(filepath));
    }
}
