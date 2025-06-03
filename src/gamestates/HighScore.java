package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.List;

import main.Game;
import ui.UrmButton;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

public class HighScore extends State implements Statemethods, MouseWheelListener {
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private HighScoreManager scoreManager;
    private Rectangle[] sortButtons;
    private boolean[] buttonHover;
    private boolean[] buttonPressed;
    private UrmButton homeButton;
    private static final int COLUMN_WIDTH = 250;
    private static final int ROW_HEIGHT = 40;
    private static final int START_X = 350;
    private static final int START_Y = 180;
    private static final int RANK_COLUMN_WIDTH = 60;
    private static final int RANK_USERNAME_SPACING = 40;
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font DATA_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Color BUTTON_BG = new Color(0, 0, 0, 180);
    private static final Color BUTTON_HOVER = new Color(50, 50, 50, 220);
    private static final Color BUTTON_PRESSED = new Color(100, 100, 100, 240);
    private static final Color BUTTON_BORDER = new Color(255, 255, 255, 150);
    private static final Color HEADER_TEXT_COLOR = new Color(255, 215, 0);
    private static final Color ROW_EVEN_COLOR = new Color(255, 255, 255, 30);
    private static final Color ROW_ODD_COLOR = new Color(255, 255, 255, 15);
    private static final Color TEXT_COLOR = new Color(255, 0, 0);
    private static final Color RANK_COLOR = new Color(255, 215, 0);
    private static final int HEADER_SPACING = 20;
    
    // Scrollbar properties
    private static final int SCROLLBAR_WIDTH = 20;
    private static final int SCROLLBAR_MIN_HEIGHT = 50;
    private static final Color SCROLLBAR_BG = new Color(0, 0, 0, 100);
    private static final Color SCROLLBAR_THUMB = new Color(255, 255, 255, 150);
    private static final Color SCROLLBAR_THUMB_HOVER = new Color(255, 255, 255, 200);
    private Rectangle scrollbar;
    private Rectangle scrollbarThumb;
    private boolean isDraggingScrollbar;
    private int scrollOffset;
    private int maxScrollOffset;
    private boolean isHoveringScrollbar;
    private int visibleRows;
    private int tableHeight;

    public HighScore(Game game) {
        super(game);
        loadBackground();
        scoreManager = new HighScoreManager();
        createSortButtons();
        initScrollbar();
        createHomeButton();
    }

    public void initMouseWheelListener() {
        game.getGamePanel().addMouseWheelListener(this);
    }

    private void initScrollbar() {
        visibleRows = (Game.GAME_HEIGHT - START_Y - 50) / ROW_HEIGHT;
        tableHeight = scoreManager.getScores().size() * ROW_HEIGHT;
        maxScrollOffset = Math.max(0, tableHeight - visibleRows * ROW_HEIGHT);
        
        int tableWidth = RANK_COLUMN_WIDTH + RANK_USERNAME_SPACING + COLUMN_WIDTH * 4;
        int scrollbarX = START_X + tableWidth + 10;
        int scrollbarY = START_Y;
        int scrollbarHeight = visibleRows * ROW_HEIGHT;
        
        scrollbar = new Rectangle(scrollbarX, scrollbarY, SCROLLBAR_WIDTH, scrollbarHeight);
        updateScrollbarThumb();
    }

    private void updateScrollbarThumb() {
        if (tableHeight <= visibleRows * ROW_HEIGHT) {
            scrollbarThumb = null;
            return;
        }

        int thumbHeight = Math.max(SCROLLBAR_MIN_HEIGHT, 
            (int)((float)visibleRows * ROW_HEIGHT / tableHeight * scrollbar.height));
        int thumbY = scrollbar.y + (int)((float)scrollOffset / maxScrollOffset * 
            (scrollbar.height - thumbHeight));
        
        scrollbarThumb = new Rectangle(scrollbar.x, thumbY, SCROLLBAR_WIDTH, thumbHeight);
    }

