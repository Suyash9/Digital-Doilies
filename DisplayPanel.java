import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.JPanel;

/**Class extending JPanel used to draw and display the doily drawings.
 * Contains paintComponent and other methods and variables used for drawing.
 * @author Suyash
 *
 */

class DisplayPanel extends JPanel {
	
	//member variables
	private Color sectorColour = Color.WHITE;	//colour of sector
	private Color drawColour = Color.YELLOW;	//colour of drawing
	private Color backgColour = Color.BLACK;	//colour of background
	
	private Integer penSize = 12;				//size of pen used for drawing and eraser
	private Integer sectorNum = 6;				//number of sectors
	private final Point center = new Point(365, 270);	//coordinates of center
	
	private Boolean toggleSector = true;		//toggle view of sector lines
	private Boolean toggleReflect = false;		//toggle reflection of drawing
	private Boolean toggleEraser = false;		//toggle between pen and eraser for eraser
	
	private ArrayList<Point> points = new ArrayList<Point>();		//arraylist of points used to draw
	private ArrayList<ArrayList<Point>> pointsArray = new ArrayList<ArrayList<Point>>();		//arraylist containing arraylist of points	
	private ArrayList<ArrayList<Point>> pointsArrayCopy = new ArrayList<ArrayList<Point>>();	//copy of arraylist containg arraylist of points
	private ArrayList<Color> colours = new ArrayList<Color>();	//arraylist of colour used for drawing
	private ArrayList<Integer> penSizes = new ArrayList<Integer>();		//arraylist of integer pen sizes used for drawing
	
	//constructor method
	public DisplayPanel() {
		this.setBackground(backgColour);
		this.addMouseMotionListener(new PaintListener(this));
		this.addMouseListener(new PaintListener(this));
		this.addMouseMotionListener(new EraseListener(this, pointsArray));
	}
	
	//method to draw sector lines and doily
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		double sectorAngle = 360.0/sectorNum;
		
		//drawing sector lines
		g2d.translate(center.x, center.y);
		if (toggleSector) {
			for (int i = 0; i < sectorNum; i++) {
				g2d.setColor(sectorColour);
				g2d.drawLine(0, 0, 0, -250);
				g2d.rotate(Math.toRadians(sectorAngle));
			}
		}
		
