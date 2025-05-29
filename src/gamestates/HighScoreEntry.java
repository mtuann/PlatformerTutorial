package gamestates;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HighScoreEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private List<ScoreEntry> scores;
    private LocalDateTime lastUpdated;

    // Default constructor for deserialization
    public HighScoreEntry() {
        this.scores = new ArrayList<>();
        this.lastUpdated = LocalDateTime.now();
    }

    public HighScoreEntry(String username) {
        this.username = username;
        this.scores = new ArrayList<>();
        this.lastUpdated = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public void addScore(int level, int completedTime) {
        if (scores == null) {
            scores = new ArrayList<>();
        }
        scores.add(new ScoreEntry(level, completedTime));
        lastUpdated = LocalDateTime.now();
        System.out.println("New score added for " + username + ":");
        System.out.println("Level: " + level);
        System.out.println("Completion Time: " + new ScoreEntry(level, completedTime).getFormattedCompletedTime());
        System.out.println("Total scores for this user: " + scores.size());
        System.out.println("Highest level achieved: " + getHighestLevel());
        System.out.println("Last updated: " + getFormattedLastUpdated());
        System.out.println("------------------------");
    }

    public List<ScoreEntry> getScores() {
        if (scores == null) {
            scores = new ArrayList<>();
            System.out.println("Initialized scores list for user: " + username);
        }
        return scores;
    }

    public int getHighestLevel() {
        if (scores == null || scores.isEmpty()) {
            return 0;
        }
        return scores.stream()
                .mapToInt(ScoreEntry::getLevel)
                .max()
                .orElse(0);
    }

    public int getBestTimeForLevel(int level) {
        if (scores == null || scores.isEmpty()) {
            return 0;
        }
        return scores.stream()
                .filter(score -> score.getLevel() == level)
                .mapToInt(ScoreEntry::getCompletedTime)
                .min()
                .orElse(0);
    }

    public ScoreEntry getLatestScore() {
        if (scores == null || scores.isEmpty()) {
            // Return a default score entry with level 0 and current time
            return new ScoreEntry(0, 0);
        }
        return scores.get(scores.size() - 1);
    }

    public LocalDateTime getLastUpdated() {
        if (lastUpdated == null) {
            lastUpdated = LocalDateTime.now();
        }
        return lastUpdated;
    }

    public String getFormattedLastUpdated() {
        if (lastUpdated == null) {
            lastUpdated = LocalDateTime.now();
        }
        return lastUpdated.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
} 