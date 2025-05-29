package gamestates;

import java.time.LocalDateTime;

public class HighScoreEntry {
    private String username;
    private int level;
    private int completedTime; // in seconds
    private LocalDateTime updatedTime;

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

    public int getCompletedTime() {
        return completedTime;
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