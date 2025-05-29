package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import gamestates.Gamestate;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private Game game;

	public GamePanel(Game game) {
		mouseInputs = new MouseInputs(this);
		this.game = game;
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
	}

	public void updateGame() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}

	public void render(Graphics g) {
		switch (Gamestate.state) {
		case MENU -> game.getMenu().draw(g);
		case PLAYING -> game.getPlaying().draw(g);
		case OPTIONS -> game.getGameOptions().draw(g);
		case CREDITS -> game.getCredits().draw(g);
		case HIGHSCORE -> game.getHighScore().draw(g);
		case USERNAME_REGISTRATION -> game.getUsernameRegistration().draw(g);
		}
	}

	public Game getGame() {
		return game;
	}

}