package gamestates;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HighScoreEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private int level;
    private int completedTime; // in seconds
    private LocalDateTime updatedTime;

    public HighScoreEntry(String username) {
        this.username = username;
        this.level = 0;
        this.completedTime = 0;
        this.updatedTime = LocalDateTime.now();
    }

    public HighScoreEntry(String username, int level, int completedTime) {
        this.username = username;
        this.level = level;
        this.completedTime = completedTime;
        this.updatedTime = LocalDateTime.now();
    }
    
    public HighScoreEntry(String username, int level, int completedTime, LocalDateTime updatedTime) {
        this.username = username;
        this.level = level;
        this.completedTime = completedTime;
        this.updatedTime = updatedTime;
    }

    public String getUsername() {
        return username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.updatedTime = LocalDateTime.now();
    }

    public int getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(int completedTime) {
        this.completedTime = completedTime;
        this.updatedTime = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public String getFormattedCompletedTime() {
        int minutes = completedTime / 60;
        int seconds = completedTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public String getFormattedUpdatedTime() {
        return updatedTime.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
} 