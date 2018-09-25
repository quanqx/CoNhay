package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Handler.CharacterHandler;
import Handler.CoordinateHandler;
import Handler.Helper;
import Handler.Movable;
import DAL.Character;
import DAL.Coordinate;
import DAL.Hint;

public class GUI extends JFrame implements MouseListener{
	
	private final static int x = 150;
	private final static int y = 100;
	private final static int width = 500;
	private final static int wCharacter = 50;
	private final static int wHint = 40;
	
	private final static boolean ALLY = true;
//	private final static boolean ENEMY = false;
	
	private boolean player;
	
	private CharacterHandler cHandler;
	
	private ArrayList<Character> allys;
	private ArrayList<Character> enemys;
	private ArrayList<Hint> hints;
	
	private Character cSelected;
	
	public GUI(){
		initProperties();
		
		cHandler = new CharacterHandler();
		
		initCharacter();
		
		this.addMouseListener(this);
		
		this.player = ALLY;
	}
	
	private void initCharacter(){
		allys = cHandler.genCharacter(CharacterHandler.ALLY, wCharacter);
		enemys = cHandler.genCharacter(CharacterHandler.ENEMY, wCharacter);
		hints = new ArrayList<>();
	}
	
	private void initProperties(){
		this.setTitle("Cờ nhảy");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,700);
		this.setLocationRelativeTo(null);
		getContentPane().setBackground(Color.WHITE);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		drawGameBoard(g2);
		
		for(Character c : allys){
			if(c.isSelected()) g2.setColor(Color.YELLOW);
			else g2.setColor(Color.RED);
			if(c.isAlive())
				drawCharacter(g2, c);
		}
		for(Character c : enemys){
			if(c.isSelected()) g2.setColor(Color.YELLOW);
			else g2.setColor(Color.BLUE);
			if(c.isAlive())
				drawCharacter(g2, c);
		}
		
