package model.characters;

import java.awt.Point;

import model.collectibles.Supply;
import model.world.CharacterCell;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;


public class Fighter extends Hero{

	
	public Fighter(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}

		
	
	
	
	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException{
	  if(this.isSpecialAction()==false){
		 super.useSpecial();
	  }
	 
	}
	
}


