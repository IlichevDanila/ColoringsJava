package ImageProcessing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Класс набора цветов
public class ColorList {
    private ArrayList<Color> colors = new ArrayList<>();

    public ColorList(){}

    public static ColorList read(String filepath) throws IOException {
        ColorList result = new ColorList();
        Scanner sc = new Scanner(new File(filepath));
        String line = null;
        while(sc.hasNext()) {
            line = sc.nextLine();
            if(!line.startsWith("//")) {
                result.add(new Color(line));
            }
        }
        return result;
    }

    public void add(Color color) {
        colors.add(color);
    }

    public int size() {
        return colors.size();
    }

    //Возвращает из этого списка ближайший к данному цвету (в смысле евклидового расстояния)
    public Color getClosestTo(Color color) {
        double min_dst = -1.0;
        double dst;
        Color best_color = Color.Black;

        for(Color itr: colors) {
            dst = Color.distance(color, itr);
            if(min_dst < 0.0 || dst < min_dst) {
                min_dst = dst;
                best_color = itr;
            }
        }

        return best_color;
    }

    //Возвращает из данного списка N наиболее часто встречающихся в изображении Image цветов
    public ColorList getBest(int N, BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        if(N <= 0 || N >= colors.size()) {
            return this;
        }

        //Заполняем таблицу частот
        HashMap<Integer, Integer> colorsFreq = new HashMap<>();

        int pixel;
        int i, j;
        for(i = 0; i < width; i++) {
            for(j = 0; j < height; j++) {
                pixel = getClosestTo(new Color(image.getRGB(i, j))).toInt();
                if(!colorsFreq.containsKey(pixel)) {
                    colorsFreq.put(pixel, 1);
                }
                else {
                    colorsFreq.put(pixel, colorsFreq.get(pixel) + 1);
                }
            }
        }

        //Выбираем N наиболее частых
        ColorList result = new ColorList();
        int max = -1;
        int prev_max = -1;
        int bestColorCode = 0;
        for(i = 0; i < N; i++) {
            for (Map.Entry<Integer, Integer> entry : colorsFreq.entrySet()) {
                if ((prev_max == -1 || entry.getValue() < prev_max) && (max == -1 || entry.getValue() > max)) {
                    max = entry.getValue();
                    bestColorCode = entry.getKey();
                }
            }
            result.add(new Color(bestColorCode));
            prev_max = max;
            max = -1;
        }

        return result;
    }
}
