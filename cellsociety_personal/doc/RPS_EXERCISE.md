# Rock Paper Scissors Lab Discussion
#### Trevon Helm, Aryan Kothari, Charles Turpin


### High Level Design Goals



### CRC Card Classes

This class's purpose or value is to represent a customer's order:
```java
public class Order {
 	// returns whether or not the given items are available to order
 	public boolean isInStock (OrderLine items)
 	// sums the price of all the given items
 	public double getTotalPrice (OrderLine items)
 	// returns whether or not the customer's payment is valid
 	public boolean isValidPayment (Customer customer)
 	// dispatches the items to be ordered to the customer's selected address
 	public void deliverTo (OrderLine items, Customer customer)
 }
 ```

This class's purpose or value is to manage the game:

```java
public class Game {
	private int currRound;
private boolean gameState;
private List<Player> players;
	
 	// Function will ask the user for the number of players playing
 	Private void askNumberOfPlayers (int);
     	// Print the scores of each player.
 	Private void printResults ();
	// Initialize the game.
	Public void startGame();
	// Clean up all elements of the game.
	Public void endGame();
	// Start the round
	Private void play();
	//
	Private void createWeapons();
	Private void updateScores();
 }
```

```java
public class Weapon {
 	public void addWeapon(Collection<Integer> data);
     // creates an order from the given data
 	public Order makeOrder (String structuredData)
 }
```

```java
public class Player {
 	// sums the numbers in the given data
 	public int getTotal (Collection<Integer> data)
     // creates an order from the given data
 	public Order makeOrder (String structuredData)
 }
```


```java
public class Something {
 	// sums the numbers in the given data
 	public int getTotal (Collection<Integer> data)
     // creates an order from the given data
 	public Order makeOrder (String structuredData)
 }
```


### Use Cases

* A new game is started with five players, their scores are reset to 0.
 ```java
startGame()
askNumberOfPlayers()
setWeapons()
 ```

* A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
 chooseWeapon()
 ```

* Given three players' choices, one player wins the round, and their scores are updated.
 ```java
 play()
 updateScores()
 ```

* A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
 addWeapon()
 ```

* A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
 startGame()
askNumberOfPlayers()
setWeapons()
 ```
