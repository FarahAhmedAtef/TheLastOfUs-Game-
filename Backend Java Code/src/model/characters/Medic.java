package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;



public class Medic extends Hero {
	//Heal amount  attribute - quiz idea
	

	public Medic(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
		
	}
	

	public void useSpecial() throws InvalidTargetException, NotEnoughActionsException, NoAvailableResourcesException{
    if(this.getTarget()!=null)
		if((this.getTarget() instanceof Zombie) || !checkadjacency() ){
			throw new InvalidTargetException("Can not heal a zombie or target is not adjacent.");
		}
		else{
			if(this.getActionsAvailable()<=0){
				throw new NotEnoughActionsException("No enough action points");
			}
			else{
				super.useSpecial();
				this.getTarget().setCurrentHp(this.getTarget().getMaxHp());
				int x=getActionsAvailable();
				this.setActionsAvailable(x-1);
			}
		}
    else{
    	throw new InvalidTargetException("There is no target.");
    }
	}
}
