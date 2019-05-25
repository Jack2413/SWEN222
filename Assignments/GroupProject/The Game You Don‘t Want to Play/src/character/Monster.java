
package character;
import commonPackage.usefor.test.MockMonster;

public class Monster implements MockMonster{
	private int health, damage, defence, drop, level;
	private final double factor = 0.7;
	private boolean isDefeated;
	// the potential drop of coins when the monster dies

	public Monster(int level) {
		initialize(level);
	}

	// ==================================== initialize
	// ========================================================
	private void initialize(int level) {
		this.level = level;
		this.isDefeated = false;
		this.health = (int) (20 * (level) * factor);
		this.damage = (int) (11 + (level + 9) * factor );
		this.defence = (int) (1 + (level + 9) * factor);
		// generates a random number of coins drop when monster is defeated
		this.drop = (int) (Math.random() * 10 * (level + 1));

	}

	public void defeated(Player player) {
		isDefeated = true;
		player.setGold(player.getGold() + drop);
	}

	// ==================================== get method
	// ========================================================
	public int getDamage() {
		return damage;
	}

	public int getDefence() {
		return defence;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	public int getDrop() {
		return drop;
	}
	public boolean isDefeated() {
		return isDefeated;
	}

	public double getFactor() {
		return factor;
	}

	public String toString() {
		return String.valueOf(this.level);
	}

	public String getName() {
		return "9" + String.valueOf(this.level);
	}

	
}
