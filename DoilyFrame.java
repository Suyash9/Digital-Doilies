import java.awt.*;
import java.util.*;
import javax.swing.*;


/**Class extending JFrame which contains various buttons and panels.
 * @author Suyash
 *
 */

class DoilyFrame extends JFrame {
	
	//method containing panels and content pane
	public void start() {
		//naming and creating new frame
		JFrame window = new JFrame("Drawing Doilies");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//creating a display panel and adding initial arraylist of points
		DisplayPanel dPanel = new DisplayPanel();
		dPanel.getPointsArray().add(new ArrayList<Point>());
						
		//creating a control panel
		ControlPanel cPanel = new ControlPanel();
		
		//button to change background colour
		JButton backgButton = new JButton("Background Colour");
		backgButton.addActionListener(cPanel.new ColourListener(dPanel, "background"));
	    
		//button to change sector colour
	    JButton sectorButton = new JButton("Sector Colour");
	    sectorButton.addActionListener(cPanel.new ColourListener(dPanel, "sector"));
	    
	    //button to change pen colour
	    JButton paintButton = new JButton("Pen Colour");
	    paintButton.addActionListener(cPanel.new ColourListener(dPanel, "draw"));
	    
	    //toggle button to erase drawings
	  	JToggleButton eraseButton = new JToggleButton("Eraser");
	  	eraseButton.addItemListener(cPanel.new ToggleListener(dPanel, "e"));
	  		
	    //slider to select pen size
	    JLabel sizeLabel = new JLabel("Pen Size:");
	    JSlider penSizes = new JSlider(JSlider.HORIZONTAL, 2, 72, 12);
	    penSizes.addChangeListener(cPanel.new PenSizeListener(dPanel));
	    
		//panel containing colour buttons, labels, eraser and slider for pen size
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(new FlowLayout());
		selectionPanel.add(backgButton);
	    selectionPanel.add(sectorButton);
	    selectionPanel.add(paintButton);
	    selectionPanel.add(eraseButton);
	    selectionPanel.add(sizeLabel);
	    selectionPanel.add(penSizes);
		
	    //button to clear panel
		JButton clear = new JButton("Clear");
		clear.addActionListener(cPanel.new ClearListener(dPanel, dPanel.getPointsArray(), dPanel.getPointsArrayCopy(), "c"));
		
		//button to undo last drawing in panel
		JButton undo = new JButton("Undo");
		undo.addActionListener(cPanel.new ClearListener(dPanel, dPanel.getPointsArray(), dPanel.getPointsArrayCopy(), "u"));
		
		//button to redo last drawing in panel
		JButton redo = new JButton("Redo");
		redo.addActionListener(cPanel.new ClearListener(dPanel, dPanel.getPointsArray(), dPanel.getPointsArrayCopy(), "r"));
		
	    //toggle button to reflect draw
	    JToggleButton reflectButton = new JToggleButton("Reflect");
	    reflectButton.addItemListener(cPanel.new ToggleListener(dPanel, "r"));
	    
		//toggle button to toggle between showing sectors
		JToggleButton toggle = new JToggleButton("Toggle Sectors");
		toggle.addItemListener(cPanel.new ToggleListener(dPanel, "s"));
		
	    //spinner to select number of sectors
	    JLabel sectorsLabel = new JLabel("Number of sectors");
	    SpinnerModel sectorNum = new SpinnerNumberModel(6, 1, 1000, 1);
		JSpinner sectorSpinner = new JSpinner(sectorNum);
		sectorSpinner.addChangeListener(cPanel.new SectorListener(dPanel));
			
		//control panel containing clear, undo , redo, toggle sector view buttons and spinner for number of sectors
		cPanel.add(clear);
		cPanel.add(undo);
		cPanel.add(redo);
		cPanel.add(reflectButton);
		cPanel.add(toggle);
		cPanel.add(sectorsLabel);
	    cPanel.add(sectorSpinner);
		cPanel.setLayout(new FlowLayout());

		//main panel used for drawing, containing all panels used for drawing
		JPanel DrawingPanel = new JPanel(); 
		DrawingPanel.setLayout(new BorderLayout());
		DrawingPanel.add(dPanel, BorderLayout.CENTER);
		DrawingPanel.add(cPanel, BorderLayout.SOUTH);
		DrawingPanel.add(selectionPanel, BorderLayout.NORTH);

		//creating a gallery and setting its preferred size
		Gallery gallery = new Gallery();
		
		//button to save doily to gallery
		JButton save = new JButton("Save Doily");
		save.addActionListener(cPanel.new GalleryListener(gallery, dPanel, "save"));
		
		//button to delete doily from gallery
		JButton del = new JButton("Delete Doily");
		del.addActionListener(cPanel.new GalleryListener(gallery, dPanel, "delete"));
		
		//button to clear doily gallery
		JButton clearg = new JButton("Clear Gallery");
		clearg.addActionListener(cPanel.new GalleryListener(gallery, dPanel, "clear"));
		
		//panel containing buttons for gallery panel
		JPanel gPanel = new JPanel();
		gPanel.add(save);
		gPanel.add(del);
		gPanel.add(clearg);
		
		//naming the gallery panel
		JLabel gLabel = new JLabel("GALLERY", SwingConstants.CENTER);
		gLabel.setFont(new Font("Comic Sans", Font.PLAIN, 26));
		
		//main panel used for gallery functions, containing all panels used for the gallery
		JPanel GalleryPanel = new JPanel();
		GalleryPanel.setLayout(new BorderLayout());
		GalleryPanel.add(gLabel, BorderLayout.NORTH);
		GalleryPanel.add(gallery, BorderLayout.CENTER);
		GalleryPanel.add(gPanel, BorderLayout.SOUTH);
		GalleryPanel.setPreferredSize(new Dimension(350, 350));
		
		//adding panels and setting layout of content pane
		Container pane = window.getContentPane();
		pane.setLayout(new BorderLayout());
		pane.add(DrawingPanel, BorderLayout.CENTER);
		pane.add(GalleryPanel, BorderLayout.WEST);
		
		window.setMinimumSize(new Dimension(1000, 650));
		window.setLocationRelativeTo(null);
		window.setResizable(false);
	    window.setVisible(true);
	    window.pack();
	}
}