package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import ui.ModeButton;
import utilz.LoadSave;

public class Mode extends State implements Statemethods {
	
	//Buttons 
	private ModeButton[] buttons = new ModeButton[3];
//	private MenuButton[] buttons = new MenuButton[5];
	private int modeX, modeY, modeWidth, modeHeight;
	
	

	private BufferedImage backgroundImg, backgroundImgPink;

	private ArrayList<ShowEntity> entitiesList;

	public Mode(Game game) {
		super(game);
		loadButtons();
		loadBackground();
		backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
		
		loadEntities();
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MODE_BACKGROUND);
		modeWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
		modeHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		modeX = Game.GAME_WIDTH / 2 - modeWidth / 2;
		modeY = (int) (25 * Game.SCALE);
		
	}

	private void loadButtons() {
		buttons[0] = new ModeButton(Game.GAME_WIDTH / 2, (int) (110 * Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new ModeButton(Game.GAME_WIDTH / 2, (int) (170 * Game.SCALE), 1, Gamestate.PLAYING);
		buttons[2] = new ModeButton(Game.GAME_WIDTH / 2, (int) (230 * Game.SCALE), 2, Gamestate.PLAYING);
		
	}

	private void loadEntities() {
		entitiesList = new ArrayList<>();
		entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS), 5, 64, 32), (int) (Game.GAME_WIDTH * 0.1), (int) (Game.GAME_HEIGHT * 0.8)));
		entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.ROBO_SPRITE), 9, 72, 32), (int) (Game.GAME_WIDTH * 0.3), (int) (Game.GAME_HEIGHT * 0.8)));
		entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.HILI_ATLAS), 8, 34, 30), (int) (Game.GAME_WIDTH * 0.6), (int) (Game.GAME_HEIGHT * 0.8)));
		entitiesList.add(new ShowEntity(getIdleAni(LoadSave.GetSpriteAtlas(LoadSave.GOBLIN_ATLAS), 8, 34, 30), (int) (Game.GAME_WIDTH * 0.8), (int) (Game.GAME_HEIGHT * 0.8)));
	}

	private BufferedImage[] getIdleAni(BufferedImage atlas, int spritesAmount, int width, int height) {
		BufferedImage[] arr = new BufferedImage[spritesAmount];
		for (int i = 0; i < spritesAmount; i++)
			arr[i] = atlas.getSubimage(width * i, 0, width, height);
		return arr;
	}

	@Override
	public void update() {
		
		for (ShowEntity se : entitiesList)
			se.update();
		
		for (ModeButton mb : buttons)
			mb.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(backgroundImg, modeX, modeY, modeWidth, modeHeight, null);
		
		for (ShowEntity se : entitiesList)
			se.draw(g);
		
		for (ModeButton mb : buttons)
			mb.draw(g);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			setGamestate(Gamestate.MENU);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (ModeButton mb : buttons)
			mb.setMouseOver(false);

		for (ModeButton mb : buttons)
			if (isInMode(e, mb)) {
				mb.setMouseOver(true);
				break;
			}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (ModeButton mb : buttons) {
			if (isInMode(e, mb)) {
				if(mb == buttons[0]) {
					System.out.println("easy");
					game.getPlaying().selectLevel(0);

				}
				else if(mb == buttons[1]) {
					System.out.println("MEDIUM");
						game.getPlaying().selectLevel(2);

				}
				else if(mb == buttons[2]) {
					System.out.println("HARD");
					game.getPlaying().selectLevel(4);
					}
				mb.setMousePressed(true);
			}

	}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (ModeButton mb : buttons) {
			if (isInMode(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				if (mb.getState() == Gamestate.PLAYING)
					game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
				break;
			}
		}
		resetButtons();
	}
	
	private void resetButtons() {
		for (ModeButton mb : buttons)
			mb.resetBools();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			setGamestate(Gamestate.MENU);
		}
	}

	private class ShowEntity {
		private BufferedImage[] idleAnimation;
		private int x, y, aniIndex, aniTick;

		public ShowEntity(BufferedImage[] idleAnimation, int x, int y) {
			this.idleAnimation = idleAnimation;
			this.x = x;
			this.y = y;
		}

		public void draw(Graphics g) {
			
			g.drawImage(idleAnimation[aniIndex], x, y, (int) (idleAnimation[aniIndex].getWidth() * 4), (int) (idleAnimation[aniIndex].getHeight() * 4), null);
		}

		public void update() {
			
			
			aniTick++;
			if (aniTick >= 25) {
				aniTick = 0;
				aniIndex++;
				if (aniIndex >= idleAnimation.length)
					aniIndex = 0;
			}

		}
	}

}
