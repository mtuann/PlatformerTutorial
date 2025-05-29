# Platformer Game

A 2D platformer game developed as a final project for the "Object-Oriented Programming & Data Structures" course at VinUniversity. This project demonstrates the application of object-oriented programming principles and data structures in game development.

## 🎮 Game Features

- Smooth 2D platformer gameplay
- Multiple levels with increasing difficulty
- Interactive game objects and collectibles
- Enemy AI and combat mechanics
- Sound effects and background music
- Menu system with options
- Particle effects and animations
- Save/load game progress

## Additional Features

### User Registration System
- Username registration with validation
- Support for existing users to continue with their accounts
- Welcome message displaying the current user's name in the main menu
- Username validation rules:
  - Only letters and numbers allowed
  - Maximum length of 15 characters
  - Case-insensitive uniqueness check

### High Score System
- Comprehensive leaderboard with multiple sorting options:
  - Username (alphabetical)
  - Level achieved
  - Completion time
  - Last updated time
- Multiple score entries per user:
  - Each level completion is recorded separately
  - Tracks highest level achieved
  - Records best completion time for each level
  - Maintains history of all attempts

### Data Persistence
- All user data and scores are stored in a single `highscores.dat` file
- Data structure includes:
  - User information
  - Multiple score entries per user
  - Level completion times
  - Timestamps for each entry
- Automatic data loading and saving
- Robust error handling for data operations

### Level Completion Tracking
- Automatic score recording when completing levels
- Tracks:
  - Level number
  - Completion time
  - Timestamp
- Updates user's highest level and best times
- Detailed logging of level completion events

## 👥 Team Members

- [Team Member 1 Name] - [Role/Contribution]
- [Team Member 2 Name] - [Role/Contribution]
- [Team Member 3 Name] - [Role/Contribution]
- [Team Member 4 Name] - [Role/Contribution]

## 🛠️ Technical Implementation

### Project Structure
```
PlatformerTutorial/
├── src/
│   ├── main/           # Main game classes
│   ├── entities/       # Game entities (player, enemies)
│   ├── gamestates/     # Different game states
│   ├── levels/         # Level design and management
│   ├── objects/        # Game objects and items
│   ├── ui/            # User interface elements
│   ├── utilz/         # Utility classes
│   ├── audio/         # Sound management
│   ├── effects/       # Visual effects
│   └── inputs/        # Input handling
├── res/               # Game resources
│   ├── audio/        # Sound files
│   └── lvls/         # Level data
└── README.md
```

### Key OOP Concepts Implemented

1. **Inheritance**
   - Game entities hierarchy
   - UI elements inheritance
   - Game state management

2. **Polymorphism**
   - Different types of enemies
   - Various game objects
   - Multiple game states

3. **Encapsulation**
   - Private class members
   - Getter/setter methods
   - Protected data access

4. **Data Structures**
   - Arrays for level data
   - Lists for game objects
   - Maps for resource management

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (recommended: IntelliJ IDEA or Eclipse)

### Installation

1. Clone the repository:
```bash
git clone [repository-url]
```

2. Navigate to the project directory:
```bash
cd PlatformerTutorial
```

3. Compile the project:
```bash
javac -d . -cp ".:res" src/main/*.java src/utilz/*.java src/ui/*.java src/objects/*.java src/gamestates/*.java src/inputs/*.java src/levels/*.java src/entities/*.java src/audio/*.java src/effects/*.java
```

4. Run the game:
```bash
java -cp ".:res" main.MainClass
```

## 🎯 Game Controls

- **Movement**: Arrow Keys or WASD
- **Jump**: Space
- **Attack**: [Attack Key]
- **Pause**: ESC
- **Menu Navigation**: Arrow Keys

## 🎨 Graphics and Assets

All game assets are stored in the `res` directory:
- Sprites and animations
- Background images
- UI elements
- Sound effects and music

## 📝 Project Documentation

### Design Patterns Used
- State Pattern for game states
- Singleton Pattern for resource management
- Observer Pattern for event handling
- Factory Pattern for object creation

### Code Quality
- Clean code principles
- Consistent naming conventions
- Proper documentation
- Error handling

## 🏆 Learning Outcomes

This project helped us understand and implement:
1. Object-Oriented Programming principles
2. Data structure implementation
3. Game development concepts
4. Team collaboration
5. Version control
6. Project management

## 📚 References

- [List any references, tutorials, or resources used]

## 📄 License

This project is created for educational purposes as part of the "Object-Oriented Programming & Data Structures" course at VinUniversity.

## 🙏 Acknowledgments

- Course instructors
- Game development community
- Open-source resources used
- [Any other acknowledgments]

## Technical Details

### Data Structure
The game uses a serialized data structure to store all user information and scores:
- `HighScoreEntry` class for user data
- `ScoreEntry` class for individual level completions
- Automatic initialization of new users
- Null safety checks for data integrity

### UI Enhancements
- Welcome message in main menu
- Interactive leaderboard with sorting buttons
- Scrollable score display
- Visual feedback for user interactions

### Error Handling
- Graceful handling of missing data
- Automatic initialization of new users
- Data validation and sanitization
- Backup mechanisms for data persistence

## How to Use

1. Start the game and register a username
2. Play through levels to accumulate scores
3. View your progress in the high score menu
4. Sort scores by different attributes
5. Continue playing with existing account on next launch

## Data Storage
All game data is stored in `highscores.dat`, which contains:
- User accounts
- Level completion records
- Best times
- Achievement history

The data is automatically loaded when the game starts and saved when changes occur. 