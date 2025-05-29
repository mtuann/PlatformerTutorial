package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class UsernameRegistration extends State implements Statemethods {
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private MenuButton submitButton;
    private String username = "";
    private boolean isTyping = false;
    private Rectangle inputBox;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 40);
    private static final Font INPUT_FONT = new Font("Arial", Font.PLAIN, 24);
    private static final Color INPUT_BG = new Color(0, 0, 0, 200);
    private static final Color INPUT_BORDER = new Color(255, 255, 255, 150);
    private static final Color TEXT_COLOR = new Color(255, 255, 255, 255);
    private static final int MAX_USERNAME_LENGTH = 15;
    private String errorMessage = "";
    private static final Font ERROR_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Color ERROR_COLOR = new Color(255, 100, 100);
    private boolean showConfirmation = false;
    private Rectangle confirmButton;
    private Rectangle cancelButton;
    private boolean confirmHover = false;
    private boolean cancelHover = false;
    private boolean confirmPressed = false;
    private boolean cancelPressed = false;
    private static final Font CONFIRM_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Color CONFIRM_BG = new Color(0, 0, 0, 200);
    private static final Color CONFIRM_BORDER = new Color(255, 255, 255, 150);
    private static final Color BUTTON_BG = new Color(0, 0, 0, 150);
    private static final Color BUTTON_HOVER = new Color(50, 50, 50, 200);
    private static final Color BUTTON_PRESSED = new Color(100, 100, 100, 200);
    private static final Color BUTTON_BORDER = new Color(255, 255, 255, 100);

    public UsernameRegistration(Game game) {
        super(game);
        loadBackground();
        createButtons();
        createInputBox();
        createConfirmationButtons();
    }

    private void createInputBox() {
        int boxWidth = 400;
        int boxHeight = 50;
        int boxX = Game.GAME_WIDTH / 2 - boxWidth / 2;
        int boxY = Game.GAME_HEIGHT / 2;
        inputBox = new Rectangle(boxX, boxY, boxWidth, boxHeight);
    }

    private void createButtons()  {
        submitButton = new MenuButton(Game.GAME_WIDTH / 2, (int) (Game.GAME_HEIGHT * 0.6), 0, Gamestate.MENU);
    }

    private void createConfirmationButtons() {
        int buttonWidth = 120;
        int buttonHeight = 40;
        int buttonY = Game.GAME_HEIGHT / 2 + 100;
        confirmButton = new Rectangle(Game.GAME_WIDTH / 2 - buttonWidth - 20, buttonY, buttonWidth, buttonHeight);
        cancelButton = new Rectangle(Game.GAME_WIDTH / 2 + 20, buttonY, buttonWidth, buttonHeight);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    @Override
    public void update() {
        if (!showConfirmation) {
            submitButton.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        // Draw background
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        // Draw semi-transparent overlay
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        if (!showConfirmation) {
            // Draw title
            g.setColor(TEXT_COLOR);
            g.setFont(TITLE_FONT);
            String title = "Enter Username";
            int titleWidth = g.getFontMetrics().stringWidth(title);
            g.drawString(title, Game.GAME_WIDTH/2 - titleWidth/2, 150);

            // Draw input box
            g.setColor(INPUT_BG);
            g.fillRect(inputBox.x, inputBox.y, inputBox.width, inputBox.height);
            g.setColor(INPUT_BORDER);
            g.drawRect(inputBox.x, inputBox.y, inputBox.width, inputBox.height);

            // Draw username text
            g.setColor(TEXT_COLOR);
            g.setFont(INPUT_FONT);
            String displayText = username;
            if (isTyping && System.currentTimeMillis() % 1000 < 500) {
                displayText += "|";
            }
            int textX = inputBox.x + 10;
            int textY = inputBox.y + inputBox.height - 10;
            g.drawString(displayText, textX, textY);

            // Draw error message if any
            if (!errorMessage.isEmpty()) {
                g.setColor(ERROR_COLOR);
                g.setFont(ERROR_FONT);
                int errorX = Game.GAME_WIDTH/2 - g.getFontMetrics().stringWidth(errorMessage)/2;
                g.drawString(errorMessage, errorX, inputBox.y + inputBox.height + 30);
            }

            // Draw submit button
            submitButton.draw(g);
        } else {
            // Draw confirmation dialog
            int dialogWidth = 400;
            int dialogHeight = 200;
            int dialogX = Game.GAME_WIDTH / 2 - dialogWidth / 2;
            int dialogY = Game.GAME_HEIGHT / 2 - dialogHeight / 2;

            // Draw dialog background
            g.setColor(CONFIRM_BG);
            g.fillRect(dialogX, dialogY, dialogWidth, dialogHeight);
            g.setColor(CONFIRM_BORDER);
            g.drawRect(dialogX, dialogY, dialogWidth, dialogHeight);

            // Draw confirmation message
            g.setColor(TEXT_COLOR);
            g.setFont(CONFIRM_FONT);
            String message = "Welcome back, " + username + "!";
            int messageWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, Game.GAME_WIDTH/2 - messageWidth/2, dialogY + 50);

            g.setFont(ERROR_FONT);
            String subMessage = "Continue with this account?";
            int subMessageWidth = g.getFontMetrics().stringWidth(subMessage);
            g.drawString(subMessage, Game.GAME_WIDTH/2 - subMessageWidth/2, dialogY + 80);

            // Draw confirmation buttons
            g.setFont(ERROR_FONT);
            
            // Draw Continue button
            g.setColor(confirmPressed ? BUTTON_PRESSED : (confirmHover ? BUTTON_HOVER : BUTTON_BG));
            g.fillRect(confirmButton.x, confirmButton.y, confirmButton.width, confirmButton.height);
            g.setColor(BUTTON_BORDER);
            g.drawRect(confirmButton.x, confirmButton.y, confirmButton.width, confirmButton.height);
            g.setColor(TEXT_COLOR);
            String continueText = "Continue";
            int continueX = confirmButton.x + (confirmButton.width - g.getFontMetrics().stringWidth(continueText)) / 2;
            int continueY = confirmButton.y + (confirmButton.height + g.getFontMetrics().getAscent()) / 2;
            g.drawString(continueText, continueX, continueY);

            // Draw Cancel button
            g.setColor(cancelPressed ? BUTTON_PRESSED : (cancelHover ? BUTTON_HOVER : BUTTON_BG));
            g.fillRect(cancelButton.x, cancelButton.y, cancelButton.width, cancelButton.height);
            g.setColor(BUTTON_BORDER);
            g.drawRect(cancelButton.x, cancelButton.y, cancelButton.width, cancelButton.height);
            g.setColor(TEXT_COLOR);
            String cancelText = "Cancel";
            int cancelX = cancelButton.x + (cancelButton.width - g.getFontMetrics().stringWidth(cancelText)) / 2;
            int cancelY = cancelButton.y + (cancelButton.height + g.getFontMetrics().getAscent()) / 2;
            g.drawString(cancelText, cancelX, cancelY);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (showConfirmation) {
            if (confirmButton.contains(e.getX(), e.getY())) {
                confirmPressed = true;
            } else if (cancelButton.contains(e.getX(), e.getY())) {
                cancelPressed = true;
            }
            return;
        }

        if (inputBox.contains(e.getX(), e.getY())) {
            isTyping = true;
        } else {
            isTyping = false;
        }

        if (isIn(e, submitButton)) {
            submitButton.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (showConfirmation) {
            if (confirmButton.contains(e.getX(), e.getY())) {
                if (confirmPressed) {
                    continueWithExistingUser();
                }
            } else if (cancelButton.contains(e.getX(), e.getY())) {
                if (cancelPressed) {
                    showConfirmation = false;
                    errorMessage = "";
                }
            }
            confirmPressed = false;
            cancelPressed = false;
            return;
        }

        if (isIn(e, submitButton)) {
            if (submitButton.isMousePressed()) {
                if (!username.isEmpty()) {
                    saveUserData();
                }
            }
        }
        submitButton.resetBools();
    }

    private void saveUserData() {
        if (!username.isEmpty()) {
            // Check if username already exists
            if (game.getHighScore().getScoreManager().getUser(username) != null) {
                showConfirmation = true;
                return;
            }
            
            game.getHighScore().getScoreManager().addNewUser(username);
            game.setCurrentUser(username);
            setGamestate(Gamestate.MENU);
        }
    }

    private void continueWithExistingUser() {
        game.setCurrentUser(username);
        setGamestate(Gamestate.MENU);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isTyping) {
            // Clear error message when user starts typing
            errorMessage = "";
            
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (!username.isEmpty()) {
                    username = username.substring(0, username.length() - 1);
                }
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!username.isEmpty()) {
                    saveUserData();
                }
            } else {
                char c = e.getKeyChar();
                if (Character.isLetterOrDigit(c) && username.length() < MAX_USERNAME_LENGTH) {
                    username += c;
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (showConfirmation) {
            confirmHover = confirmButton.contains(e.getX(), e.getY());
            cancelHover = cancelButton.contains(e.getX(), e.getY());
            return;
        }

        submitButton.setMouseOver(false);
        if (isIn(e, submitButton))
            submitButton.setMouseOver(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Not needed for registration
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for registration
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Not needed for registration
    }
} 