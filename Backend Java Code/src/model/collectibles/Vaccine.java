package model.collectibles;

import java.awt.Point;
import java.util.Random;

import model.characters.Hero;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import engine.Game;
import exceptions.NoAvailableResourcesException;



public class Vaccine implements Collectible {

	public Vaccine() {
		
	}
	public void pickUp(Hero h){
		 h.getVaccineInventory().add(this);
	  }
	 public void use(Hero h) throws NoAvailableResourcesException{
		 
		 if(h.getVaccineInventory().size()<=0){
				throw new NoAvailableResourcesException("No enough vaccines.");
			}
		 else{
			h.getVaccineInventory().remove(this);
			int x=(int)h.getTarget().getLocation().getX();
			int y=(int)h.getTarget().getLocation().getY();
			Random r= new Random();
			Game.zombies.remove(h.getTarget());
			int hindex=r.nextInt(Game.availableHeroes.size());
			Hero h1= Game.availableHeroes.get(hindex);
			Point p= new Point(x,y);
			h1.setLocation(p);
			Game.availableHeroes.remove(hindex);
			Game.heroes.add(h1);
		    ((CharacterCell)Game.map[x][y]).setCharacter(h1);
		 }
		 
	 }
	
}
