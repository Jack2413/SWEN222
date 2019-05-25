package character;
import java.util.HashMap;
import java.util.Map;

/**
 * A magic temple that boosts player's stats, note the stats will be boosted for free,
 * but player can only choose from one of them and after used the temple will no longer be active.
 * @author stella
 *
 */
public class Temple {
	//a variety of stats that the temple can boost, with the amount it boosts.
	//for example ("health", 10) means increase player's health by 10
	private Map<String, Integer> boosts;
	//the temple can only be used once and after that it will no longer be active
	private boolean isActive;

	public Temple() {
		initialize();
	}

	//==================================== initialize ======================================================
	/**
	 * random health ,damage and defence
	 */
	private void initialize(){
		boosts = new HashMap<>();
		isActive = true;
		boosts.put("health", (int)(Math.random() * 200 + 200));
		boosts.put("damage", (int)(Math.random() * 10 + 3));
		boosts.put("defence", (int)(Math.random() * 10 + 5));
	}

	//==================================== use method ======================================================
	/**
	 * Player chooses one stat to boost.
	 * @param stats
	 * @param player
	 */
	public void boost(String stats, Player player) {
		if(stats.equals("health")) {
			player.setHealth(player.getHealth() + boosts.get(stats));
		}else if(stats.equals("damage")) {
			player.setDamage(player.getDamage() + boosts.get(stats));
		}else if(stats.equals("defence")) {
			player.setDefence(player.getDefence() + boosts.get(stats));
		}

		//temple is no longer active
		isActive = false;
	}

	//==================================== get method ======================================================
	public boolean isActive() {
		return isActive;
	}
	public Map<String, Integer> getBoosts(){
		return boosts;
	}
}
