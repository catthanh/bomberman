package uet.oop.bomberman.utilities;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.graphics.Sprite;

public class Convert {
    public static int pixelToTile(double i) {
        return (int) (i) / (Sprite.SCALED_SIZE);
    }

    public double tileToPixel(double xTile, double yTile) {
        return 0;
    }
}
