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
                
                // Initialize scores list and lastUpdated for each entry
                for (HighScoreEntry entry : loadedData.values()) {
                    if (entry.getScores() == null) {
                        entry.getScores().clear(); // This will initialize the list if it's null
                    }
                    // Ensure lastUpdated is set
                    entry.getLastUpdated();
                }
                
                userMap = loadedData;
                scores = new ArrayList<>(userMap.values());
                sortScores();
                
                System.out.println("=== Loaded High Score Data ===");
                System.out.println("Total users: " + userMap.size());
                for (HighScoreEntry entry : scores) {
                    try {
                        System.out.println("User: " + entry.getUsername());
                        System.out.println("Total scores: " + entry.getScores().size());
                        System.out.println("Highest level: " + entry.getHighestLevel());
                        System.out.println("Last updated: " + entry.getFormattedLastUpdated());
                        System.out.println("------------------------");
                    } catch (Exception e) {
                        System.out.println("Error processing entry for user: " + entry.getUsername());
                        e.printStackTrace();
                    }
                }
                System.out.println("=== Data Load Complete ===\n");
            } catch (Exception e) {
                System.out.println("Error loading high score data: " + e.getMessage());
                e.printStackTrace();
                // Initialize with empty data if loading fails
                userMap = new HashMap<>();
                scores = new ArrayList<>();
            }
        } else {
            System.out.println("No existing high score data found. Starting with empty data.");
            userMap = new HashMap<>();
            scores = new ArrayList<>();
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(userMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addScore(String username, int level, int completedTime) {
        System.out.println("\n=== Adding new score ===");
        System.out.println("Username: " + username);
        System.out.println("Level: " + level);
        System.out.println("Completion Time: " + new ScoreEntry(level, completedTime).getFormattedCompletedTime());
        
        HighScoreEntry entry = userMap.get(username);
        if (entry == null) {
            System.out.println("Creating new user entry");
            entry = new HighScoreEntry(username);
            userMap.put(username, entry);
            scores.add(entry);
        } else {
            System.out.println("Updating existing user");
            System.out.println("Previous highest level: " + entry.getHighestLevel());
            System.out.println("Previous best time for level " + level + ": " + 
                (entry.getBestTimeForLevel(level) > 0 ? 
                    new ScoreEntry(level, entry.getBestTimeForLevel(level)).getFormattedCompletedTime() : 
                    "No previous time"));
        }
        
        entry.addScore(level, completedTime);
        System.out.println("New highest level: " + entry.getHighestLevel());
        System.out.println("New best time for level " + level + ": " + 
            new ScoreEntry(level, entry.getBestTimeForLevel(level)).getFormattedCompletedTime());
        System.out.println("Total scores for this user: " + entry.getScores().size());
        System.out.println("=== Score added successfully ===\n");
        
        sortScores();
        saveData();
    }

    public void addNewUser(String username) {
        System.out.println("\n=== Adding new user ===");
        System.out.println("Username: " + username);
        
        if (!userMap.containsKey(username)) {
            System.out.println("Creating new user entry");
            HighScoreEntry entry = new HighScoreEntry(username);
            userMap.put(username, entry);
            scores.add(entry);
            sortScores();
            saveData();
            System.out.println("User created successfully");
        } else {
            System.out.println("User already exists");
            System.out.println("Current highest level: " + userMap.get(username).getHighestLevel());
            System.out.println("Total scores: " + userMap.get(username).getScores().size());
        }
        System.out.println("=== User addition complete ===\n");
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
            case "level" -> Comparator.comparingInt(HighScoreEntry::getHighestLevel);
            case "completedTime" -> Comparator.comparingInt(e -> e.getLatestScore().getCompletedTime());
            case "updatedTime" -> Comparator.comparing(HighScoreEntry::getLastUpdated);
            default -> Comparator.comparingInt(HighScoreEntry::getHighestLevel);
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

    public List<ScoreEntry> getUserScores(String username) {
        HighScoreEntry entry = userMap.get(username);
        return entry != null ? entry.getScores() : new ArrayList<>();
    }

    public int getUserHighestLevel(String username) {
        HighScoreEntry entry = userMap.get(username);
        return entry != null ? entry.getHighestLevel() : 0;
    }

    public int getUserBestTimeForLevel(String username, int level) {
        HighScoreEntry entry = userMap.get(username);
        return entry != null ? entry.getBestTimeForLevel(level) : 0;
    }
} 