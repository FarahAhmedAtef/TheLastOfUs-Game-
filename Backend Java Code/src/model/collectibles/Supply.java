package model.collectibles;

import java.util.Random;

import engine.Game;
import exceptions.NoAvailableResourcesException;
import model.characters.Hero;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;



public class Supply implements Collectible  {

	

	
	public Supply() {
		
	}
  public void pickUp(Hero h){
	 h.getSupplyInventory().add(this);
	 
	 
  }
	 public void use(Hero h) throws NoAvailableResourcesException{
		 
		 if(h.getSupplyInventory().size()<=0){
				throw new NoAvailableResourcesException("No enough supply");
			}
		 else{
			h.getSupplyInventory().remove(this);
		 }
		 
		 
		 
	 }
	
		
		

}
