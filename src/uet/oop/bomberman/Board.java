package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.GrassTile;
import uet.oop.bomberman.entities.tile.WallTile;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.KeyPolling;

import java.util.ArrayList;
import java.util.List;

public class Board {
    Game _game;
    List<Entity> tiles = new ArrayList<>();
    List<Entity> mobs = new ArrayList<>();
    Camera _camera;

    public KeyPolling get_input() {
        return _input;
    }

    KeyPolling _input;
    public Board(Game game){
        _camera = game._camera;
        _game=game;
        createMap();
        _input=_game._input;
        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(),this);
        mobs.add(bomberman);
    }

    public void createMap() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 15; j++) {
                Entity object;
                if (j == 0 || j == 15 - 1 || i == 0 || i == 20 - 1) {
                    object = new WallTile(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new GrassTile(i, j, Sprite.grass.getFxImage());
                }
                tiles.add(object);
            }
        }
    }

    public Entity getTilesAt(int XTile, int YTile) {

        return tiles.get(XTile+YTile*Game.TILE_WIDTH);
    }
    public void update(double secondsSinceLastFrame){mobs.forEach(g->g.update(secondsSinceLastFrame));}
    public void render(Camera camera){

        camera.prepare();
        tiles.forEach(g -> g.render(camera));
        mobs.forEach(g -> g.render(camera));
    }
}
