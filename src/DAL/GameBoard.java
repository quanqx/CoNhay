package DAL;

public class GameBoard {
	
	private int startX;
	private int startY;
	private int width;
	private Coordinate[] coordinates;
	
	public GameBoard(){}
	
	public GameBoard(int startX, int startY, int width, Coordinate[] coordinates) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.coordinates = coordinates;
	}
	public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public Coordinate[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinate[] coordinates) {
		this.coordinates = coordinates;
	}
	
	
}
