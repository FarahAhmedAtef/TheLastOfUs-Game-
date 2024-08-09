package model.characters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import engine.*;


public abstract class Hero extends Character {
	

		private int actionsAvailable;
		private int maxActions;
		private ArrayList<Vaccine> vaccineInventory;
		private ArrayList<Supply> supplyInventory;
		private boolean specialAction;
	
		
		public Hero(String name,int maxHp, int attackDmg, int maxActions) {
			super(name,maxHp, attackDmg);
			this.maxActions = maxActions;
			this.actionsAvailable = maxActions;
			this.vaccineInventory = new ArrayList<Vaccine>();
			this.supplyInventory=new ArrayList<Supply>();
			this.specialAction=false;
		
		}

	
	


		public boolean isSpecialAction() {
			return specialAction;
		}



		public void setSpecialAction(boolean specialAction) {
			this.specialAction = specialAction;
		}



		public int getActionsAvailable() {
			return actionsAvailable;
		}



		public void setActionsAvailable(int actionsAvailable) {
			this.actionsAvailable = actionsAvailable;
		}



		public int getMaxActions() {
			return maxActions;
		}



		public ArrayList<Vaccine> getVaccineInventory() {
			return vaccineInventory;
		}


		public ArrayList<Supply> getSupplyInventory() {
			return supplyInventory;
		}
    public void attack() throws InvalidTargetException, NotEnoughActionsException{
    if(this instanceof Fighter && this.isSpecialAction()==true){
    	super.attack();
    }
    else{
    	if(this.actionsAvailable<=0){
    		throw new NotEnoughActionsException("There is no enough action points.");
    	}
    	else{
    	super.attack();
    	int x=getActionsAvailable();
		this.setActionsAvailable(x-1);
    	}	
    }
    }
    public void onCharacterDeath(){
    	super.onCharacterDeath();
    	Game.heroes.remove(this);
    }
    public void reset(){
    	this.actionsAvailable=this.maxActions;
    	this.setTarget(null);
    	this.setSpecialAction(false);
    }
    public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException{
    	
    		if(this.supplyInventory.size()>0){
    			this.supplyInventory.get(this.supplyInventory.size()-1).use(this);
    			this.setSpecialAction(true);
    		}
    		else{
    			throw new NoAvailableResourcesException("No enough supply.");
    		}
    	
  
    }
    public void helpermove(int x, int y,int xhero, int yhero ) throws MovementException{
    	if(Game.map[xhero][yhero] instanceof CharacterCell){
			CharacterCell c=(CharacterCell)Game.map[xhero][yhero];
			if(c.getCharacter()!=null){
				throw new MovementException("There is a character in this cell");
			}
		}
		else if(Game.map[xhero][yhero] instanceof CollectibleCell){
			CollectibleCell c=(CollectibleCell)Game.map[xhero][yhero];
			c.getCollectible().pickUp(this);
			
		}
		else if (Game.map[xhero][yhero] instanceof TrapCell){
			int t=((TrapCell)Game.map[xhero][yhero]).getTrapDamage();
			this.setCurrentHp(this.getCurrentHp()-t);
			if(this.getCurrentHp()<=0){
				this.onCharacterDeath();
				Game.map[x][y]=new CharacterCell(null);
				Game.map[xhero][yhero]=new CharacterCell(null);
				return;
			}
			
		}
		this.setLocation(new Point(xhero,yhero));
		Game.map[x][y]=new CharacterCell(null);
		CharacterCell c2= new CharacterCell(this);
		Game.map[xhero][yhero]=c2;
		Game.map[xhero][yhero].setVisible(true);
		Game.makeadjacentvisible(this);
		this.actionsAvailable--;
    }
		
		
public void move(Direction d) throws MovementException, NotEnoughActionsException{
if(this.getCurrentHp()>0){
	if(this.actionsAvailable!=0){
	int xhero=(int)this.getLocation().getX();
	int yhero=(int)this.getLocation().getY();
	int x=xhero;
	int y=yhero;
if((d.equals(Direction.RIGHT)&& yhero==14) || (d.equals(Direction.UP)&& xhero==14) ||(d.equals(Direction.DOWN)&& xhero==0)|| (d.equals(Direction.LEFT)&& yhero==0)){
			throw new MovementException("you are moving out of the map.");
	}	
	
else if (d.equals(Direction.RIGHT)){
	yhero++;
	
}
else if (d.equals(Direction.LEFT)){
	yhero--;
}
else if (d.equals(Direction.UP)){
	xhero++;
}
else if (d.equals(Direction.DOWN)){
	xhero--;
}
        helpermove(x,y,xhero,yhero);
	
	
}
	else{
		throw new NotEnoughActionsException("There are no enough action points.");
	}
}
else{
	this.onCharacterDeath();
}
}
public void cure() throws InvalidTargetException,NotEnoughActionsException,NoAvailableResourcesException{

	if(this.getTarget()!=null){
	if(((this.getTarget() instanceof Fighter) ||(this.getTarget() instanceof Medic)
			||(this.getTarget() instanceof Explorer)) || !checkadjacency()){
		throw new InvalidTargetException("Can not cure a hero or target is not adjacent.");
	}
	else{
		if(this.getActionsAvailable()==0){
			throw new NotEnoughActionsException("No enough action points.");
		}
		else{
			this.getVaccineInventory().get(this.getVaccineInventory().size()-1).use(this);
		    this.actionsAvailable--;
		}
	}
}
	else{
		throw new InvalidTargetException("There is no target.");
	}
}
}
