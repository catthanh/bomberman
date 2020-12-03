package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends ItemTile {
    public FlameItem(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        _img = Sprite.powerup_flames.getFxImage();
    }

    @Override
    public void activeItem(Bomber e) {
        Bomber.bomRadius++;
    }

    @Override
    public void update(double s) {

    }

}
