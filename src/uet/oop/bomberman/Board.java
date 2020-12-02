package uet.oop.bomberman;

import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.mob.Balloom;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Oneal;
import uet.oop.bomberman.entities.tile.BrickTile;
import uet.oop.bomberman.entities.tile.GrassTile;
import uet.oop.bomberman.entities.tile.LayeredTile;
import uet.oop.bomberman.entities.tile.WallTile;
import uet.oop.bomberman.graphics.Camera;
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
    public static int TILE_SIZE = 16,
            TILE_WIDTH = 20,
            TILE_HEIGHT = 15,
            SCALE = 3,
            PIXEL_WIDTH = TILE_SIZE * TILE_WIDTH * SCALE,
            PIXEL_HEIGHT = TILE_SIZE * TILE_HEIGHT * SCALE;

    public KeyPolling get_input() {
        return _input;
    }

    KeyPolling _input;

    public static int getTileWidth() {
        return TILE_WIDTH;
    }

    public static int getTileHeight() {
        return TILE_HEIGHT;
    }

    public Board(Game game) {
        _camera = game._camera;
        _game = game;
        //createMap();
        _input = _game._input;
        loadMap(1);
        //Entity bomberman = new Bomber(1, 1, this);
        //mobs.add(bomberman);

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
        tiles.set(2 + 2 * Board.getTileWidth(), new WallTile(2, 2));
        tiles.set(2 + 4 * Board.getTileWidth(), new WallTile(2, 4));
        LayeredTile le = new LayeredTile(3, 5, new BrickTile(3, 5));
        tiles.set(3 + 5 * Board.getTileWidth(), le);
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

    public void loadMap(int level) {
        InputStream is = getClass().getClassLoader().getResourceAsStream("levels/Level" + level + ".txt");
        assert is != null;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringTokenizer st = null;
        try {
            st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int _level = Integer.parseInt(st.nextToken());
        TILE_HEIGHT = Integer.parseInt(st.nextToken());
        TILE_WIDTH = Integer.parseInt(st.nextToken());
        PIXEL_WIDTH = TILE_SIZE * TILE_WIDTH * SCALE;
        PIXEL_HEIGHT = TILE_SIZE * TILE_HEIGHT * SCALE;
        for (int j = 0; j < TILE_HEIGHT; j++) {
            for (int i = 0; i < TILE_WIDTH; i++) {
                char c = 0;
                try {
                    c = (char) br.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switch (c) {
                    case '#': {
                        tiles.add(new WallTile(i, j));
                        break;
                    }
                    case ' ': {
                        tiles.add(new GrassTile(i, j));
                        break;
                    }
                    case '*': {
                        LayeredTile le = new LayeredTile(i, j, new BrickTile(i, j));
                        tiles.add(le);
                        break;
                    }
                    case 'p': {
                        mobs.add(new Bomber(i, j, this));
                        tiles.add(new GrassTile(i, j));
                        break;
                    }
                    case '1': {
                        mobs.add(new Balloom(i, j));
                        tiles.add(new GrassTile(i, j));
                        break;
                    }
                    case '2': {
                        mobs.add(new Oneal(i, j));
                        tiles.add(new GrassTile(i, j));
                        break;
                    }
                    default: {
                        tiles.add(new GrassTile(i, j));
                        break;
                    }

                }
            }
            try {
                br.read();
                br.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public Entity getTilesAt(int XTile, int YTile) {
        if (XTile + YTile * Board.getTileWidth() < 0) return null;
        if (bombs.containsKey(XTile + YTile * Board.getTileWidth()))
            return bombs.get(XTile + YTile * Board.getTileWidth());
        return tiles.get(XTile + YTile * Board.getTileWidth());
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
        if (bombs.containsKey(xTile + yTile * Board.getTileWidth())) {
            return bombs.get(xTile + yTile * Board.getTileWidth());
        }
        return null;
    }

    public void addFlame(List<Flame> le) {
        flames.addAll(le);
    }

    public void clearFlames() {
        flames.clear();
    }

    public Entity getPlayer() {
        for (Entity e : mobs) {
            if (e instanceof Bomber) return e;
        }
        return mobs.get(0);
    }
}
