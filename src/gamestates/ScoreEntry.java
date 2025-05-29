package gamestates;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ScoreEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private int level;
    private int completedTime; // in seconds
    private LocalDateTime completionTime;

    public ScoreEntry(int level, int completedTime) {
        this.level = level;
        this.completedTime = completedTime;
        this.completionTime = LocalDateTime.now();
    }

    public int getLevel() {
        return level;
    }

    public int getCompletedTime() {
        return completedTime;
    }

    public LocalDateTime getCompletionTime() {
        return completionTime;
    }

    public String getFormattedCompletedTime() {
        int minutes = completedTime / 60;
        int seconds = completedTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public String getFormattedCompletionTime() {
        return completionTime.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
} 