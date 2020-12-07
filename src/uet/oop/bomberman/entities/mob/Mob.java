package uet.oop.bomberman.entities.mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Convert;

public abstract class Mob extends Entity {
    protected int _direction = -1;
    protected boolean _moving = false;
    protected boolean _alive = true;
    protected boolean _dying = false;
    protected Board _board;
    protected int _animate = 0;

    public boolean is_alive() {
        return _alive;
    }

    public Mob(int xUnit, int yUnit, Image img, Board board) {
        x = xUnit * Sprite.SCALED_SIZE;
        y = yUnit * Sprite.SCALED_SIZE;
        _img = img;
        _board = board;
    }


    @Override
    public abstract boolean collide(Entity e);

    public void kill() {
        _dying = true;
        _animate = 0;
    }

    public boolean movable(double xx, double yy) {

        double top = yy;
        double bot = yy + Sprite.SCALED_SIZE * 15.0 / 16.0;
        double left = xx;
        double right = xx + Sprite.SCALED_SIZE * 12.0 / 16.0;

        return
                _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(top)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(left), Convert.pixelToTile(bot)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(top)).collide(this) &&
                        _board.getTilesAt(Convert.pixelToTile(right), Convert.pixelToTile(bot)).collide(this);
    }
}
