package uet.oop.bomberman.utilities;

import uet.oop.bomberman.Game;

public class CoordinateConverter {
    public static int pixelToTile(double x) {
        return  (int)x/(Game.TILE_SIZE*Game.SCALE);
    }
    public double tileToPixel(double xTile, double yTile) {
        return 0;
    }
}
