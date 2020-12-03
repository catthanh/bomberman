package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends ItemTile {
    public SpeedItem(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        _img = Sprite.powerup_speed.getFxImage();
    }

    @Override
    public void activeItem(Bomber e) {
        Bomber.speedLevel++;
    }

    @Override
    public void update(double s) {

    }

}
