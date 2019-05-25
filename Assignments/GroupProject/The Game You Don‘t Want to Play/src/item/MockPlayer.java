package item;

import commonPackage.usefor.test.RealPlayer;

public class MockPlayer implements RealPlayer{
	private int health, damage, defence;
	private int gold = 0;
	private int speed = 1;
	@Override
	public void setHealth(int health) {
		this.health = health;
	}
	@Override
	public int getHealth() {
		return health;
	}
	@Override
	public void setDamage(int damage) {
		this.damage = damage;
	}
	@Override
	public int getDamage() {
		return damage;
	}
	@Override
	public void setDefence(int defence) {
		this.defence = defence;
	}
	@Override
	public int getDefence() {
		return defence;
	}
	@Override
	public void setGold(int gold) {
		this.gold = gold;
	}
	@Override
	public int getGold() {
		return gold;
	}
	@Override
	public int getSpeed() {
		return speed;
	}
	@Override
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
