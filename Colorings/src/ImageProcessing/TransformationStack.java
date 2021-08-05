package ImageProcessing;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

//"Стек" трансформаций, хранящий их для последовательного применения к изображению
public class TransformationStack {
    private ArrayList<Transformation> stack = new ArrayList<>();

    public void add(Transformation transformation) {
        stack.add(transformation);
    }

    public void apply(BufferedImage image, int width, int height) {
        for(Transformation transformation: stack) {
            transformation.apply(image, width, height);
        }
    }
}
