package uet.oop.bomberman;

import javafx.geometry.Point2D;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.BrickTile;
import uet.oop.bomberman.entities.tile.GrassTile;
import uet.oop.bomberman.entities.tile.LayeredTile;
import uet.oop.bomberman.entities.tile.WallTile;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.KeyPolling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Board {
    Game _game;
    List<Entity> tiles = new ArrayList<>();
    List<Entity> mobs = new ArrayList<>();
    Map<Integer, Bomb> bombs = new HashMap<>();
    List<Entity> flames = new ArrayList<>();
    Camera _camera;

    public KeyPolling get_input() {
        return _input;
    }

    KeyPolling _input;

    public Board(Game game) {
        _camera = game._camera;
        _game = game;
        createMap();
        _input = _game._input;
        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), this);
        mobs.add(bomberman);
    }

    public void createMap() {

        for (int j = 0; j < 15; j++) {
            for (int i = 0; i < 20; i++) {
                Entity object;

                {
                    object = new GrassTile(i, j);
                }
                tiles.add(object);
            }
        }
        tiles.set(2 + 2 * Game.TILE_WIDTH, new WallTile(2, 2));
        tiles.set(2 + 4 * Game.TILE_WIDTH, new WallTile(2, 4));
        LayeredTile le = new LayeredTile(3, 5, new BrickTile(3, 5));
        tiles.set(3 + 5 * Game.TILE_WIDTH, le);
        for (int j = 0; j < 15; j++) {
            for (int i = 0; i < 20; i++) {
                if (getTilesAt(i, j) instanceof WallTile) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    public void loadMap(String fileName) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("/levels/Level1.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int level = Integer.parseInt(st.nextToken());


    }

    public Entity getTilesAt(int XTile, int YTile) {
        if (XTile + YTile * Game.TILE_WIDTH < 0) return null;
        return tiles.get(XTile + YTile * Game.TILE_WIDTH);
    }

    public void update(double secondsSinceLastFrame) {
        updateTiles(secondsSinceLastFrame);

        mobs.forEach(g -> g.update(secondsSinceLastFrame));
        updateBomb(secondsSinceLastFrame);
        updateFlames(secondsSinceLastFrame);

    }

    private void updateTiles(double secondsSinceLastFrame) {
        tiles.forEach(g -> g.update(secondsSinceLastFrame));
    }

    private void updateFlames(double s) {
        Iterator<Entity> flamesIterator = flames.iterator();
        while (flamesIterator.hasNext()) {
            Flame f = (Flame) flamesIterator.next();
            f.update(s);
            if (f.getTimeLeft() < 0) {
                flamesIterator.remove();
            }
        }
    }

    private void updateBomb(double s) {

        Iterator<Map.Entry<Integer, Bomb>> bombIterator = bombs.entrySet().iterator();
        while (bombIterator.hasNext()) {
            Map.Entry b = bombIterator.next();
            Bomb bb = (Bomb) b.getValue();
            bb.update(s);
            if (bb.isExploded()) {
                bombIterator.remove();
            }
        }
    }

    public void render(Camera camera) {
        camera.prepare();
        tiles.forEach(g -> g.render(camera));
        bombs.forEach((g, v) -> v.render(camera));
        mobs.forEach(g -> g.render(camera));
        flames.forEach(g -> g.render(camera));
    }

    public void addBomb(Bomb e) {
        if (!bombs.containsKey(e.getArrayIndex()))
            bombs.put(e.getArrayIndex(), e);
    }

    public Bomb getBomb(int xTile, int yTile) {
        if (bombs.containsKey(xTile + yTile * Game.TILE_WIDTH)) {
            return bombs.get(xTile + yTile * Game.TILE_WIDTH);
        }
        return null;
    }

    public void addFlame(List<Flame> le) {
        flames.addAll(le);
    }

    public void clearFlames() {
        flames.clear();
    }
}
