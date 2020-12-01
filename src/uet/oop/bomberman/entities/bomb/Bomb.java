package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.BrickTile;
import uet.oop.bomberman.entities.tile.LayeredTile;
import uet.oop.bomberman.entities.tile.WallTile;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Convert;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    int _animate = 0;
    double timeLeft = 2;
    Board _board;
    private boolean exploded = false;
    int radius = 3;
    List<Flame> flames = new ArrayList<>();

    public Bomb(int xUnit, int yUnit, Board board) {

        x = xUnit * Sprite.SCALED_SIZE;
        y = yUnit * Sprite.SCALED_SIZE;
        _img = Sprite.bomb.getFxImage();
        _board = board;

    }

    @Override
    public void update(double s) {
        animate(s);
        explode(s);
    }

    public void forceExplode() {
        exploded = true;
    }

    private void explode(double s) {
        timeLeft -= s;
        if (timeLeft < 0) exploded = true;
        int xt = Convert.pixelToTile(x);
        int yt = Convert.pixelToTile(y);
        // create flame
        if (exploded) {// center
            addFlame(xt, yt, 4, true);
            //up
            for (int i = 1; i < radius; i++) {
                Entity e = _board.getTilesAt(xt, yt - i);
                if (e instanceof LayeredTile) {
                    ((LayeredTile) e).destroy();
                    if (!e.collide(this)) break;
                }
                Bomb b = _board.getBomb(xt, yt - i);
                

                if (_board.getTilesAt(xt, yt - i) instanceof WallTile) break;
                addFlame(xt, yt - i, 1, (i == radius - 1));
            }

            //right
            for (int i = 1; i < radius; i++) {
                Entity e = _board.getTilesAt(xt + i, yt);
                if (e instanceof LayeredTile) {
                    ((LayeredTile) e).destroy();
                    if (!e.collide(this)) break;
                }

                if (_board.getTilesAt(xt + i, yt) instanceof WallTile) break;
                addFlame(xt + i, yt, 0, (i == radius - 1));
            }

            //down
            for (int i = 1; i < radius; i++) {
                Entity e = _board.getTilesAt(xt, yt + i);
                if (e instanceof LayeredTile) {
                    ((LayeredTile) e).destroy();
                    if (!e.collide(this)) break;
                }

                if (_board.getTilesAt(xt, yt + i) instanceof WallTile) break;
                addFlame(xt, yt + i, 3, (i == radius - 1));
            }

            //left
            for (int i = 1; i < radius; i++) {
                Entity e = _board.getTilesAt(xt - i, yt);
                if (e instanceof LayeredTile) {
                    ((LayeredTile) e).destroy();
                    if (!e.collide(this)) break;
                }
                if (_board.getTilesAt(xt - i, yt) instanceof WallTile) break;
                addFlame(xt - i, yt, 2, (i == radius - 1));
            }

            _board.addFlame(flames);
        }
    }

    private boolean addFlame(int xTile, int yTile, int direction, boolean last) {
        int m = xTile + yTile * Game.TILE_WIDTH;
        if (m < 0 || m >= Game.TILE_WIDTH * Game.TILE_HEIGHT) return false;

        flames.add(new Flame(xTile, yTile, direction, last, this._board));
        return true;
    }

    public boolean isExploded() {
        return exploded;
    }

    private void animate(double s) {
        int m = 0;
        _animate += s * 1000;
        if (_animate > 10000) _animate = 0;
        if (_animate % 800 < 200) m = 0;
        else if (_animate % 800 < 400) m = 1;
        else if (_animate % 800 < 600) m = 2;
        else if (_animate % 800 < 800) m = 3;
        if (m == 0) _img = Sprite.bomb.getFxImage();
        if (m == 1) _img = Sprite.bomb_1.getFxImage();
        if (m == 2) _img = Sprite.bomb_2.getFxImage();
        if (m == 3) _img = Sprite.bomb_1.getFxImage();
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}