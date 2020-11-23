package uet.oop.bomberman;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public class Camera {
    GraphicsContext _gc;

    public static int getxOffset() {
        return xOffset;
    }

    public static void setxOffset(int xOffset) {
        Camera.xOffset = xOffset;
    }

    public static int getyOffset() {
        return yOffset;
    }

    public static void setyOffset(int yOffset) {
        Camera.yOffset = yOffset;
    }

    private static int xOffset, yOffset;

    private static  int WIDTH, HEIGHT;
    Camera(int width, int height, GraphicsContext gc) {
        xOffset = 0;
        yOffset = 0;
        WIDTH = width;
        HEIGHT = height;
        _gc = gc;
    }
    void render(){
        //render map
        //render mob
    }

    public void drawImage(Image img, int x, int y) {
        _gc.drawImage(img,x-xOffset,y-yOffset);
    }

    public void prepare() {
        _gc.clearRect(0, 0, WIDTH, HEIGHT);

    }


}
