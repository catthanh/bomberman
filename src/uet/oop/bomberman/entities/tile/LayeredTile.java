package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.LinkedList;

public class LayeredTile extends Tile {
    LinkedList<Tile> _entities = new LinkedList<>();

    public LayeredTile(int xUnit, int yUnit, Tile... entities) {
        super(xUnit, yUnit);
        _entities.add(new GrassTile(xUnit, yUnit));
        for (Tile e : entities) _entities.add(e);
    }


    @Override
    public void update(double s) {
        Tile t = _entities.getLast();
        if (t.isDestroyed()) _entities.removeLast();
        t.update(s);
    }

    @Override
    public Image get_img() {
        return _entities.getLast().get_img();
    }

    @Override
    public boolean collide(Entity e) {
        return _entities.getLast().collide(e);
    }

    @Override
    public void destroy() {
        _entities.getLast().destroy();
    }
}
