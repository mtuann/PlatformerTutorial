package gamestates;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDateTime;


public class HighScoreManager {
    private List<HighScoreEntry> scores;
    private String currentSortColumn = "level";
    private boolean ascending = false;

    public HighScoreManager() {
        scores = new ArrayList<>();
        // Add some sample data
        addSampleData();
    }

    private void addSampleData() {
        // scores.add(new HighScoreEntry("Alexander", 85, 95));
        // scores.add(new HighScoreEntry("Benjamin", 92, 75));
        // scores.add(new HighScoreEntry("Charlotte", 78, 110));
        // scores.add(new HighScoreEntry("Daniel", 95, 82));
        // scores.add(new HighScoreEntry("Emily", 88, 98));
        // scores.add(new HighScoreEntry("Frederick", 73, 125));
        // scores.add(new HighScoreEntry("George", 91, 85));
        // scores.add(new HighScoreEntry("Henry", 82, 105));
        // scores.add(new HighScoreEntry("Isabella", 94, 78));
        // scores.add(new HighScoreEntry("James", 86, 92));
        // scores.add(new HighScoreEntry("Katherine", 89, 88));
        // scores.add(new HighScoreEntry("Liam", 96, 72));
        // scores.add(new HighScoreEntry("Michael", 83, 102));
        // scores.add(new HighScoreEntry("Oliver", 90, 86));
        // scores.add(new HighScoreEntry("William", 87, 94));
        scores.add(new HighScoreEntry("Alexander", 85, 95, LocalDateTime.of(2023, 11, 1, 14, 30, 0)));
        scores.add(new HighScoreEntry("Benjamin", 92, 75, LocalDateTime.of(2023, 11, 2, 15, 45, 0)));
        scores.add(new HighScoreEntry("Charlotte", 78, 110, LocalDateTime.of(2023, 11, 3, 9, 15, 0)));
        scores.add(new HighScoreEntry("Daniel", 95, 82, LocalDateTime.of(2023, 11, 4, 11, 20, 0)));
        scores.add(new HighScoreEntry("Emily", 88, 98, LocalDateTime.of(2023, 11, 5, 16, 10, 0)));
        scores.add(new HighScoreEntry("Frederick", 73, 125, LocalDateTime.of(2023, 11, 6, 13, 25, 0)));
        scores.add(new HighScoreEntry("George", 91, 85, LocalDateTime.of(2023, 11, 7, 10, 40, 0)));
        scores.add(new HighScoreEntry("Henry", 82, 105, LocalDateTime.of(2023, 11, 8, 17, 55, 0)));
        scores.add(new HighScoreEntry("Isabella", 94, 78, LocalDateTime.of(2023, 11, 9, 12, 05, 0)));
        scores.add(new HighScoreEntry("James", 86, 92, LocalDateTime.of(2023, 11, 10, 14, 15, 0)));
        scores.add(new HighScoreEntry("Katherine", 89, 88, LocalDateTime.of(2023, 11, 11, 15, 30, 0)));
        scores.add(new HighScoreEntry("Liam", 96, 72, LocalDateTime.of(2023, 11, 12, 9, 45, 0)));
        scores.add(new HighScoreEntry("Michael", 83, 102, LocalDateTime.of(2023, 11, 13, 11, 50, 0)));
        scores.add(new HighScoreEntry("Oliver", 90, 86, LocalDateTime.of(2023, 11, 14, 16, 20, 0)));
        scores.add(new HighScoreEntry("William", 87, 94, LocalDateTime.of(2023, 11, 15, 13, 35, 0)));
        scores.add(new HighScoreEntry("Sophie", 93, 81, LocalDateTime.of(2023, 11, 16, 10, 25, 0)));
        scores.add(new HighScoreEntry("Thomas", 84, 97, LocalDateTime.of(2023, 11, 17, 17, 40, 0)));
        scores.add(new HighScoreEntry("Victoria", 97, 71, LocalDateTime.of(2023, 11, 18, 12, 55, 0)));
        scores.add(new HighScoreEntry("Zachary", 81, 108, LocalDateTime.of(2023, 11, 19, 14, 10, 0)));
        scores.add(new HighScoreEntry("Amelia", 88, 89, LocalDateTime.of(2023, 11, 20, 15, 25, 0)));
    }

    public void addScore(String username, int level, int completedTime) {
        scores.add(new HighScoreEntry(username, level, completedTime));
        sortScores();
    }

    public List<HighScoreEntry> getScores() {
        return scores;
    }

    public void sortBy(String column) {
        if (currentSortColumn.equals(column)) {
            ascending = !ascending;
        } else {
            currentSortColumn = column;
            ascending = true;
        }
        sortScores();
    }

    private void sortScores() {
        Comparator<HighScoreEntry> comparator = switch (currentSortColumn) {
            case "username" -> Comparator.comparing(HighScoreEntry::getUsername);
            case "level" -> Comparator.comparingInt(HighScoreEntry::getLevel);
            case "completedTime" -> Comparator.comparingInt(HighScoreEntry::getCompletedTime);
            case "updatedTime" -> Comparator.comparing(HighScoreEntry::getUpdatedTime);
            default -> Comparator.comparingInt(HighScoreEntry::getLevel);
        };

        if (!ascending) {
            comparator = comparator.reversed();
        }

        scores.sort(comparator);
    }

    public String getCurrentSortColumn() {
        return currentSortColumn;
    }

    public boolean isAscending() {
        return ascending;
    }
} 