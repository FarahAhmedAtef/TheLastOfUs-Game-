package model.world;

import java.util.Random;

import engine.Game;

public class TrapCell extends Cell {

	private int trapDamage;
	
	public TrapCell() {
		int x [] = {10,20,30};
 		Random r = new Random();
		int result = r.nextInt(3);
		trapDamage = x[result];
		
		
	}

	public int getTrapDamage() {
		return trapDamage;
	}
	

	public void traprandomallocation() {
		Random r = new Random();
		int i =r.nextInt(15);
		int j =r.nextInt(15);
		while((Game.map[i][j] instanceof CollectibleCell) 
				|| (Game.map[i][j] instanceof TrapCell) 
				|| (((CharacterCell)Game.map[i][j]).getCharacter()!=null)) {
			i =r.nextInt(15);
			j =r.nextInt(15);
		}
		Game.map [i][j]=this;
	}
}
