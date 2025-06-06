package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.Gamestate;
import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private GamePanel gamePanel;

	public MouseInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (Gamestate.state) {
		case PLAYING -> gamePanel.getGame().getPlaying().mouseDragged(e);
		case OPTIONS -> gamePanel.getGame().getGameOptions().mouseDragged(e);
		case USERNAME_REGISTRATION -> gamePanel.getGame().getUsernameRegistration().mouseDragged(e);
		}
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void mouseMoved(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU -> gamePanel.getGame().getMenu().mouseMoved(e);
		case PLAYING -> gamePanel.getGame().getPlaying().mouseMoved(e);
		case OPTIONS -> gamePanel.getGame().getGameOptions().mouseMoved(e);
		case HIGHSCORE -> gamePanel.getGame().getHighScore().mouseMoved(e);
		case USERNAME_REGISTRATION -> gamePanel.getGame().getUsernameRegistration().mouseMoved(e);
		}
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (Gamestate.state) {
		case PLAYING -> gamePanel.getGame().getPlaying().mouseClicked(e);
		case USERNAME_REGISTRATION -> gamePanel.getGame().getUsernameRegistration().mouseClicked(e);
		}
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void mousePressed(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU -> gamePanel.getGame().getMenu().mousePressed(e);
		case PLAYING -> gamePanel.getGame().getPlaying().mousePressed(e);
		case OPTIONS -> gamePanel.getGame().getGameOptions().mousePressed(e);
		case HIGHSCORE -> gamePanel.getGame().getHighScore().mousePressed(e);
		case USERNAME_REGISTRATION -> gamePanel.getGame().getUsernameRegistration().mousePressed(e);
		}
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void mouseReleased(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU -> gamePanel.getGame().getMenu().mouseReleased(e);
		case PLAYING -> gamePanel.getGame().getPlaying().mouseReleased(e);
		case OPTIONS -> gamePanel.getGame().getGameOptions().mouseReleased(e);
		case HIGHSCORE -> gamePanel.getGame().getHighScore().mouseReleased(e);
		case USERNAME_REGISTRATION -> gamePanel.getGame().getUsernameRegistration().mouseReleased(e);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Not In use
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Not In use
	}

}