    private void createSortButtons() {
        sortButtons = new Rectangle[5];
        buttonHover = new boolean[5];
        buttonPressed = new boolean[5];
        int buttonY = START_Y - 50;  // Adjusted to be closer to the table
        int buttonHeight = 40;
        
        // Add rank button (non-sortable)
        sortButtons[0] = new Rectangle(START_X, buttonY, RANK_COLUMN_WIDTH, buttonHeight);
        
        // Add other buttons with adjusted positions
        for (int i = 1; i < sortButtons.length; i++) {
            int x = START_X + RANK_COLUMN_WIDTH + RANK_USERNAME_SPACING + (i - 1) * COLUMN_WIDTH;
            sortButtons[i] = new Rectangle(x, buttonY, COLUMN_WIDTH, buttonHeight);
        }
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    private void createHomeButton() {
        int buttonX = Game.GAME_WIDTH/2 + 300;  // Position to the right of the title
        int buttonY = 10;  // Align with the title
        homeButton = new UrmButton(buttonX, buttonY, URM_SIZE, URM_SIZE, 2);  // 2 is the home icon index
    }

    @Override
    public void update() {
        // Update scrollbar if needed
        if (tableHeight > visibleRows * ROW_HEIGHT) {
            updateScrollbarThumb();
        }
        homeButton.update();
    }

    @Override
    public void draw(Graphics g) {
        // Draw background
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        // Draw title with shadow
        g.setColor(new Color(0, 0, 0, 150));
        g.setFont(new Font("Arial", Font.BOLD, 44));
        String title = "HIGH SCORES";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, Game.GAME_WIDTH/2 - titleWidth/2 + 2, 82);
        
        g.setColor(new Color(255, 215, 0));
        g.drawString(title, Game.GAME_WIDTH/2 - titleWidth/2, 80);

        // Draw home button
        homeButton.draw(g);

        // Draw column headers and sort buttons
        g.setFont(HEADER_FONT);
        String[] headers = {"RANK", "USERNAME", "LEVEL", "TIME", "UPDATED"};
        for (int i = 0; i < headers.length; i++) {
            Rectangle button = sortButtons[i];
            
            // Draw button background with different states
            if (i > 0) {  // Skip hover/press effects for rank column
                if (buttonPressed[i]) {
                    g.setColor(BUTTON_PRESSED);
                } else if (buttonHover[i]) {
                    g.setColor(BUTTON_HOVER);
                } else {
                    g.setColor(BUTTON_BG);
                }
                g.fillRect(button.x, button.y, button.width, button.height);
                
                // Draw button border
                g.setColor(BUTTON_BORDER);
                g.drawRect(button.x, button.y, button.width, button.height);
            }
            
            // Draw header text
            g.setColor(HEADER_TEXT_COLOR);
            int textX = button.x + (button.width - g.getFontMetrics().stringWidth(headers[i])) / 2;
            int textY = button.y + button.height - 10;
            g.drawString(headers[i], textX, textY);
            
            // Draw sort indicator if this column is being sorted (skip for rank column)
            if (i > 0) {
                String column = switch (i) {
                    case 1 -> "username";
                    case 2 -> "level";
                    case 3 -> "completedTime";
                    case 4 -> "updatedTime";
                    default -> "";
                };
                
                if (column.equals(scoreManager.getCurrentSortColumn())) {
                    String arrow = scoreManager.isAscending() ? "↑" : "↓";
                    g.drawString(arrow, button.x + button.width - 30, textY);
                }
            }
        }

        // Draw scores with clipping
        g.setFont(DATA_FONT);
        List<HighScoreEntry> scores = scoreManager.getScores();
        
        // Set clipping area for the table
        int tableWidth = RANK_COLUMN_WIDTH + RANK_USERNAME_SPACING + COLUMN_WIDTH * 4;
        g.setClip(START_X - 10, START_Y, tableWidth + 20, visibleRows * ROW_HEIGHT);
        
        for (int i = 0; i < scores.size(); i++) {
            HighScoreEntry entry = scores.get(i);
            ScoreEntry latestScore = entry.getLatestScore();
            int y = START_Y + 35 + i * ROW_HEIGHT - scrollOffset;
            
            // Draw row background for better readability
            g.setColor(i % 2 == 0 ? ROW_EVEN_COLOR : ROW_ODD_COLOR);
            g.fillRect(START_X - 10, y - ROW_HEIGHT + 5, tableWidth + 20, ROW_HEIGHT);
            
            // Rank (center-aligned)
            g.setColor(RANK_COLOR);
            String rankStr = String.valueOf(i + 1);
            int rankX = START_X + (RANK_COLUMN_WIDTH - g.getFontMetrics().stringWidth(rankStr)) / 2;
            g.drawString(rankStr, rankX, y);
            
            // Username (left-aligned)
            g.setColor(TEXT_COLOR);
            g.drawString(entry.getUsername(), START_X + RANK_COLUMN_WIDTH + RANK_USERNAME_SPACING, y);
            
            // Level (center-aligned)
            String levelStr = latestScore != null ? String.valueOf(latestScore.getLevel()) : "0";
            int levelX = START_X + RANK_COLUMN_WIDTH + RANK_USERNAME_SPACING + COLUMN_WIDTH + 
                (COLUMN_WIDTH - g.getFontMetrics().stringWidth(levelStr)) / 2;
            g.drawString(levelStr, levelX, y);
            
            // Time (center-aligned)
            String timeStr = latestScore != null ? latestScore.getFormattedCompletedTime() : "00:00";
            int timeX = START_X + RANK_COLUMN_WIDTH + RANK_USERNAME_SPACING + COLUMN_WIDTH * 2 + 
                (COLUMN_WIDTH - g.getFontMetrics().stringWidth(timeStr)) / 2;
            g.drawString(timeStr, timeX, y);
            
            // Updated time (left-aligned)
            g.drawString(entry.getFormattedLastUpdated(), 
                START_X + RANK_COLUMN_WIDTH + RANK_USERNAME_SPACING + COLUMN_WIDTH * 3, y);
        }
        
        // Reset clipping
        g.setClip(null);

        // Draw scrollbar if needed
        if (tableHeight > visibleRows * ROW_HEIGHT) {
            // Draw scrollbar background
            g.setColor(SCROLLBAR_BG);
            g.fillRect(scrollbar.x, scrollbar.y, scrollbar.width, scrollbar.height);
            
            // Draw scrollbar thumb
            if (scrollbarThumb != null) {
                g.setColor(isHoveringScrollbar ? SCROLLBAR_THUMB_HOVER : SCROLLBAR_THUMB);
                g.fillRect(scrollbarThumb.x, scrollbarThumb.y, 
                    scrollbarThumb.width, scrollbarThumb.height);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            setGamestate(Gamestate.MENU);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (homeButton.getBounds().contains(e.getX(), e.getY())) {
            homeButton.setMousePressed(true);
            return;
        }

        if (scrollbarThumb != null && scrollbarThumb.contains(e.getX(), e.getY())) {
            isDraggingScrollbar = true;
            return;
        }

        // Check if clicking on scrollbar track
        if (scrollbar != null && scrollbar.contains(e.getX(), e.getY())) {
            int clickY = e.getY();
            if (scrollbarThumb != null) {
                if (clickY < scrollbarThumb.y) {
                    // Click above thumb - scroll up
                    scrollOffset = Math.max(0, scrollOffset - visibleRows * ROW_HEIGHT);
                } else {
                    // Click below thumb - scroll down
                    scrollOffset = Math.min(maxScrollOffset, scrollOffset + visibleRows * ROW_HEIGHT);
                }
                updateScrollbarThumb();
            }
            return;
        }

        for (int i = 1; i < sortButtons.length; i++) {  // Start from 1 to skip rank column
            Rectangle button = sortButtons[i];
            if (button.contains(e.getX(), e.getY())) {
                buttonPressed[i] = true;
                String column = switch (i) {
                    case 1 -> "username";
                    case 2 -> "level";
                    case 3 -> "completedTime";
                    case 4 -> "updatedTime";
                    default -> "";
                };
                scoreManager.sortBy(column);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (homeButton.isMousePressed()) {
            if (homeButton.getBounds().contains(e.getX(), e.getY())) {
                setGamestate(Gamestate.MENU);
            }
        }
        homeButton.resetBools();
        isDraggingScrollbar = false;
        for (int i = 0; i < buttonPressed.length; i++) {
            buttonPressed[i] = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        homeButton.setMouseOver(false);
        if (homeButton.getBounds().contains(e.getX(), e.getY()))
            homeButton.setMouseOver(true);

        // Update button hover states
        for (int i = 0; i < sortButtons.length; i++) {
            buttonHover[i] = sortButtons[i].contains(e.getX(), e.getY());
        }

        // Update scrollbar hover state
        if (scrollbarThumb != null) {
            isHoveringScrollbar = scrollbarThumb.contains(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isDraggingScrollbar && scrollbarThumb != null) {
            int newY = e.getY() - scrollbarThumb.height / 2;
            newY = Math.max(scrollbar.y, Math.min(scrollbar.y + scrollbar.height - scrollbarThumb.height, newY));
            
            float scrollRatio = (float)(newY - scrollbar.y) / (scrollbar.height - scrollbarThumb.height);
            scrollOffset = (int)(scrollRatio * maxScrollOffset);
            
            updateScrollbarThumb();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (tableHeight <= visibleRows * ROW_HEIGHT) {
            return;
        }

        int scrollAmount = e.getWheelRotation() * ROW_HEIGHT;
        scrollOffset = Math.max(0, Math.min(maxScrollOffset, scrollOffset + scrollAmount));
        updateScrollbarThumb();
    }

    public HighScoreManager getScoreManager() {
        return scoreManager;
    }
} 