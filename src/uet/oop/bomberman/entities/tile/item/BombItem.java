package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends ItemTile {
    public BombItem(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        _img = Sprite.powerup_bombs.getFxImage();
    }

    @Override
    public void activeItem(Bomber e) {
        e.increaseBombNumber();
    }

    @Override
    public void update(double s) {

    }


}
