package model.characters;

import java.awt.Point;
import java.util.Random;

import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;




public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;

	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException{
		int xhero=(int)this.getLocation().getX();
		int yhero =(int)this.getLocation().getY();
		boolean flag=false;
		for(int x=xhero-1;x<=xhero+1;x++){ 
			for(int y=yhero-1;y<=yhero+1;y++){
				if(x>=0 && x<=14){
					if(y>=0 && y<=14){
						if(Game.map[x][y] instanceof CharacterCell){
							if(((CharacterCell)Game.map[x][y]).getCharacter() instanceof Fighter ||
									((CharacterCell)Game.map[x][y]).getCharacter() instanceof Medic || 
									((CharacterCell)Game.map[x][y]).getCharacter() instanceof Explorer){
								
								this.setTarget(((CharacterCell)Game.map[x][y]).getCharacter());
								flag=true;
								break;
							}
						}
					}

				}
			}
			if(flag) break;
		}
		if(this.getTarget()!=null){
			super.attack();
		}
	}

	public void onCharacterDeath(){
		super.onCharacterDeath();
		Game.zombies.remove(this);
		Zombie z= new Zombie();
		Game.zombierandomallocation(z);
		Game.zombies.add(z);

	}


}