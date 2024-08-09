package engine;
import static org.junit.Assert.fail;

import java.util.*;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NotEnoughActionsException;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Game {
	
	public static Cell [][] map=new Cell[15][15];
	public static ArrayList <Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList <Hero> heroes =  new ArrayList<Hero>();
	public static ArrayList <Zombie> zombies =  new ArrayList<Zombie>();
	
	
		
	public static void loadHeroes(String filePath)  throws IOException {
		
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Hero hero=null;
			switch (content[1]) {
			case "FIGH":
				hero = new Fighter(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			case "MED":  
				hero = new Medic(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3])) ;
				break;
			case "EXP":  
				hero = new Explorer(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			}
			availableHeroes.add(hero);
			line = br.readLine();
			
			
		}
		br.close();
		
	}
	public static void makeadjacentvisible(Hero h){
		int xhero=(int)h.getLocation().getX();
		int yhero =(int)h.getLocation().getY();
		for(int x=xhero-1;x<=xhero+1;x++){ 
			for(int y=yhero-1;y<=yhero+1;y++){
				if(x>=0 && x<=14){
				if(y>=0 && y<=14)
				   map[x][y].setVisible(true);
				
			}
		}
	}
	}

	 public static boolean vaccinerandomallocation(Vaccine v) {
			Random r = new Random();
			int i =r.nextInt(15);
			int j =r.nextInt(15);
			if(map[i][j] instanceof CharacterCell){
				if(((CharacterCell)Game.map[i][j]).getCharacter()==null) {
					CollectibleCell s1 = new CollectibleCell(v);
					Game.map [i][j]=s1;
					return true;
				}
				return false;
			}
			return false;
		}
	 public static  void supplyrandomallocation(Supply s) {
			Random r = new Random();
			int i =r.nextInt(15);
			int j =r.nextInt(15);
			while((Game.map[i][j] instanceof CollectibleCell) 
					|| (Game.map[i][j] instanceof TrapCell) 
					|| (((CharacterCell)Game.map[i][j]).getCharacter()!=null)) {
				i =r.nextInt(15);
				j =r.nextInt(15);
			}
			CollectibleCell s1 = new CollectibleCell(s);
			Game.map [i][j]=s1;
		}
		
	
	 public static void startGame(Hero h)  {
		 for(int i=0;i<map.length;i++){
				for(int j=0;j<map[i].length;j++){
					map[i][j]=new CharacterCell(null);
				}
		 }
		 availableHeroes.remove(h);
			heroes.add(h);
			h.setLocation(new Point(0,0));
			CharacterCell c = new CharacterCell(h);
			map[0][0]=c;
		    for(int i=0; i<5 ;i++) {
			Vaccine v = new Vaccine();
			if(!vaccinerandomallocation(v)){
				i--;
			}
			}
	 
	
		 for(int i=0; i<5 ;i++)  {
			 
			Supply s = new Supply();
			supplyrandomallocation(s);
			
			}
		
		for(int i=0;i<10;i++){
			Zombie z = new Zombie();
			zombierandomallocation(z);
			zombies.add(z);
		}
		
	  for(int i=0; i<5 ;i++) {
		 TrapCell t = new TrapCell();
		 t.traprandomallocation();
 
	 }
	  map[0][0].setVisible(true);
	  makeadjacentvisible(h);
	
		
		}
		public static boolean checkWin() {
			 if(heroes.size()>= 5){
				return checkcollectedandused();
				}
			 else 
				return false; 
			 
			 }
		 public static boolean checkGameOver() {	
			 if(checkcollectedandused()) {
				 return true;
			 }
			 else if( heroes.size()== 0 ){
				 return true; 
			 }
			else
			 return false;
		 }
		 public static boolean checkcollectedandused(){
			 for(int i=0;i<map.length;i++){
					for(int j=0;j<map[i].length;j++){
						if(map[i][j] instanceof CollectibleCell){
							if(((CollectibleCell)map[i][j]).getCollectible() instanceof Vaccine){
								return false;
							}
						}
					}
				}
				for(int i=0;i<heroes.size();i++){
					if(heroes.get(i).getVaccineInventory().size()>0){
						return false;
					}
		 }
				return true;
		
		 }
	
   public static void endTurn() throws InvalidTargetException, NotEnoughActionsException{
	   checkWin();
	   checkGameOver();
	   for(int i=0;i<zombies.size();i++){
		   zombies.get(i).setTarget(null);
	   }
	   for(int i=0;i<zombies.size();i++){
		   zombies.get(i).attack();
	   }
	   for(int i=0;i<zombies.size();i++){
		   zombies.get(i).setTarget(null);
	   }
		for(int h=0;h<heroes.size();h++){
			heroes.get(h).reset();
		}
		Zombie z= new Zombie();
		zombierandomallocation(z);
		zombies.add(z);
		for(int g=0;g<map.length;g++){
			for(int d=0;d<map[g].length;d++){
				if(map[g][d]!=null)
				map[g][d].setVisible(false);
			}
		}
		for(int s=0;s<heroes.size();s++){
			makeadjacentvisible(heroes.get(s));
		}
		
   }
   public static void zombierandomallocation(Zombie z){
	   Random r = new Random();
		int i =r.nextInt(15);
		int j =r.nextInt(15);
		while((Game.map[i][j] instanceof CollectibleCell) || 
				(Game.map[i][j] instanceof TrapCell) || 
				(((CharacterCell)Game.map[i][j]).getCharacter()!=null)) {
			i =r.nextInt(15);
			j =r.nextInt(15);
		}
		z.setLocation(new Point(i,j));
		CharacterCell v1 = new CharacterCell(z);
		Game.map[i][j]=v1;
	}
  
   }