		//drawing doily
		g2d.rotate(Math.toRadians(360 / sectorNum), 0, 0);
		for (int j = 1; j <= sectorNum; j++) {
			g2d.rotate(Math.toRadians(sectorAngle));
			if (!pointsArray.isEmpty()) {	
				for (int i = 1; i < pointsArray.size(); i++) {
					for (Point p : pointsArray.get(i)) {
						g.setColor(colours.get(i-1));
						int size = penSizes.get(i-1);
						
						int x = (int) p.getX() - center.x;
						int y = (int) p.getY() - center.y;
						
						g.fillOval(x, y, size, size);
						if (toggleReflect) {
							g.fillOval(-x, y, size, size);
						}
					}
					
					if (!toggleEraser) {
						for (Point p : points) {
							int x = (int) p.getX() - center.x;
							int y = (int) p.getY() - center.y;
							g.fillOval(x, y, penSize, penSize);
							if (toggleReflect) {
								g.fillOval(-x, y, penSize, penSize);
							}
						}
					} 
				}				
			}		
		}	
	}	
	
	//listener to implement drawings of doily 
	class PaintListener extends MouseAdapter{
			
		DisplayPanel dPanel;
		
		public PaintListener(DisplayPanel dPanel){
			this.dPanel = dPanel;
		}
		
		//mouse listener methods to implement drawing of the doily
		public void mouseDragged(MouseEvent e) {
			if (!pointsArray.isEmpty()) {
				points.add(e.getPoint());
				dPanel.repaint();
			}
		}
		
		public void mouseReleased(MouseEvent e) {
			if (!toggleEraser) {
				penSizes.add(penSize);
				colours.add(drawColour);
				pointsArray.add(points);
			}
			dPanel.points = new ArrayList<Point>();
			dPanel.repaint();
		}
	}
	
	//listener to implement eraser to erase the drawing
	class EraseListener extends MouseAdapter {
				
		DisplayPanel dPanel;
		ArrayList<ArrayList<Point>> list = new ArrayList<ArrayList<Point>>();
			
		public EraseListener(DisplayPanel dPanel, ArrayList<ArrayList<Point>> list) {
			this.dPanel = dPanel;
			this.list = list;
		}
		
		//mouse dragged method to implement eraser
		public void mouseDragged(MouseEvent e) {
			int x1 = e.getX();
			int x2 = e.getX() + penSize;
			int y1 = e.getY();
			int y2 = e.getY() + penSize;		
			
			for (ArrayList<Point> points : list) {
				ArrayList<Point> removePoints = new ArrayList<Point>();
				removePoints.addAll(points);
				for (Point p : removePoints) {
					if (p.getX() >= x1 && p.getX() <= x2 && p.getY() >= y1 && p.getY() <= y2) {
						points.remove(p);
					}
				}
			}
		}
	}
		
	//getter method for doily screenshot
	public BufferedImage getScreenShot(JPanel panel) {
		BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		panel.paint(image.getGraphics());
		return image;
	}
	
	//getter method for number of sectors
	public Integer getSectorNumber() {
		return sectorNum;
	}

	//setter method for number of sectors
	public void setSectorNumber (Integer sectorNum) {
		this.sectorNum = sectorNum;
	}
	
	//getter method for colour of sector
	public Color getSectorColour() {
		return sectorColour;
	}
	
	//setter method for colour of sector
	public void setSectorColour (Color sectorColour) {
		this.sectorColour = sectorColour;
	}
	
	//getter method for colour of pen used for drawing
	public Color getDrawColour() {
		return drawColour;
	}
	
	//setter method for colour of pen used for drawing
	public void setDrawColour (Color drawColour) {
		this.drawColour = drawColour;
	}
	
	//getter method for size of pen used for drawing
	public Integer getPenSize() {
		return penSize;
	}
	
	//setter method for size of pen used for drawing
	public void setPenSize (Integer penSize) {
		this.penSize = penSize;
	}
	
	//getter method for boolean used to show sector lines
	public boolean getToggleSector() {
		return toggleSector;
	}

	//setter method for boolean used to show sector lines
	public void setToggleSector(boolean toggleSector) {
		this.toggleSector = toggleSector;
	}
	
	//getter method for boolean used to toggle reflection of drawing
	public Boolean getToggleReflect() {
		return toggleReflect;
	}

	//setter method for boolean used to toggle reflection of drawing
	public void setToggleReflect(Boolean toggleReflect) {
		this.toggleReflect = toggleReflect;
	}
	
	//getter method for boolean used to toggle between eraser and pen
	public Boolean getToggleEraser() {
		return toggleEraser;
	}
	
	//setter method for boolean used to toggle between eraser and pen
	public void setToggleEraser(Boolean toggleEraser) {
		this.toggleEraser = toggleEraser;
	}
		
	//getter method which returns arraylist of points
	public ArrayList<Point> getPoints(){
		return points;
	}
	
	//getter method which returns arraylist of arraylist of points
	public ArrayList<ArrayList<Point>> getPointsArray(){
		return pointsArray;
	}
	
	//getter method which returns copy of arraylist of arraylist of points
	public ArrayList<ArrayList<Point>> getPointsArrayCopy(){
		return pointsArrayCopy;
	}
	
	//getter method which returns arraylist of colours used for drawing
	public ArrayList<Color> getColourList(){
		return colours;
	}
	
	//getter method which returns arraylist of integer pen sizes used for drawing
	public ArrayList<Integer> getPenSizeList(){
		return penSizes;
	}
}