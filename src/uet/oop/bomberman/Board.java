package uet.oop.bomberman;

import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.entities.mob.enemy.Balloom;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.enemy.Oneal;
import uet.oop.bomberman.entities.tile.*;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
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
    List<Bomber> bombers = new ArrayList<>();
    Map<Integer, Bomb> bombs = new HashMap<>();
    Map<Integer, Flame> flames = new HashMap<>();
    Camera _camera;
    public static int level = 1;
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


    public void loadMap(int level) {
        tiles.clear();
        mobs.clear();
        bombers.clear();
        bombs.clear();
        flames.clear();
        Bomber.bomRadius = 1;
        Bomber.bombNumber = 1;
        Bomber.speedLevel = 1.0;
        Camera.reset();
        InputStream is = getClass().getClassLoader().getResourceAsStream("levels/Level" + level + ".txt");
        assert is != null;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringTokenizer st = null;
        try {
            st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        level = Integer.parseInt(st.nextToken());
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
                        //mobs.add(new Bomber(i, j, this));
                        bombers.add(new Bomber(i, j, this));
                        tiles.add(new GrassTile(i, j));
                        break;
                    }
                    case '1': {
                        mobs.add(new Balloom(i, j, this
                        ));
                        tiles.add(new GrassTile(i, j));
                        break;
                    }
                    case '2': {
                        mobs.add(new Oneal(i, j, this));
                        tiles.add(new GrassTile(i, j));
                        break;
                    }
                    case 'x': {
                        tiles.add(new LayeredTile(i, j, new PortalTile(i, j), new BrickTile(i, j)));
                        break;
                    }
                    case 'b': {
                        tiles.add(new LayeredTile(i, j, new BombItem(i, j), new BrickTile(i, j)));
                        break;
                    }
                    case 'f': {
                        tiles.add(new LayeredTile(i, j, new FlameItem(i, j), new BrickTile(i, j)));
                        break;
                    }
                    case 's': {
                        tiles.add(new LayeredTile(i, j, new SpeedItem(i, j), new BrickTile(i, j)));
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
        if (flames.containsKey(XTile + YTile * Board.getTileWidth()))
            return flames.get(XTile + YTile * Board.getTileWidth());
        return tiles.get(XTile + YTile * Board.getTileWidth());
    }

    public void update(double secondsSinceLastFrame) {
        updateTiles(secondsSinceLastFrame);
        updateMobs(secondsSinceLastFrame);

        updateBomb(secondsSinceLastFrame);
        updateFlames(secondsSinceLastFrame);

    }

    private void updateMobs(double secondsSinceLastFrame) {
        if (mobs.size() == 0) {
            PortalTile.passable = true;
            if (PortalTile.passed) levelUp();
        }
        if (!getPlayer().is_alive()) {
            Bomber.trialLeft--;
            if (Bomber.trialLeft < 0) {
                Game._phase = GamePhase.GAME_OVER;
                Bomber.trialLeft = 2;
                ScorePanel.timeLeft = 200;
            }
            loadMap(level);
        }
        bombers.forEach(g -> g.update(secondsSinceLastFrame));
        Iterator<Entity> mobsIterator = mobs.listIterator();
        while (mobsIterator.hasNext()) {
            Mob m = (Mob) mobsIterator.next();
            m.update(secondsSinceLastFrame);
            if (!m.is_alive()) {
                mobsIterator.remove();
            }
        }
        //mobs.forEach(g -> g.update(secondsSinceLastFrame));
    }

    private void levelUp() {
        loadMap(++level);
        Game._phase = GamePhase.GAME_LEVEL_PRELOAD;
        ScorePanel.timeLeft = 200;
    }

    private void updateTiles(double secondsSinceLastFrame) {

        tiles.forEach(g -> g.update(secondsSinceLastFrame));
    }

    private void updateFlames(double s) {
        Iterator<Map.Entry<Integer, Flame>> flamesIterator = flames.entrySet().iterator();
        while (flamesIterator.hasNext()) {
            Map.Entry<Integer, Flame> f = flamesIterator.next();
            Flame ff = f.getValue();
            ff.update(s);
            if (ff.getTimeLeft() < 0) {
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
        flames.forEach((g, v) -> v.render(camera));
        mobs.forEach(g -> g.render(camera));
        bombs.forEach((g, v) -> v.render(camera));
        bombers.forEach(g -> g.render(camera));
    }

    public boolean containsBomb(Bomb e) {
        return bombs.containsKey(e.getArrayIndex());
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

    public void addFlame(Map<Integer, Flame> me) {
        flames.putAll(me);
    }

    public void clearFlames() {
        flames.clear();
    }

    public Bomber getPlayer() {
        return bombers.get(0);
    }
}
