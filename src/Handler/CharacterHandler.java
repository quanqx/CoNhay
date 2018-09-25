package Handler;

import java.util.ArrayList;

import DAL.Coordinate;
import DAL.Character;

public class CharacterHandler {
	
	public final static String ALLY = "ALLY";
	public final static String ENEMY = "ENEMY";
	
	public ArrayList<Character> genCharacter(String type, int widthCharacter){
		ArrayList<Character> result = new ArrayList<>();
		
		for(int i = 0; i < 5; i++){
			if(type == this.ALLY && i < 3) continue;
			if(type == this.ENEMY && i > 1) break;
			for (int j = 0; j < 5; j++) {
				if((i == 1 || i == 3) && j == 2) continue;
				Coordinate coordinate = new Coordinate(j, i);
				Character character = new Character(coordinate, widthCharacter, true, false, false);
				if((i == 0 || i == 4) & j == 2) character.setKing(true);
				result.add(character);
			}
		}
		
		return result;
	}
	
	public ArrayList<Character> characterSelected(Coordinate cdn, ArrayList<Character> characters, int widthCharacter, int startX, int startY, int widthGameBoard){
		for(Character c : characters){
			Coordinate temp = CoordinateHandler.getCoordinateOxy(c.getCoordinate(), startX, startY, widthGameBoard);
			if(Helper.distance(new Coordinate(temp.getX(), temp.getY()), cdn) <= widthCharacter/2){
				c.setSelected(true);
				break;
			}
		}
		return characters;
	}
	
	public Character getCharacterByCoordinate(Coordinate coordinate, ArrayList<Character> allys, ArrayList<Character> enemys){// != king
		Character result = null;
		for(int i = 0; i<allys.size(); i++){
			if(allys.get(i).getCoordinate().getX() == coordinate.getX() && allys.get(i).getCoordinate().getY() == coordinate.getY() && !allys.get(i).isKing()){
				result = allys.get(i);
				break;
			}
			if(enemys.get(i).getCoordinate().getX() == coordinate.getX() && enemys.get(i).getCoordinate().getY() == coordinate.getY() && !enemys.get(i).isKing()){
				result = enemys.get(i);
				break;
			}
		}
		return result;
	}
	
	public int countCharacterDead(ArrayList<Character> characters){
		int count = 0;
		for(Character c : characters){
			if(!c.isAlive()) count ++;
		}
		return count;
	}
	
}
