# 🚢 Battleship Game

## Project Description

This project is designed as a console-based educational project. It is a modified version of the classic **Battleship** game, developed in **Java**.
In addition to the basic rules, it features:


- 🤖 **Bot opponent** — Play solo against a Bot player
- 🚀 **Special ships** — Certain ships trigger counter-actions when hit
- 💬 **Console-based interface** — Interact through real-time prompts and feedback
- 🔁 **Turn-based mechanics** — Automatic player switching after each turn
- 🧭 **Command support** — Use the commands `HELP`, `EXIT`, `RESTART`, and `SHOWBOARDS` for more control

---

## User Guide

### 🟢 Starting the Game

1. Launch the game as described in the setup instructions.
2. Enter the names of both players, **or**:
    - Press **Enter** for the second player to play against the **bot**.

---

### 🚢 Placing Ships

- Each player places their ships manually.
- A list of available ships will be displayed in the terminal.
- Choose a ship from the list.
- Enter the **coordinates** where you want to place the ship.
- Repeat the process until you've placed ships covering a total of **6 cells**.

---

### 🎯 Gameplay

- Players take turns attacking a cell on the opponent’s board.
- If a **special ship** is hit, a **counter-action** may be triggered  
  (currently: skip a round or a random counter-attack).

---

### 🏁 Game Over

- The game ends when **all ships** of one player have been destroyed.

---

## Build & Run Instructions

### 📋 Requirements

- **Language**: Java 21 or higher
- **Build Tool**: Maven 3.9.6 or higher
- **IDE (optional)**: IntelliJ IDEA, Eclipse

---


### 📥 Cloning the Repository

```bash
   git clone git@github.com:zakariahmed1/battleship.git
   cd battleship
```
---
### 🛠️ Option 1: Build & Run via Maven

#### 1. Build the project:
```bash
mvn package
```

This will:
- Download dependencies
- Compile the source code
- Run tests (if present)
- Package the application into a JAR file located in the target/ directory

#### 2. Run the project:
```bash
java -jar target/BattleShip-Project-1.0-SNAPSHOT.jar
```
---

### 💻 Option 2: Build & Run via IDE
1. Open the project in your IDE (e.g. IntelliJ IDEA or Eclipse)
2. Import it as a **Maven project**
3. Ensure that **Java 21** is selected as the project SDK
4. Navigate to the following file and run the `main()` method:

```
src/main/java/application/App.java
```
---

## Implementation Overview
### 🧩 High-Level Components

- **Board & Cell:**  
  Represent the grid and individual cell states, managing ship placement and attacks.
- **Ship & Subclasses (Destroyer, Carrier, Cruiser):**  
  Abstract base class for ships, with concrete implementations for different ship types and behaviours
- **Player (Abstract) and Implementations (HumanPlayer, BotPlayer):**  
  The player class handles user inputs (placing ships, attacks) and the BotPlayer generates those by his own.
- **GameManager:**  
  Game loop for game turns, attack handling, and special actions and game commands
- **IOHandler:**  
  Console I/O abstraction for cleaner prompts and outputs.


### 🧩 Interfaces Between Components
- **Player → Board:** Players place ships and defend attacks on their board.
- **Board → Cell:** Board initialized the cells and adds/attacks ships
- **Cell → Ship:** Cells can contain ships and keep track if revealed
- **Ship → SpecialForce**  Manages special ship actions and queues them on the GameManager
- **GameManager → Player** Gets the chosen fleet and the attacks from a player and performs attacks on the other player.
- **GameManager → CommandExecutor** Implements the logic of special ship-counterattacks


- **GameManager → CommandException :** GameManager handles Command-Exceptions
- **GameManager/Player → IOManager:** Performs IO via the IOManager class
- **GameManager → Queue<SpecialForces>:** Executes all the queued special-actions of ships
- **IOManager → InputParser:** Parses inputs into Cells, Ships, Commands
- **IOManager/InputParser →ShipBuilder:**  Retrieves supported ship types and constructs ship instances with descriptions.

---
## Third-Party Libraries
- **JUnit 5 (org.junit.jupiter)** – used for unit testing and test automation

---
## Notable Programming Techniques

- Encapsulation & Abstraction: Using the main principles of OOP to encapsulate classes and behaviours

- Polymorphism: Using Player and Ship and SpecialForce as a reference type and the overridden methods via dynamic dispatching
- Static members: Using static methods for non-object related behaviours (parsing data)
- Singleton pattern: The GameManager is implemented as a Singleton
- Exception Handling: Used to manage invalid inputs and to handle game-commands
- Functional Programming & Streams: Used lambdas to implement functional interfaces, streams for concise code
- Generic types / AAD and their implementations: Used Lists and Queue 
- IO: Used Scanner class for IO via terminal (System.in/out)
- Regex

- Unit tests: JUnit for automatic tests
- Generally code simplifying techniques like factorizing in methods, returns in loops, boolean conditions etc.

---
## Human Experience & Collaboration

### 🧑‍🤝‍🧑 Group Members and Workload

| Name                | GitHub Username         | Role                          |
|---------------------|-------------------------|-------------------------------|
| Zakaria Ahmed       | @zakariahmed1           | Board and Cell logic          | 
| Anastasiia Guliaeva | @kamikaros              | Ship logic and Bot            | 
| Philipp Vieider     | @str0el                 | Game logic and HumanPlayer    | 
| Leonardo De Riva    | @leoddi (left group)    | Game logic and HumanPlayer    | 


### 💻 Use of Git

The team collaborated using GitHub.

We used mainly per-user branches and merged our changes into the dev branch.
After having a first working prototype, we merged dev into main.

## 🧠 Individual Challenges

### Zakaria Ahmed (@zakariahmed1)
    - logic  between cell and board so that they work together
    - coordinating  with the group overal working in a team
    - managing the ship clas,ses
    - coming up with enough meth,ods for the Project

### Anastasiia Guliaeva (@kamikaros):

    - Developing a working game flow between the GameManager, Player, Board and Cell classes.
    - Making sure the ship placement goes according to the rules of the game and doesn't overlap.
    - Preventing invalid attacks inside the game.
    - Maintaining the game state consistent (board update, hiding the enemy's board, etc).

### Philipp Vieider (@str0el):

    - Abstraction of the Player-types, such that we do not have to perform instanceof-checks all the time
    - Generally managing the turn logic and the continuous changes while implementing new features
    - Handling special force-actions of ships via a Queue after a turn was player, without changing the board's outputs
    - Generally the communication and programming in a group

### Leonardo De Riva (@leoddi):

    - left the group

## 📌 Final Notes
This project provided us with valuable experience in Java programming.  
For many of us, it was our first group project in Java, and a great opportunity to apply the concepts and techniques we learned during the Programming Project course.

Of course, there is still plenty of room for improvement - both in the technical implementation and in how we structured our collaboration.  
Nonetheless, we are happy of what we accomplished within the time we had available.

We would like to thank our professors/tutors for the guidance throughout the course.
