# Breakout Abstractions Lab Discussion
#### Charles Turpin, Trevon Helm, Aryan Kothari


## Principle Slogans

* Single Responsibility

* Open Closed



### Block

This superclass's purpose as an abstraction: Basic block behavior, like setting color, damage (status), initialization, etc.
```java
 public class Block {
 	public int something ()
 	// no implementation, just a comment about its purpose in the abstraction: Set the damage the block has taken
 }
```

#### Subclasses

This subclass's high-level behavioral differences from the superclass: Override the superclass methods, implement new methods specific to this specific block behavior.
```java
 public class SuperBlock extends Block {
 	public int something ()
 	// no implementation, just a comment about what it does differently: Explode block if health == 0
 }
```

#### Affect on Game/Level class

Game class would call methods from Block/Superblock when updating the status of the objects on Screen.

### Power-up

This superclass's purpose as an abstraction: It will handle basic powerup functionalities shared by all powerups. We brainstormed that this would be drawing the powerup (things like color and appearance), falling down from a brick, and a method for having the ball be collected by the paddle.
```java
 public class PowerUp {
 	public int something ()
 	// no implementation, just a comment about its purpose in the abstraction: fallDown() will have the powerup fall down towards the bottom of the screen.
 }
```

#### Subclasses

This subclass's high-level behavorial differences from the superclass: Will have the specific behavior of each powerup, so an extendPaddle powerup would have some function that extends the paddle.

```java
 public class X extends PowerUp {
 	public int something ()
 	// no implementation, just a comment about what it does differently: extendPaddle() will extend the paddle a certain length.
 }
```

#### Affect on Game/Level class
The game class will be responsible for “activating” the power up by calling other objects upon being collected by the paddle, depending on what the power up actually is (speeding the ball, increasing paddle length, etc.)


### Level

This superclass's purpose as an abstraction: Responsible for setting the screen, i.e brick layout from file, paddle, ball, lives
```java
 public class Level {
 	public int something ()
 	// no implementation, just a comment about its purpose in the abstraction: Load file in from resources folder
 }
```

#### Subclasses

This subclass's high-level behavorial differences from the superclass: Will feature features specific to the level.
```java
 public class X extends Level {
 	public int something ()
 	// no implementation, just a comment about what it does differently: verticalPaddle() : Will start the program with the paddle on the top of the screen.
 }
```

#### Affect on Game class

This should clean up methods in the game class, as a lot of it’s functionality in drawing the objects will not be the responsibility of the Levels class.

### Others?

