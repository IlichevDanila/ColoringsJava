package ImageProcessing;

//Просто класс цвета
public class Color {
    private int pixel;  //Хранит 0xff<B><G><R>, где R, G и B - компоненты цвета в виде байт (от 0x00 до 0xff)

    public static final Color Black = new Color(0, 0,0);
    public static final Color White = new Color(255, 255, 255);

    public Color(int pxl){
        pixel = pxl | 0xff000000;
    }

    public Color(int r, int g, int b) {
        pixel = 0xff000000 | chopToRGB(b) << 16 | chopToRGB(g) << 8 | chopToRGB(r);
    }

    //Создает Color по RGB коду
    public Color(String RGB) {
        RGB = RGB.toLowerCase();
        pixel = 0xff000000;

        int[] values = new int[6];
        for(int i = 0; i < 6; i++) {
            values[i] = (int) RGB.charAt(5 - i);
            values[i] = (values[i] >= (int) 'a' && values[i] <= (int) 'f') ? 10 + values[i] - (int) 'a' : values[i] - (int) '0';
        }

        pixel |= ((values[5] << 4) + values[4]) << 16;
        pixel |= ((values[3] << 4) + values[2]) << 8;
        pixel |= ((values[1] << 4) + values[0]);
    }

    public int r(){return pixel & 0xff;}
    public int g(){return (pixel & 0xff00) >> 8;}
    public int b(){return (pixel & 0xff0000) >> 16;}

    public int toInt() {return pixel;}

    //Обрезает целое число до диапазона [0; 255]
    public static int chopToRGB(int val) {
        return Math.max(0, Math.min(255, val));
    }

    //Простое евклидово расстояние в R^3
    public static double distance(Color x, Color y) {
        double r = x.r() - y.r();
        double g = x.g() - y.g();
        double b = x.b() - y.b();
        return Math.sqrt(r*r + g*g + b*b);
    }
}
