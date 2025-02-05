package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;


public class Explorer extends Hero {
	

	public Explorer(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}

	
	
	public void makemapvisible(){
		for(int i=0;i<Game.map.length;i++) {
			for(int j=0;j<Game.map[i].length;j++) {
				Game.map[i][j].setVisible(true);
			}
		}
	}

	public void useSpecial() throws NotEnoughActionsException, NoAvailableResourcesException, InvalidTargetException{
		if(this.getActionsAvailable()<=0){
			throw new NotEnoughActionsException("There are no enough action points.");
		}
		else{
			if(this.isSpecialAction()==false){
				super.useSpecial();
			}
			this.makemapvisible();
			int x=getActionsAvailable();
			this.setActionsAvailable(x-1);
		}
	}
}
