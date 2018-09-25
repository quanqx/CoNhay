package Handler;

import java.util.ArrayList;

import DAL.Coordinate;
import DAL.Character;

public class Movable {
	
	public static ArrayList<Coordinate> getMovable(Character character){
		ArrayList<Coordinate> result = new ArrayList<>();
		int x = character.getCoordinate().getX();
		int y = character.getCoordinate().getY();
		if((x+y)%2 == 0){
			if(x-1 >= 0 && y-1 >= 0)
				result.add(new Coordinate(x-1, y-1));
			if(x>=0 && y-1 >=0)
				result.add(new Coordinate(x, y-1));
			if(x+1 <= 4 && y-1 >= 0)
				result.add(new Coordinate(x+1, y-1));
			if(x+1 <= 4 && y >= 0)
				result.add(new Coordinate(x+1, y));
			if(x+1 <= 4 && y+1 <= 4)
				result.add(new Coordinate(x+1, y+1));
			if(x >= 0 && y+1 <= 4)
				result.add(new Coordinate(x, y+1));
			if(x-1 >= 0 && y+1 <= 4)
				result.add(new Coordinate(x-1, y+1));
			if(x-1 >= 0 && y >= 0)
				result.add(new Coordinate(x-1, y));
		}
		else{
			if(x>=0&&y-1>=0)
				result.add(new Coordinate(x, y-1));
			if(x+1<=4 && y>=0)
				result.add(new Coordinate(x+1, y));
			if(x-1>=0  && y>=0)
				result.add(new Coordinate(x-1,y));
			if(x>=0 && y+1<=4)
				result.add(new Coordinate(x, y+1));
		}
		
		return result;
	}
	
	public static ArrayList<Coordinate> getKingEatable(Character king, ArrayList<Character> enemys, ArrayList<Character> allys){
		ArrayList<Coordinate> result = new ArrayList<>();
		
		int x = king.getCoordinate().getX();
		int y = king.getCoordinate().getY();
		
		ArrayList<Coordinate> movable = getMovable(king);
		for(Coordinate c : movable){
			for(Character ally : allys){
				if(c.getX() == ally.getCoordinate().getX() && c.getY() == ally.getCoordinate().getY()){
					Coordinate eatable = new Coordinate((c.getX()-x)*2 + x, (c.getY()-y)*2 + y);
					for(Character enemy : enemys){
						if(eatable.getX() == enemy.getCoordinate().getX() && eatable.getY() == enemy.getCoordinate().getY()){
							result.add(eatable);
						}
					}
				}
			}
		}
		
		return result;
	}
	
	public static boolean enclosed(Character c, ArrayList<Character> characters){
		int count = 0;
		ArrayList<Coordinate> movable = getMovable(c);
		for(Character item : characters){
			for(Coordinate coordinate : movable){
				if(item.getCoordinate().getX() == coordinate.getX() && item.getCoordinate().getY() == coordinate.getY()){
					count ++;
				}
			}
		}
		if(count == movable.size()) return true;
		return false;
	}
}
