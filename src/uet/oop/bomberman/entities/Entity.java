package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Convert;

public abstract class Entity {
    public double getX() {
        return x;
    }

    public int getXTile() {
        return Convert.pixelToTile(x);
    }

    public double getY() {
        return y;
    }

    public int getYTile() {
        return Convert.pixelToTile(y);
    }

    public int getArrayIndex() {
        return getXTile() + getYTile() * Board.getTileWidth();
    }

    //Tọa độ X tính từ góc trái trên trong Canvas
    protected double x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected double y;

    protected Image _img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Image get_img() {
        return _img;
    }


    public void render(Camera camera) {
        camera.drawImage(get_img(), (int) x, (int) y);
    }

    public abstract void update(double s);

    public abstract boolean collide(Entity e);
}
