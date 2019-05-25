package Board;

import character.*;
import commonPackage.usefor.test.*;
import item.*;

/**
 * Ground is one of the most important entity which could contain one of 
 * item,shop,temple or monster.
 * @author tian
 *
 * @param <T>
 */
public class Ground<T> extends Entity{
	private T t;
	public Ground(int code, int x, int y, int size) {
		super(code, x, y, size);
		setGround();
	}

	//===================================== Return ================================================
	/**
	 * Is it lava?
	 * if code name as 01 then it is lava
	 * @return
	 */
	public boolean isLava(){
		if(super.Code == 01) return true;
		return false;
	}

	/**
	 * Return what is this ground contain.
	 * T    - I have a fairy sticks, make me smaller, make me larger and make me prettier.
	 * Shop - you walk by, pass by, but should not miss it! 
	 * @return
	 */
	public T getWhatContain(){return this.t;}

	/**
	 * Could player Move? 
	 * if code name as 00 then mean it is empty ground then player could move
	 * @return
	 */
	public boolean couldMove(){
		if(super.Code == 00) return true;
		return false;
	}

	/**
	 * Pick item then set this become normal ground
	 * @return
	 */
	public boolean pickItem(){
		if(this.t != null && this.t instanceof Item) return SetContainNothing();
		return false;
	}	
	
	/**
	 * When you replace item then need drop item on board. For example,unique item weapon, armor
	 * @param i
	 * @return
	 */
	public boolean setItem(MockWearableItem i){
		if(i != null){
			super.Code = Integer.valueOf(i.getName());
			System.out.println(super.Code);
			setGround();
			return true;
		}
		return false;
	}

	/**
	 * When we beat monster then we need clean the battleground set it to normal ground
	 * @return
	 */
	public boolean cleanBattleground(){
		if(this.t != null && this.t instanceof Monster) return SetContainNothing();

		return false;
	}	

	/**
	 * Please accept my sincerest and deepest apology, Sorry for we are closed.
	 * @return
	 */
	public boolean closeShop(){
		if(this.t != null && this.t instanceof MockShop) return SetContainNothing();

		return false;
	}

	/**
	 *  Myth is Myth, you cannot have good luck every day.
	 * @return
	 */
	public boolean closeTemple(){
		if(this.t != null && this.t instanceof MockTemple) return SetContainNothing();

		return false;
	}

	/**
	 * Your time is over, this time belongs to me!
	 * @return
	 */
	public boolean killWiseMan(){
		if(this.t != null && this.t instanceof WiseMan) return SetContainNothing();

		return false;
	}



	/**
	 * Help method for reset this ground to normal ground
	 * @return
	 */
	public boolean SetContainNothing(){
		this.t = null;
		super.Code = 0;
		return true;
	}

	// ==================================Initialize====================================================
	/**
	 * Set this ground except Contain Player
	 * 00 - nothing  
	 * 01 - lava
	 * 03 - contain wise man type0      - W0
	 * 04 - contain wise man type1      - W1
	 * 05 - contain wise man type2      - W2
	 * 20 - GoldDoor - Gold key  - 30
	 * 21 - CyanDoor - Cyan key  - 31
	 * 22 - Bornze D - Bronze K  - 32
	 * 23 - Purplye  - Purplye   - 33
	 * 24 - Silver D - Sliver K  - 34

	 * 40 - Big BloodVial               - BB
	 * 41 - Small BloodVial             - SB
	 * 43 - Bomb                        - BM
	 * 48 - Fate Coin
	 * 49 - MonsterManual

     * 60 - Shop Type 0
	 * 65 - Temple Type 0 
	 * 66 - Temple Type 1
	 * 67 - Temple Type 2
	 * 
	 * 70 - 0-Armor                     - AR	 
	 * 71 - 1-Armor
	 * 72 - 2-Armor
	 * 73 - 0-Weapon                    - WP	 
	 * 74 - 1-Weapon
	 * 75 - 2-Weapon
	 * 76 - 0-wing                      - WG
	 * 77 - 1-Wing
	 * 78 - 2-Wing
	 * 
	 
	 * 91 - M1  92 - M2 93 - M3 94 - M4 95 - M5 96 - M6 97 - M7 98 - M8
	 */ @SuppressWarnings({"unchecked"})
	 private  void setGround() {
		 if(super.Code == 03) this.t = (T) new WiseMan(new Armor(-1,-1,2));
		 else if(super.Code == 04) this.t = (T) new WiseMan(new Weapon(-1,-1,2));
		 else if(super.Code == 05) this.t = (T) new WiseMan(new Wing(-1,-1,2));
		 else if(super.Code == 06) this.t = (T) new WiseMan(new MonsterManual());

		 else if(super.Code == 30) this.t = (T) new Key(super.PosX,super.PosY, "gold");
		 else if(super.Code == 31) this.t = (T) new Key(super.PosX,super.PosY, "cyan");
		 else if(super.Code == 32) this.t = (T) new Key(super.PosX,super.PosY, "bronze");
		 else if(super.Code == 33) this.t = (T) new Key(super.PosX,super.PosY, "purple");
		 else if(super.Code == 34) this.t = (T) new Key(super.PosX,super.PosY, "silver");

		 else if(super.Code == 40) this.t = (T) new BloodVial(super.PosX,super.PosY,"big");
		 else if(super.Code == 41) this.t = (T) new BloodVial(super.PosX,super.PosY,"small");
		 else if(super.Code == 43) this.t = (T) new Bomb(super.PosX,super.PosY);
		 else if(super.Code == 48) this.t = (T) new FateCoin(super.PosX,super.PosY);
		 //else if(super.Code == 60) this.t = (T) new Shop();
		 else if(super.Code>=70&&super.Code<=72) this.t = (T) new Armor(super.PosX,super.PosY,super.Code-70);
		 else if(super.Code>=73&&super.Code<=75) this.t = (T) new Weapon(super.PosX,super.PosY,super.Code-73);
		 else if(super.Code>=76&&super.Code<=78) this.t = (T) new Wing(super.PosX,super.PosY,super.Code-76);
		 else if(super.Code>=91&&super.Code<=98) this.t = (T) new Monster(super.Code-90);    

	 }
	 
	 
	 /**
	  * Cause we want two pieces point to one shop or Temple then we need this method.
	  * @param s
	  * @param code
	  * @return
	  */
	 public void setShopOrTemple(T t){
		 assert t!=null;
		 this.t = t;
	}



	 //================================= Test ============================================================


}
