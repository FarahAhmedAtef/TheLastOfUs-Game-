package model.characters;

import java.awt.Point;

import model.collectibles.Supply;
import model.world.CharacterCell;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import engine.*;

public abstract class Character {
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;

	public Character() {
	}

	public Character(String name, int maxHp, int attackDmg) {
		this.name=name;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
		this.attackDmg = attackDmg;
	}

	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if(currentHp < 0) 
			this.currentHp = 0;
		else if(currentHp > maxHp) 
			this.currentHp = maxHp;
		else 
			this.currentHp = currentHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}
	public boolean checkadjacency(){
		int xhero=(int)this.getLocation().getX();
		int yhero =(int)this.getLocation().getY();
		int xtarget=(int)this.getTarget().getLocation().getX();//11
		int ytarget=(int)this.getTarget().getLocation().getY();//11
		for(int x=xhero-1;x<=xhero+1;x++){ 
			for(int y=yhero-1;y<=yhero+1;y++){
				if(x>=0 && x<=14){
					if(y>=0 && y<=14)
						if(xtarget==x && ytarget==y){
							return true;
						}
				}
			}
		}
		return false;
	}

	public void attack() throws InvalidTargetException,NotEnoughActionsException{
		if(this.target!=null){
			if((this instanceof Zombie && this.target instanceof Zombie) || 
					(!(this instanceof Zombie) && !(this.target instanceof Zombie)) 
					|| checkadjacency()==false ){
				throw new InvalidTargetException("Can not attack this target or target is not adjacent.");
			}
			else{
				this.target.currentHp=this.target.currentHp-this.attackDmg;
				if(this.target.currentHp >0){
					this.target.defend(this);
				}
				else{
					this.target.defend(this);
					this.target.currentHp=0;
					this.target.onCharacterDeath();
				}

			}
		}
		else{
			throw new InvalidTargetException("There is no target.");
		}
	}


	public void defend(Character c){
		this.setTarget(c);
		c.setCurrentHp(c.getCurrentHp()-(this.attackDmg/2));
		if(c.currentHp<=0){
			c.currentHp=0;
			c.onCharacterDeath();
		}

	}
	public void onCharacterDeath(){

		int a=(int)this.location.getX();
		int b=(int)this.location.getY();
		Game.map[a][b]=new CharacterCell(null);

	}

}
