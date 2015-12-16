package Base;

/**
 *  Game Start/Over
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import Map.IMapCheckPoint;
import Map.ActionMap;
import Map.LabirintMap;
import Map.BossMap;
import Map.MapWay;
import Map.MapWayFind;
import Paint.Canvas;
import Paint.Tile;
import Paint.Unit;

public class Game {

	private Canvas canvas;
	private Player player;
	private ActionMap map;
	private PlayAudio audio;
	private List<Bot> bots;
	private static Timer mTimer;
	/**
	 * Use Isometric projection
	 */
	final public static boolean USE_ISO = true;

	/**
	 *  Indent by painting on the right
	 */
	final public static int OFFSET_MAP_X = 320;

	/**
	 *  timer of the floor
	 */
	private int timerTile = -1;
	/**
	 * tile with fire
	 */
	private int deadTileX = 0, deadTileY = 0;
	/**
	 * is keyEvent or no 1-true
	 */
	private int flag = 0;
	private int flag1 = 0;

	

	public void start() {

		canvas = new Canvas();
		player = new Player();
		javax.swing.JFrame f = new javax.swing.JFrame();
		f.setLayout(null);
		f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		f.setResizable(false);
		canvas.setBounds(0, 0, 640, 480);
		f.add(canvas);
		f.setTitle("Игра");
		f.setBounds(300, 300, 640, 480);
		f.setVisible(true);
		Listener();
		bots = new ArrayList<Bot>();
		setMap(1);
		audio = new PlayAudio();
		URL url1 = this.getClass().getResource("/sound/3.wav");
		audio.play(url1);

		Timer mTimer = new Timer();
		MyTimerTask mMyTimerTask = new MyTimerTask();
		mTimer.schedule(mMyTimerTask, 0, 100);

	}

	/**
	 * stop 
	 */
	public void stop(String name) {

		canvas.addRenders(new Unit() {
			@Override
			public void render(Graphics g) {
				Image image = new ImageIcon(getClass().getResource(name)).getImage();
				g.drawImage(image, 0, 0, 640, 480, null);

			}

			@Override
			public int getDeep() {

				return Integer.MAX_VALUE;

			}
		});
		canvas.repaint();
		unListener();
		mTimer.cancel();
	}

	/**
	 * use mouse and key
	 */
	public void Listener() {
		canvas.setFocusable(true);
		canvas.addKeyListener(keyListener);
		canvas.addMouseListener(mouseListener);
	}

	/**
	 * don't use mouse and key
	 */
	public void unListener() {
		canvas.setFocusable(false);
		canvas.removeKeyListener(keyListener);
		canvas.removeMouseListener(mouseListener);
	}

	/**
	 * keyEvent
	 */
	public KeyAdapter keyListener = new KeyAdapter() {

		@Override
		public void keyPressed(KeyEvent e) {

			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN: {			
				player.setDirect("DOWN");
				player.turnBoth();
				break;
			}
			case KeyEvent.VK_UP: {
				player.setDirect("UP");
				player.turnTop();
				break;
			}
			case KeyEvent.VK_RIGHT: {
				player.setDirect("RIGHT");
				player.turnRight();
				break;
			}
			case KeyEvent.VK_LEFT: {
				player.setDirect("LEFT");
				player.turnLeft();
				break;
			}
			}
			flag = flag + 1;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.setDirect("Null1");
			}
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
				player.setDirect("Null2");
			}
		}

	};
	/**
	 * mouseEvent
	 */
	public MouseAdapter mouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			flag = 0;

			int startTileX, startTileY;
			if (USE_ISO) {
				// inverse function of isometric
				int _offX = (e.getX() - Game.OFFSET_MAP_X), _y = (2 * e.getY() - _offX) / 2,
						isoX = Math.round((_offX + _y) / Tile.SIZE) + 0 - 0 - 0,
						isoY = Math.round((_y + Tile.SIZE / 2) / Tile.SIZE) + 0 - 0;
				startTileX = isoX + Math.round((Math.abs(player.getX() - player.getPosUnitX())) / Tile.SIZE);
				startTileY = isoY + Math.round((Math.abs(player.getY() - player.getPosUnitY())) / Tile.SIZE);
			} else {
				startTileX = (int) Math.floor((Math.abs(player.getX() - player.getPosUnitX()) + e.getX()) / Tile.SIZE);
				startTileY = (int) Math.floor((Math.abs(player.getY() - player.getPosUnitY()) + e.getY()) / Tile.SIZE);
			}
			// make way
			if (tileIsPossible(startTileX, startTileY)) {
				MapWay mapPath = makePath(player.getX() / Tile.SIZE, player.getY() / Tile.SIZE, startTileX, startTileY);
				player.mapway = mapPath;
			}
		}
	};

	/**
	 * make way
	 */

	public MapWay makePath(int startX, int startY, int endX, int endY) {
		MapWayFind mfp = new MapWayFind();
		mfp.setcheckPoint(new IMapCheckPoint() {
			@Override
			public boolean check(int x, int y) {
				if (tileIsPossible(x, y)) {
					// two zombie on one tile is unpossible
					for (Bot bot : bots) {
						if (bot.getX() / Tile.SIZE == x && bot.getY() / Tile.SIZE == y) {
							return false;
						}
					}
					return true;
				}
				return false;
			}

		});

		if (mfp.findWay(startX, startY, endX, endY)) {
			return mfp.getWay();
		}

		return null;
	}

	public void setMap(int mapId) {
		if (mapId == 2) {
			map = new BossMap();
			player.mapway = null;
			
			player.setPosition(3 * Tile.SIZE,3 * Tile.SIZE);
			/**
			 * static position
			 */

			player.setPosUnit(3 * Tile.SIZE, 3 * Tile.SIZE);
			player.turnRight();
			timerTile = 0;
			bots.clear();
		} else {
			map = new LabirintMap();

			Bot bot;
			Random random = new Random();
			int a = 0, b = 0;
			int i = 0;
			// one bot is in one region (sum 4 bots)
			while (i < 4) {
				bot = new Bot();
				if (i == 2)
					a = 1;
				if (i == 1 || i == 3)
					b = 1;
				int x = random.nextInt(13) + 3 + a * 13;
				int y = random.nextInt(13) + 1 + a * 13;
				if (tileIsPossible(x, y)) {					
					bot.setPosUnit(x * Tile.SIZE,y * Tile.SIZE);
					bot.setPosition(x * Tile.SIZE,y * Tile.SIZE);				
					bots.add(bot);
					i++;
					b = 0;
				}

			}
			player.mapway = null;
			player.setPosition(Tile.SIZE,Tile.SIZE);
			// static position		
			player.setPosUnit(Tile.SIZE,Tile.SIZE);

		}

	}

	/**
	 * change map
	 */
	public void changeMap() {
		int tileX = player.getX() / Tile.SIZE, tileY = player.getY() / Tile.SIZE;
		int tileId = map.getTile(tileX, tileY);		
		Tile tile = Tile.getTileId(tileId);
		if (tile.getDoor() > 0) {
			setMap(tile.getDoor());
		}
	}

	/**
	 * working with event
	 */
	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			changeMap();
			canvas.cleaneRenders();
			int W = map.getWidth(), H = map.getHeight();
			int tileId;
			// String tileId;
			/**
			 * sum tiles on the screen
			 */

			int widthTile = 13, heightTile = 15;
			/**
			 * center
			 */
			int centreTileWigdth = widthTile / 2 - 1, centreTileHeight = heightTile / 2 - 1;
			/**
			 * what tiles we must paint
			 */

			int startTileX = (int) Math.floor(Math.abs(player.getX() - player.getPosUnitX()) / Tile.SIZE);
			int startTileY = (int) Math.floor(Math.abs(player.getY() - player.getPosUnitY()) / Tile.SIZE);
			int endTileX = widthTile + startTileX > W ? W : widthTile + startTileX;
			int endTileY = heightTile + startTileY > H ? H : heightTile + startTileY;

			/**
			 * map shift
			 */
			int offsetX = (player.getX() - player.getPosUnitX()) % Tile.SIZE;
			int offsetY = (player.getY() - player.getPosUnitY()) % Tile.SIZE;
			/**
			 * when must map move, when player
			 */

			boolean movePlayerX = (player.getX() / Tile.SIZE < centreTileWigdth
					|| W - player.getX() / Tile.SIZE < centreTileWigdth + 1),
					movePlayerY = (player.getY() / Tile.SIZE < centreTileHeight
							|| H - player.getY() / Tile.SIZE < centreTileHeight + 2);
			// fire tile on 2 floor
			if (timerTile == 1200) {
				stop("/data/win.jpg");
				return;
			}

			if ((timerTile % 120 == 0) && (timerTile > 4)) {

				if ((deadTileX == player.getX() / Tile.SIZE || deadTileX - 1 == player.getX() / Tile.SIZE)
						&& (deadTileY == player.getY() / Tile.SIZE || deadTileY - 1 == player.getY() / Tile.SIZE)) {
					stop("/data/game-over.png");
					return;
				} else {

					deadTileX = player.getX() / Tile.SIZE + 1;
					deadTileY = player.getY() / Tile.SIZE + 1;
				}
			}
			if (timerTile == 0) {

				deadTileX = 5;
				deadTileY = 5;

			}
			if (timerTile > 0) {
				if (timerTile % 8 == 0)
					paintPlayer("/data/fenix.png", 1569, 301, 40, 58, 20, -35, 270, 100, 150);
				else
					paintPlayer("/data/fenix.png", 1634, 301, 41, 58, 20, -35, 270, 100, 150);
			}
			//  paint map
			Tile tile;
			for (int y = startTileY; y < endTileY; y++) {
				for (int x = startTileX; x < endTileX; x++) {
					tileId = map.getTile(x, y);

					if (tileId == 14/* tileId.equals("Way") */) {

						if (((x == deadTileX) || (x == deadTileX - 1)) && ((y == deadTileY) || (y == deadTileY - 1))) {
							tileId = 15;
							// tileId="Fire1";

							if (timerTile % 8 < 4)
								tileId = 15;
							// tileId="Fire1";

							else
								tileId = 16;
							// tileId="Fire2";
							timerTile = timerTile + 1;

						}
					}
					tile = Tile.getTileId(tileId);
					tile.setPosition((x - startTileX) * Tile.SIZE - offsetX, (y - startTileY) * Tile.SIZE - offsetY);
					canvas.addRenders(tile);
				}
			}

			// add bot
			for (Bot bot : bots) {

				// if bot is on players tile - game over
				if (bot.getX() / Tile.SIZE == player.getX() / Tile.SIZE
						&& bot.getY() / Tile.SIZE == player.getY() / Tile.SIZE) {
					stop("/data/game-over.png");
					return;
				}

				// paint bot, if he is visible
				if (bot.getX() >= startTileX * Tile.SIZE && bot.getX() <= endTileX * Tile.SIZE
						&& bot.getY() >= startTileY * Tile.SIZE && bot.getY() <= endTileY * Tile.SIZE) {
					// new pursuit point
					if (bot.posPlayer(player.getX() / Tile.SIZE, player.getY() / Tile.SIZE)) {
						bot.mapway = makePath(bot.getX() / Tile.SIZE, bot.getY() / Tile.SIZE, bot.posX, bot.posY);

					} else
						// change position
						if (bot.getDirectX() != 0 || bot.getDirectY() != 0) {
						bot.setPosition(bot.getX() + bot.getDirectX() * bot.getSpeed(),bot.getY() + bot.getDirectY() * bot.getSpeed());
					}

					bot.setPosUnit((bot.getX() - startTileX * Tile.SIZE) - offsetX,(bot.getY() - startTileY * Tile.SIZE) - offsetY);
					canvas.addRenders(bot);
				}
			}

			if (flag == 0)
				canvas.addRenders(player);

			if ((player.getDirectX() != 0 || player.getDirectY() != 0) && possibleMove()) {

				player.setPosition(player.getX() + player.getDirectX() * player.getSpeed(),player.getY() + player.getDirectY() * player.getSpeed());
				if (movePlayerX) {					
					player.setPosUnit(player.getPosUnitX() + player.getDirectX() * player.getSpeed(),player.getPosUnitY());
				}
				if (movePlayerY) {	
					player.setPosUnit(player.getPosUnitX(), player.getPosUnitY() + player.getDirectY() * player.getSpeed());
				}
			}
			//  move, when is keyEvent
			if (flag != 0) {
				if (possibleMove()) {

					if (player.getDirectX()==0 && player.getDirectY()==1) {
						if (flag % 24 < 7) {
							paintPlayer("/data/hero/warr.png", 1304, 39, 80, 60, -10, -35, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						} else if ((flag % 24 < 12 && flag % 24 > 6) || (flag % 24 > 17)) {
							paintPlayer("/data/hero/warr.png", 1174, 40, 74, 59, -10, -35, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						}

						else if (flag % 24 < 18 && flag % 24 > 11) {
							paintPlayer("/data/hero/warr.png", 931, 40, 64, 56, 0, -25, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						}

					} else if (player.getDirectX()==0 && player.getDirectY()==-1) {

						if (flag % 24 < 7) {
							paintPlayer("/data/hero/warr.png", 1318, 411, 69, 79, 0, -30, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						} else if ((flag % 24 < 12 && flag % 24 > 6) || (flag % 24 > 17)) {
							paintPlayer("/data/hero/warr.png", 1193, 421, 66, 69, 10, -30, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						}

						else if (flag % 24 < 18 && flag % 24 > 11) {
							paintPlayer("/data/hero/warr.png", 1067, 421, 57, 66, 10, -30, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						}

					} else if (player.getDirectX()==1 && player.getDirectY()==0) {
						if (flag % 24 < 7) {
							paintPlayer("/data/hero/warr.png", 807, 684, 56, 60, 10, -35, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						} else if ((flag % 24 < 12 && flag % 24 > 6) || (flag % 24 > 17)) {
							paintPlayer("/data/hero/warr.png", 686, 682, 47, 62, 10, -35, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						}

						else if (flag % 24 < 18 && flag % 24 > 11) {
							paintPlayer("/data/hero/warr.png", 555, 681, 53, 60, 10, -35, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						}

					} else if (player.getDirectX()==-1 && player.getDirectY()==0) {

						if (flag % 24 < 7) {
							paintPlayer("/data/hero/warr.png", 939, 165, 48, 66, 25, -30, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						} else if ((flag % 24 < 12 && flag % 24 > 6) || (flag % 24 > 17)) {
							paintPlayer("/data/hero/warr.png", 1066, 164, 45, 64, 25, -30, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						}

						else if (flag % 24 < 18 && flag % 24 > 11) {
							paintPlayer("/data/hero/warr.png", 1448, 158, 55, 70, 25, -40, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						}

					} else if (player.getDirectX()==0 && player.getDirectY()==0) {

						if (flag1 % 6 < 3) {
							paintPlayer("/data/hero/warr.png", 42, 686, 48, 57, 20, -35, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						}

						else {
							paintPlayer("/data/hero/warr.png", 170, 686, 47, 57, 20, -35, player.getPosUnitX(),
									player.getPosUnitY(), 10000);
						}

						flag1 = flag1 + 1;
					}
				} else {

					paintPlayer("/data/hero/warr.png", 42, 686, 48, 57, 20, -35, player.getPosUnitX(),
							player.getPosUnitY(), 10000);

				}
			
			}

			canvas.repaint();

		}

	}

	/**
	 * paint
	 */
	private void paintPlayer(String name, int offsetX, int offsetY, int width, int hight, int standoffX, int standoffY,
			int X, int Y, int layerdoor) {
		canvas.addRenders(new Tile(name, X, Y, true, 0, layerdoor) {
			@Override
			public void render(Graphics g) {

				int isoX = X, isoY = Y;
				if (Game.USE_ISO) {
					isoX = (X - Y);
					isoY = (X + Y) / 2;

				}

				g.drawImage(getImage(name), isoX + Game.OFFSET_MAP_X + standoffX, isoY + standoffY,
						isoX + width + Game.OFFSET_MAP_X + standoffX, isoY + hight + standoffY, offsetX, offsetY,
						offsetX + width, offsetY + hight, null);
			}
		});
	}

	/**
	 *   is move possible or no
	 */
	public boolean possibleMove() {
		int left, right, up, down;
		boolean isPossible = true;

		// up and down
		left = (int) Math.ceil((player.getX()) / Tile.SIZE);
		right = (int) Math.floor((player.getX() + player.getWidth() - 1) / Tile.SIZE);
		up = (int) Math.ceil((player.getY() + player.getSpeed() * player.getDirectY()) / Tile.SIZE);
		down = (int) Math
				.floor((player.getY() + player.getHeight() + player.getSpeed() * player.getDirectY() - 1) / Tile.SIZE);
		if (player.getDirectY() == -1 && !(tileIsPossible(left, up) && tileIsPossible(right, up))) {
			isPossible = false;
		} else if (player.getDirectY() == 1 && !(tileIsPossible(left, down) && tileIsPossible(right, down))) {
			isPossible = false;
		}
		// left and right
		left = (int) Math.ceil((player.getX() + player.getSpeed() * player.getDirectY()) / Tile.SIZE);
		right = (int) Math
				.floor((player.getX() + player.getWidth() + player.getSpeed() * player.getDirectX() - 1) / Tile.SIZE);
		up = (int) Math.ceil((player.getY()) / Tile.SIZE);
		down = (int) Math.floor((player.getY() + player.getHeight() - 1) / Tile.SIZE);
		if (player.getDirectX() == -1 && !(tileIsPossible(left, up) && tileIsPossible(left, down))) {
			isPossible = false;
		} else if (player.getDirectX() == 1 && !(tileIsPossible(right, up) && tileIsPossible(right, down))) {
			isPossible = false;
		}

		return isPossible;
	}

	/**
	 * is move  possible on definite tile or no
	 */
	public boolean tileIsPossible(int x, int y) {
		Tile tile = Tile.getTileId(map.getTile(x, y));
		return (tile != null && tile.getIsPossible());
	}
}
