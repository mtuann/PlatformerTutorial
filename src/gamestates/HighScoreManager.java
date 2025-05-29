package gamestates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

public class HighScoreManager {
    private static final String DATA_FILE = "highscores.dat";
    private List<HighScoreEntry> scores;
    private Map<String, HighScoreEntry> userMap;
    private String currentSortColumn = "level";
    private boolean ascending = false;

    public HighScoreManager() {
        scores = new ArrayList<>();
        userMap = new HashMap<>();
        loadData();
    }

    private void loadData() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                @SuppressWarnings("unchecked")
                Map<String, HighScoreEntry> loadedData = (Map<String, HighScoreEntry>) ois.readObject();
                userMap = loadedData;
                scores = new ArrayList<>(userMap.values());
                sortScores();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(userMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdateUser(String username, int level, int completedTime) {
        HighScoreEntry entry = userMap.get(username);
        if (entry == null) {
            entry = new HighScoreEntry(username, level, completedTime);
            userMap.put(username, entry);
            scores.add(entry);
        } else {
            entry.setLevel(level);
            entry.setCompletedTime(completedTime);
        }
        sortScores();
        saveData();
    }

    public void addNewUser(String username) {
        if (!userMap.containsKey(username)) {
            HighScoreEntry entry = new HighScoreEntry(username);
            userMap.put(username, entry);
            scores.add(entry);
            sortScores();
            saveData();
        }
    }

    public HighScoreEntry getUser(String username) {
        return userMap.get(username);
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