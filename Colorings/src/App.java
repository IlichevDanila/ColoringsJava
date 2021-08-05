import ImageProcessing.*;

import java.io.IOException;

public class App {

    private static String imagePath = "resources/TestImage.jpg";
    private static String saveResultPath = "resources/output/result.jpg";
    private static String saveBordersPath = "resources/output/borders.jpg";
    private static String colorListPath = "resources/ColorTable.txt";

    public static void main(String[] args) {
        Image image;
        try {
            image = new Image(imagePath);
        }
        catch (IOException e) {
            System.out.println("Unable to open " + imagePath);
            return;
        }

        ColorList bigList = null;
        try {
            bigList = ColorList.read(colorListPath);
        }
        catch(IOException e) {
            System.out.println("Unable to open " + colorListPath);
        }

        ColorList colorList = bigList.getBest(12, image.getBufferedImage());

        TransformationStack stack = new TransformationStack();
        stack.add(new Simplification(colorList));
        stack.add(new ModeBlur(11, 11));
        stack.add(new Blur(11, 11));
        stack.add(new Simplification(colorList));
        stack.add(new ModeBlur(17, 17));

        image.applyTransformationStack(stack);

        try {
            image.save(saveResultPath);
        }
        catch(IOException e) {
            System.out.println("Unable to save " + saveResultPath);
            return;
        }

        image.applyTransformation(new Borders());

        try {
            image.save(saveBordersPath);
        }
        catch(IOException e) {
            System.out.println("Unable to save " + saveBordersPath);
            return;
        }
    }
}
