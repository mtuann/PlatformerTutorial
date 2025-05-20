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