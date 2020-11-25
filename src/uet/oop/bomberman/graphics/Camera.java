package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Camera {
    GraphicsContext _gc;

    public static int getxOffset() {
        return xOffset;
    }

    public static void setOffset(double xOffset, double yOffset) {
        Camera.xOffset = (int) xOffset;
        Camera.yOffset = (int) yOffset;
    }

    public static int getyOffset() {
        return yOffset;
    }


    private static int xOffset, yOffset;

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    private static int WIDTH, HEIGHT;

    public Camera(int width, int height, GraphicsContext gc) {
        xOffset = 0;
        yOffset = 0;
        WIDTH = width;
        HEIGHT = height;
        _gc = gc;
    }

    void render() {
        //render map
        //render mob
    }

    public void drawImage(Image img, int x, int y) {
        _gc.drawImage(img, x - xOffset, y - yOffset);
    }

    public void prepare() {
        _gc.clearRect(0, 0, WIDTH, HEIGHT);

    }


}
