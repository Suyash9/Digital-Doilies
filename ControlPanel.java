import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**Class extending JPanel which contains listeners and methods which control various aspects of the doily drawings.
 * @author Suyash
 *
 */

class ControlPanel extends JPanel {
	
	//listener to clear, undo and redo panel
	class ClearListener implements ActionListener{
		
		DisplayPanel dPanel;
		ArrayList<ArrayList<Point>> pointsArray;
		ArrayList<ArrayList<Point>> pointsArrayCopy;
		String str;
		
		public ClearListener(DisplayPanel dPanel, ArrayList<ArrayList<Point>> pointsArray, ArrayList<ArrayList<Point>> pointsArrayCopy, 
				String str) {
			this.dPanel = dPanel;
			this.pointsArray = pointsArray;
			this.pointsArrayCopy = pointsArrayCopy;
			this.str = str;
		}
		
		//action performed method to clear, undo and redo
		public void actionPerformed (ActionEvent e) {
			if (str.equals("c")) {
				pointsArray.clear();
				pointsArrayCopy.clear();
				dPanel.getColourList().add(dPanel.getDrawColour());
				dPanel.getColourList().clear();
				dPanel.getPenSizeList().add(dPanel.getPenSize());
				dPanel.getPenSizeList().clear();
				dPanel.getPointsArray().add(new ArrayList<Point>());
			} else if (str.equals("u")) {
				if(!pointsArray.isEmpty()) {
					pointsArrayCopy.add(pointsArray.get(pointsArray.size()-1));
					pointsArray.remove(pointsArray.size()-1);
				}
			} else if (str.equals("r")) {
				if(!pointsArrayCopy.isEmpty()) {
					pointsArray.add(pointsArrayCopy.get(pointsArrayCopy.size()-1));
					pointsArrayCopy.remove(pointsArrayCopy.size()-1);
				}
			}
			dPanel.repaint();
		}
	}
	
	//listener to implement pen size
	class PenSizeListener implements ChangeListener{
		
		DisplayPanel dPanel;
		
		public PenSizeListener(DisplayPanel dPanel) {
			this.dPanel = dPanel;
		}
		
		//state changed method which changes size of pen
		public void stateChanged(ChangeEvent e) {
			JSlider slider = (JSlider) e.getSource();
			dPanel.setPenSize(slider.getValue());
			dPanel.repaint();
		}	
	}
    
    //listener to implement toggle boolean for showing of sector lines, reflection, and eraser
	class ToggleListener implements ItemListener{
		
		DisplayPanel dPanel;
		String str;
		
		public ToggleListener(DisplayPanel dPanel, String str) {
			this.dPanel = dPanel;
			this.str = str;
		}
		
		//state changed method to toggle showing of sector lines
		public void itemStateChanged(ItemEvent e) {
			if (str.equals("s")) {
				if(e.getStateChange() == ItemEvent.DESELECTED){
				    dPanel.setToggleSector(true);
				} else if (e.getStateChange() == ItemEvent.SELECTED){
					dPanel.setToggleSector(false);
				}
			} else if(str.equals("r")) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					dPanel.setToggleReflect(false);			
				} else if (e.getStateChange() == ItemEvent.SELECTED) {
					dPanel.setToggleReflect(true);	
				}
			} else if (str.equals("e")) {
				if(e.getStateChange() == ItemEvent.DESELECTED){
					dPanel.setToggleEraser(false);
				} else if (e.getStateChange() == ItemEvent.SELECTED){
				    dPanel.setToggleEraser(true);
				}
			}
			dPanel.repaint();
		}
	}
	
	//listener to implement selection of colour of background, sector and drawing
	public class ColourListener implements ActionListener{
		
		DisplayPanel dPanel;
		String str;

		public ColourListener(DisplayPanel dPanel, String str) {
			this.dPanel = dPanel;
			this.str = str;
		}
		
		//action performed method used to change colour of background, sector and drawing
		public void actionPerformed(ActionEvent e) {
			DoilyFrame window = new DoilyFrame();
			
			if (str.equals("background")) {
				Color backgColour = JColorChooser.showDialog(window, "Change Background Colour", dPanel.getBackground());
				if (backgColour != null){
					dPanel.setBackground(backgColour);
				}
			} else if (str.equals("sector")) {
				Color sectorColour = JColorChooser.showDialog(window, "Change Sector Colour", dPanel.getSectorColour());
				if (sectorColour != null) {
					dPanel.setSectorColour(sectorColour);					
				}
			} else if (str.equals("draw")) {
				Color drawColour = JColorChooser.showDialog(window, "Change Drawing Colour", dPanel.getDrawColour());
				if (drawColour != null) {
					dPanel.setDrawColour(drawColour);
				}
			}
		}
	}
	
	//listener to implement number of sectors
	class SectorListener implements ChangeListener{
		
		DisplayPanel dPanel;
		
		public SectorListener(DisplayPanel dPanel) {
			this.dPanel = dPanel;
		}
		
		//state changed method to select number of sectors
		public void stateChanged(ChangeEvent e) {
			JSpinner temp = (JSpinner) e.getSource();
			dPanel.setSectorNumber((Integer) temp.getValue());
			dPanel.repaint();
		}
	}
	
	//listener to implement gallery to save and delete doilies
	class GalleryListener implements ActionListener{
		
		Gallery g;
		DisplayPanel dPanel;
		String str;
		
		public GalleryListener (Gallery g, DisplayPanel dPanel, String str) {
			this.g = g;
			this.dPanel = dPanel;
			this.str = str;
		}
		
		//action performed method to save and delete doilies
		public void actionPerformed(ActionEvent e) {
			if(str.equals("save")) {
				if (g.gallery.size() < 12) {
					BufferedImage temp = dPanel.getScreenShot(dPanel);
					BufferedImage image = resize(temp, 100, 100);
					g.addGallery(new JLabel(new ImageIcon(image)));
					g.revalidate();
				} else {
					JOptionPane.showMessageDialog(null, "Maximum of 12 doilies can be saved.");
				}
			} else if(str.equals("delete")) {
				String doilyIndex = JOptionPane.showInputDialog(null, "Enter the number of the doily that you want to delete : ", "Deleted saved Doily", 1);;
				if(doilyIndex!=null) {
					try {
						int indexValue = Integer.parseInt(doilyIndex);
						if (indexValue <= g.gallery.size()) {
							g.deleteGallery(indexValue-1);
							g.revalidate();
						} else if(g.gallery.size()==0){
							JOptionPane.showMessageDialog(null, "The gallery is empty.");
						}
					}catch (Exception ex){
						JOptionPane.showMessageDialog(null, "There is no such doily.");
					}
				}
				g.repaint();
			} else if(str.equals("clear")) {
				g.clearGallery();
				g.revalidate();
				g.repaint();
			}
		}
	}

	//method to resize doily image saved to gallery
	public static BufferedImage resize(BufferedImage image, int width, int height) {
		BufferedImage saved = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) saved.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(image, 0, 0, width, height, null);
		g2d.dispose();
		return saved;
	}
}