package character;

import java.util.Scanner;
import java.util.Stack;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Board.*;
import commonPackage.usefor.test.RealPlayer;
import item.*;
import main.Game;
import main.InvalidMove;
import resources.SoundResources;

/**
 * This class represents the character that player controls, player can do all
 * sorts of things such as move, attack, use items and so on. Player can also
 * equip items, and have certain health, damage and defence, when his health
 * reaches 0, he dies and the game is over.
 * 
 * @author stella
 *
 */
public class Player implements RealPlayer{
	// the maximum amount of items can be stored in the inventory
	public static final int INVENTORY_CAPACITY = 30;
	private Game game;
	private Monster VSMonster;
	private boolean moveAble=true;
	// a stack of items that player collected and can be used later.
	private Stack<ConsumableItem> inventory;
	// the current position of player on board
	private int xPos, yPos;
	private int health, damage, defence;
	private int gold = 0;
	private int speed = 1;
	private String facingDirection;
	private boolean isDead;

	// equipments
	private Armor armor;
	private Weapon weapon;
	private Wing wing;

	public Player() {
		facingDirection = "down";
		inventory = new Stack<>();
		health = 100;
		damage = 10;
		defence = 10;
		gold = 0;
		xPos = 5;
		yPos = 0;
	}

	/**
	 * Use for reload
	 */
	public void ParserPlayer(Scanner sc) {
		try {
			// first facing direction
			this.facingDirection = sc.next();
			// second parse inventory

			if (sc.hasNext("i")) {
				sc.next(); // consume s
				sc.next(); // consume (
				while (sc.hasNextInt()) {
					int a = sc.nextInt();
					System.out.println(a);
					if (a == 30) this.inventory.push(new Key(-1, -1, "gold"));
					else if (a == 31) this.inventory.push(new Key(-1, -1, "cyan"));
					else if (a == 32) this.inventory.push(new Key(-1, -1, "bronze"));
					else if (a == 33) this.inventory.push(new Key(-1, -1, "purple"));
					else if (a == 34) this.inventory.push(new Key(-1, -1, "silver"));
					else if (a == 40) this.inventory.push(new BloodVial(-1, -1, "big"));
					else if (a == 41) this.inventory.push(new BloodVial(-1, -1, "small"));
					else if (a == 43) this.inventory.push(new Bomb(-1, -1));
					else if (a == 48) this.inventory.push(new FateCoin(-1,-1));
					else if (a == 48) this.inventory.push(new MonsterManual());
				}
			}
			sc.next(); // consume )
			// third parse parameter
			this.xPos = sc.nextInt();
			this.yPos = sc.nextInt();
			this.health = sc.nextInt();
			this.damage = sc.nextInt();
			this.defence = sc.nextInt();
			this.gold = sc.nextInt();
			this.speed = sc.nextInt();
			// finally add gear
			while (sc.hasNextInt()) {
				int gear = sc.nextInt();
				if (gear == -1) continue;
				else if (gear == 70) this.armor = new Armor(-1, -1, 0);
				else if (gear == 71) this.armor = new Armor(-1, -1, 1);
				else if (gear == 72) this.armor = new Armor(-1, -1, 2);
				else if (gear == 73) this.weapon = new Weapon(-1, -1, 0);
				else if (gear == 74) this.weapon = new Weapon(-1, -1, 1);
				else if (gear == 75) this.weapon = new Weapon(-1, -1, 2);
				else if (gear == 76) this.wing = new Wing(-1, -1, 0);
				else if (gear == 77) this.wing = new Wing(-1, -1, 1);
				else if (gear == 78) this.wing = new Wing(-1, -1, 2);
			}

		} catch (java.util.InputMismatchException e) {
			System.out.println(e);
		}
		
	}

	/**
	 * pick up an item and add to inventory
	 * 
	 * @param item
	 * @throws InvalidMove
	 */
	public void addItem(ConsumableItem item) throws InvalidMove {
		if (inventory.size() == INVENTORY_CAPACITY)
			throw new InvalidMove("Inventory is full.");

		inventory.add(item);
	}