		if(hints.size() != 0){
			g.setColor(Color.PINK);
			for(Hint h : hints){
				drawHint(g2, h);
			}
		}
		
		
	}
	
	private void drawHint(Graphics2D g, Hint h){
		g.drawOval(x - wHint/2 + h.getCoordinate().getX()*width/4, y - wHint/2 + h.getCoordinate().getY()*width/4, wHint, wHint);
		g.fillOval(x - wHint/2 + h.getCoordinate().getX()*width/4, y - wHint/2 + h.getCoordinate().getY()*width/4, wHint, wHint);
	}
	
	private void drawCharacter(Graphics2D g, Character c){
		if(!c.isKing()){
			g.drawOval(x - wCharacter/2 + c.getCoordinate().getX()*width/4, y - wCharacter/2 + c.getCoordinate().getY()*width/4, wCharacter, wCharacter);
			g.fillOval(x - wCharacter/2 + c.getCoordinate().getX()*width/4, y - wCharacter/2 + c.getCoordinate().getY()*width/4, wCharacter, wCharacter);
		}
		else{
			g.drawRect(x - wCharacter/2 + c.getCoordinate().getX()*width/4, y - wCharacter/2 + c.getCoordinate().getY()*width/4, wCharacter, wCharacter);
			g.fillRect(x - wCharacter/2 + c.getCoordinate().getX()*width/4, y - wCharacter/2 + c.getCoordinate().getY()*width/4, wCharacter, wCharacter);
		}
	}
	
	private void drawGameBoard(Graphics2D g2){
		g2.setColor(Color.BLACK);
//		g2.setStroke(new BasicStroke(3));
		g2.drawRect(x, y, width, width);
		g2.drawLine(x, y, x + width, y + width);
		g2.drawLine(x + width, y, x, y + width);
		g2.drawLine(x + width, y, x, y + width);
		g2.drawLine(x, y+width/2, x+width, y+width/2);
		g2.drawLine(x+width/2, y, x+width/2, y+width);
		g2.drawLine(x, y+width/4, x+width, y+width/4);
		g2.drawLine(x+width/4, y, x+width/4, y+width);
		g2.drawLine(x, y+3*width/4, x+width, y+3*width/4);
		g2.drawLine(x+3*width/4, y, x+3*width/4, y+width);
		g2.drawLine(x+width/2, y, x, y + width/2);
		g2.drawLine(x+width, y+width/2, x+width/2, y+width);
		g2.drawLine(x+width/2, y, x+width, y+width/2);
		g2.drawLine(x, y + width/2, x+width/2, y+width);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(move(e)) return;
		onMouseClick(e);
	}
	
	private void onMouseClick(MouseEvent e){
		if(player == ALLY)
			for(Character c : allys){
				characterSelected(e, c);
			}
		else
			for(Character c : enemys){
				characterSelected(e, c);
			}
//		move(e);
	}
	
	private void characterSelected(MouseEvent e, Character c){
		Coordinate temp = CoordinateHandler.getCoordinateOxy(c.getCoordinate(), x, y, width);
		if(Helper.distance(temp, new Coordinate(e.getX(), e.getY())) < wCharacter/2){
			if(cSelected != null) cSelected.setSelected(false);
			cSelected = c;
			c.setSelected(true);
			
			checkHint(c);
			repaint();
		}
	}
	
	private void checkHint(Character c){
		hints = new ArrayList<>();
		if(c.isKing()){
			if(player == ALLY){
				ArrayList<Coordinate> kingAllyEatable = Movable.getKingEatable(c, enemys, allys);// for king ally
				for(Coordinate cdn : kingAllyEatable){
//					if(countAlly<3 && cdn.getX() == 2 && cdn.getY() == 2) continue;
					Hint hint = new Hint(cdn, wHint);
					hints.add(hint);
				}
			}
			else{
				ArrayList<Coordinate> kingEnemyEatable = Movable.getKingEatable(c, allys, enemys);// for king enemy
				for(Coordinate cdn : kingEnemyEatable){
//					if(countEnemy<3 && cdn.getX() == 2 && cdn.getY() == 2) continue;
					Hint hint = new Hint(cdn, wHint);
					hints.add(hint);
				}
			}
		}
		ArrayList<Coordinate> cdns = Movable.getMovable(c);
		
		if(cdns.size() == 0) return;
		for(Coordinate cdn : cdns){
			int count = 0;
			for(Character item : allys){
				if((item.getCoordinate().getX() == cdn.getX() && item.getCoordinate().getY() == cdn.getY())){
					count ++;
					break;
				}
			}
			for(Character item : enemys){
				if((item.getCoordinate().getX() == cdn.getX() && item.getCoordinate().getY() == cdn.getY())){
					count ++;
					break;
				}
			}
			if(count == 0){
				Hint hint = new Hint(cdn, wHint);
				hints.add(hint);
			}
		}
	}
	
	private boolean move(MouseEvent e){
		if(cSelected != null){
			for(Hint h : hints){
				if(Helper.distance(CoordinateHandler.getCoordinateOxy(h.getCoordinate(), x, y, width), new Coordinate(e.getX(), e.getY())) < wHint/2){
					cSelected.setCoordinate(h.getCoordinate());
					if(checkWin()) return true;
					cSelected.setSelected(false);
					if(cSelected.isKing()){
						Character c = cHandler.getCharacterByCoordinate(h.getCoordinate(), allys, enemys);
						if(c != null){
							c.setAlive(false);
							c.setCoordinate(new Coordinate(99999, 99999));
						}
					}
					cSelected = null;
					hints = new ArrayList<>();
					player = !player;
					checkEnclosed();
					repaint();
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkWin(){
		
		if(cHandler.countCharacterDead(allys) == allys.size()-1){
			JOptionPane.showMessageDialog(this, "Bạn đã thua!");
			reset();
			return true;
		}
		
		if(cHandler.countCharacterDead(enemys) == enemys.size()-1){
			JOptionPane.showMessageDialog(this, "Bạn đã thắng!");
			reset();
			return true;
		}
		
		for(int i = 0; i< allys.size(); i++){
			for (int j = 0; j < enemys.size(); j++) {
				if(allys.get(i).isKing() && enemys.get(j).isKing()){
					if(allys.get(i).getCoordinate().getX() == enemys.get(j).getCoordinate().getX() && allys.get(i).getCoordinate().getY() == enemys.get(j).getCoordinate().getY()){
						if(allys.get(i).isSelected()){
							JOptionPane.showMessageDialog(this, "Bạn đã thắng!");
							reset();
							return true;
						}
						if(enemys.get(j).isSelected()){
							JOptionPane.showMessageDialog(this, "Bạn đã thua!");
							reset();
							return true;
						}
					}
				}
			}
			
		}
		return false;
	}
	
	private void checkEnclosed(){
		boolean enclosed = false;
		for(Character ally : allys){
			if(Movable.enclosed(ally, enemys)){
				ally.setAlive(false);
				ally.setCoordinate(new Coordinate(99999,99999));
				enclosed = true;
				if(ally.isKing()){
					JOptionPane.showMessageDialog(this, "Bạn đã thua!");
					reset();
					return;
				}
			}
		}
		for(Character enemy : enemys){
			if(Movable.enclosed(enemy, allys)){
				enemy.setAlive(false);
				enemy.setCoordinate(new Coordinate(99999,99999));
				enclosed = true;
				if(enemy.isKing()){
					JOptionPane.showMessageDialog(this, "Bạn đã thắng!");
					reset();
					return;
				}
			}
		}
		
		if(enclosed) repaint();
		
	}
	
	private void reset(){
		hints = new ArrayList<>();
		initCharacter();
		player = ALLY;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