	/**
	 * player tries to attack monster and monster attack back, the damage is equal
	 * to current damage - other's defence. when either of them reaches 0 health,
	 * the fight is over.
	 * 
	 * @param monster
	 * @return
	 */
	public boolean attack(Monster monster, Player player) {
		game.setAttacking(true);
		moveAble=false;
		VSMonster = monster;
		java.util.Timer timer = new java.util.Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				
				int playerDamage = damage - monster.getDefence();
				if (playerDamage < 0)
					playerDamage = 0;

				int monsterDamage = monster.getDamage() - defence;
				if (monsterDamage < 0)
					monsterDamage = 0;
				System.out.println("monster health: " + monster.getHealth());
				System.out.println("player health: " + health);
				monster.setHealth(monster.getHealth() - playerDamage);
				health -= monsterDamage;
				SoundResources.Fight1.sound.play();
				if (monster.getHealth() <= 0) {
					this.cancel();
					game.setAttacking(false);
					monster.defeated(player);
					moveAble=true;
					if(monster.getName().equals("98")) {
						game.SetgameWin();
					}
					System.out.printf("Gold: %d\n", player.getGold());					
				}else if(health<0) {
					this.cancel();
					game.setAttacking(false);
					moveAble=true;
				}
				game.changeView();
			}
		}, 0, 200);

		//game.setAttacking(false);
		return true;
	}

	/**
	 * player tries to equip an equipment on ground, and if player currently have
	 * one on him, the one will be taken off and put on ground instead
	 * 
	 * @param item
	 * @return the equipment being take off, null if doesn't have one
	 */
	public WearableItem equip(WearableItem item) {
		WearableItem old = null; // the equipment that is going to be taken off
		if (item instanceof Armor) {
			if (armor != null)
				old = armor;
			armor = (Armor) item;
			System.out.println("armor :" + armor.getDefence());
		} else if (item instanceof Weapon) {
			if (weapon != null)
				old = weapon;
			weapon = (Weapon) item;
			System.out.println("weapon :" + weapon.getAttack());
		} else if (item instanceof Wing) {
			if (wing != null)
				old = wing;
			wing = (Wing) item;
			System.out.println("wing :" + wing.getIncreasedDamage());
			System.out.println("wing :" + wing.getIncreasedDefense());
		}

		if (old != null)
			old.takeOff(this);	//if previously have an equipment, take it off
								//with stats being deducted
		item.putOn(this);		//and put on new one, with new stats boost

		return old;
	}

	/**
	 * use a key in the corresponding color from inventory to open door
	 * 
	 * @param color
	 *            the color of the key needed to open the door
	 * @throws InvalidMove
	 *             throws an invalid move exception if doesn't have a key in that
	 *             color
	 */
	public void useKey(String color) throws InvalidMove {
		boolean hasKey = false;
		for (ConsumableItem item : inventory) {
			if (item instanceof Key && ((Key) item).getColor().equals(color)) {		//found a key to use
				inventory.remove(item);
				hasKey = true;
				break;
			}
		}

		// when all items in inventory has been checked and
		// player doesn't have the key, the door cannot be opened
		if (!hasKey)
			throw new InvalidMove("You don't have a <" + color + "> key in inventory, cannot open the door");
	}

	/**
	 * use a bomb from inventory to break a breakable wall.
	 * 
	 * @throws InvalidMove
	 *             throws an invalid move excepction if doesn't have a bomb
	 */
	public void useBomb() throws InvalidMove {
		boolean hasBomb = false;
		for (ConsumableItem item : inventory) {
			if (item instanceof Bomb) {			//found a bomb to use
				inventory.remove(item);		
				hasBomb = true;
				break;
			}
		}

		//no bomb to use
		if (!hasBomb)
			throw new InvalidMove("No bomb in inventory, cannot break the wall");
	}

	/**
	 * click on the health potion to restore health
	 * 
	 * @param type
	 * @throws InvalidMove
	 */
	public void useHealthPotion(String type) throws InvalidMove {
		BloodVial b = null;
		for (ConsumableItem item : inventory) {
			if (item instanceof BloodVial && ((BloodVial) item).getType().equals(type)) {		//found a health potion to use
				b = (BloodVial) item;
				break;
			}
		}

		if (b == null) {
			throw new InvalidMove("No health potion to use.");
		} else {
			b.use(this);
			inventory.remove(b);
		}
	}
	
	/**
	 * click on the fate coin to use, player will randomly gain or lose health.
	 * 
	 * @throws InvalidMove
	 */
	public void useFateCoin() throws InvalidMove {
		FateCoin c = null;
		for (ConsumableItem item : inventory) {
			if (item instanceof FateCoin) {			//found a fate coin to use
				c = (FateCoin) item;
				break;
			}
		}
		if (c == null) {
			throw new InvalidMove("No fate coin to use.");
		} else {
			c.use(this);
			inventory.remove(c);
		}
	}
	
	// ================ movement methods =====================
	public void move(String direction) throws InvalidMove {
		//reset player to be not in shop or temple
		game.setInShop(false);
		game.setInTemple(false);
		
		int boardSize = Level.BOARDSIZE;
		Entity[][] board = game.getBoard().getCurrentLevel().getEntities();

		if (direction.equals("right")) {
			moveRight(board, boardSize);
			System.out.println("player x: "+getXPos()+"  y:"+getYPos() );
		} else if (direction.equals("left")) {
			moveLeft(board, boardSize);
			System.out.println("player x: "+getXPos()+"  y:"+getYPos() );
		} else if (direction.equals("up")) {
			moveUp(board, boardSize);
			System.out.println("player x: "+getXPos()+"  y:"+getYPos() );
		} else if (direction.equals("down")) {
			moveDown(board, boardSize);
			System.out.println("player x: "+getXPos()+"  y:"+getYPos() );
		}
		
		game.changeView();
		newGridInteraction(board);
	}

	public void moveRight(Entity[][] board, int boardSize) throws InvalidMove {
		if (xPos + 1 > boardSize - 1)
			throw new InvalidMove("Cannot move out of board");
		if (this.isDead)
			throw new InvalidMove("Cannot move out of board");

		Entity e = board[yPos][xPos + 1];
		if (e != null) {
			if (e instanceof Wall)
				throw new InvalidMove("Cannot move towards wall on right");
			else if (e instanceof Door)
				game.tryOpenDoor((Door) e);
		}

		if (!(e instanceof Ground && ((Ground) e).isLava()))
			xPos++;
	}

	public void moveLeft(Entity[][] board, int boardSize) throws InvalidMove {
		if (xPos - 1 < 0)
			throw new InvalidMove("Cannot move out of board");

		Entity e = board[yPos][xPos - 1];
		if (e != null) {
			if (e instanceof Wall)
				throw new InvalidMove("Cannot move towards wall on left");
			else if (e instanceof Door)
				game.tryOpenDoor((Door) e);
		}

		if (!(e instanceof Ground && ((Ground) e).isLava()))
			xPos--;
	}

	public void moveUp(Entity[][] board, int boardSize) throws InvalidMove {
		if (yPos - 1 < 0)
			throw new InvalidMove("Cannot move out of board");

		Entity e = board[yPos - 1][xPos];
		if (e != null) {
			if (e instanceof Wall)
				throw new InvalidMove("Cannot move towards wall on top");
			else if (e instanceof Door)
				game.tryOpenDoor((Door) e);
		}

		if (!(e instanceof Ground && ((Ground) e).isLava()))
			yPos--;
	}

	public void moveDown(Entity[][] board, int boardSize) throws InvalidMove {
		if (yPos + 1 > boardSize - 1)
			throw new InvalidMove("Cannot move out of board");
		Entity e = board[yPos + 1][xPos];
		if (e != null) {
			if (e instanceof Wall)
				throw new InvalidMove("Cannot move towards wall on bottom");
			else if (e instanceof Door)
				game.tryOpenDoor((Door) e);
		}

		if (!(e instanceof Ground && ((Ground) e).isLava()))
			yPos++;
	}

	/**
	 * if there is an item that can be picked up in the new position, pick it up, if
	 * there is a stair, teleport player to corresponding level, if there is a
	 * monster, player tries to attack it
	 * 
	 * @param board
	 * @throws InvalidMove
	 */
	public void newGridInteraction(Entity[][] board) throws InvalidMove {
		Entity e = board[yPos][xPos];
		if (e != null) {
			if (e instanceof Ground) {
				Ground g = ((Ground) e);
				if (g.getWhatContain() instanceof Item) {			//if there is an item, try to pick it up
					if (g.getWhatContain() instanceof ConsumableItem) {
						addItem((ConsumableItem) (g.getWhatContain()));
						game.setToEmpty(g);
					}
				} else if (g.getWhatContain() instanceof Monster) {	//if there is a monster, attack it
					System.out.println("monster encountered");
					if (attack((Monster) g.getWhatContain(),this)) {
						g.cleanBattleground();
						System.out.println("win");
					} else {
						JOptionPane.showConfirmDialog(new JLabel("defeat!"), "Again?");
					}
				} else if (g.getWhatContain() instanceof Shop) {		//if there is a shop
					System.out.println("shop encountered");
					game.setInShop(true);
				} else if(g.getWhatContain() instanceof Temple) {		//if there is a temple
					game.setInTemple(true);
				} else if(g.getWhatContain() instanceof WiseMan) {	//if there is a wise man, give stuff to player
					((WiseMan)g.getWhatContain()).give(this);
					game.setToEmpty(e);
				}
			} else if (e instanceof Stairs) {						//if there is a stairs, go to corresponding level
				if (((Stairs) e).upOrDownStair())
					game.getBoard().setCurrentLevel(game.getBoard().getCurrentLevelNumber() + 1);
				else
					game.getBoard().setCurrentLevel(game.getBoard().getCurrentLevelNumber() - 1);
			}
		}
	}

	/**
	 * finds the next grid on player's current facing direction. for example if
	 * player is in pisition (2, 2) and facing right, the position of this grid will
	 * be (2, 3)
	 * 
	 * @param currentBoard
	 * @return
	 */
	public Entity findFacingEntity(Entity[][] currentBoard) {
		Entity e = null;
		int boardSize = Level.BOARDSIZE;
		if (facingDirection.equals("right")) {
			if (xPos + 1 <= boardSize - 1)
				e = currentBoard[yPos][xPos + 1];
		} else if (facingDirection.equals("left")) {
			if (xPos - 1 >= 0)
				e = currentBoard[yPos][xPos - 1];
		} else if (facingDirection.equals("up")) {
			if (yPos - 1 >= 0)
				e = currentBoard[yPos - 1][xPos];
		} else if (facingDirection.equals("down")) {
			if (yPos + 1 <= boardSize - 1)
				e = currentBoard[yPos + 1][xPos];
		}
		return e;
	}

	/**
	 * check whether player is dead.
	 * @return	true 
	 * 				- if player's health reaches 0
	 */
	public boolean checkDead() {
		if (health <= 0) {
			return isDead = true;
		}
		return isDead;
	}

	// =============== counting inventory ====================
	/**
	 * count the number of keys in given color in inventory
	 * 
	 * @param color
	 * @return
	 */
	public int getNumKeys(String color) {
		int num = 0;
		for (ConsumableItem item : inventory) {
			if (item instanceof Key) {
				if (((Key) item).getColor().equals(color)) {
					num++;
				}
			}
		}
		return num;
	}

	public int getNumBombs() {
		int num = 0;
		for (ConsumableItem item : inventory) {
			if (item instanceof Bomb) {
				num++;
			}
		}
		return num;
	}

	public int getNumBlood(String type) {
		int num = 0;
		for (ConsumableItem item : inventory) {
			if (item instanceof BloodVial) {
				if (((BloodVial) item).getType().equals(type)) {
					num++;
				}
			}
		}
		return num;
	}

	// ================= setter and getters ===================

	/**
	 * set a change on current health, either because of using items (health
	 * potion/equipments) or attacted by a monster
	 * 
	 * @param amount
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getDefence() {
		return defence;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getGold() {
		return gold;
	}

	public String getFacingDirection() {
		return facingDirection;
	}

	public void setFacingDirection(String direction) {
		this.facingDirection = direction;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public Armor getCurrentArmor() {
		return armor;
	}

	public Weapon getCurrentWeapon() {
		return weapon;
	}

	public Wing getCurrentWing() {
		return wing;
	}

	public Stack<ConsumableItem> getInventory() {
		return inventory;
	}

	public void setCurrentGame(Game game) {
		this.game = game;
	}

	public Player setPos(int x, int y) {
		this.xPos = x;
		this.yPos = y;
		return this;
	}
	
	public boolean moveAble() {
		return moveAble;
	}
	
	public Monster getVSmonster() {
		return this.VSMonster;
	}

	@Override
	public String toString() {
		StringBuilder temp = new StringBuilder();
		// first append face direction
		temp.append(facingDirection + " ");
		// second append stack
		Stack<ConsumableItem> original = this.inventory;
		Stack<String> inverse = new Stack<>();
		for (ConsumableItem ci : original)
			inverse.push(ci.toString());
		temp.append("i ( ");
		for (String str : inverse)
			temp.append(str + " ");
		temp.append(")\n");
		// third int x,int y, int hh, int de,int df,int gd,int speed,
		temp.append(this.xPos + " ");
		temp.append(this.yPos + " ");
		temp.append(this.health + " ");
		temp.append(this.damage + " ");
		temp.append(this.defence + " ");
		temp.append(this.gold + " ");
		temp.append(this.speed + " \n");
		// finally Armor ar,Weapon we,Wing wi
		if (this.armor == null)
			temp.append("-1 ");
		else
			temp.append(this.armor.toString() + " ");
		if (this.weapon == null)
			temp.append("-1 ");
		else
			temp.append(this.weapon.toString() + " ");
		if (this.wing == null)
			temp.append("-1 \n");
		else
			temp.append(this.wing.toString() + " \n");
		return temp.toString();
	}

}