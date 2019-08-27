import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;

/**Class used to store images of doilies.
 * Contains methods to save and delete doily images. 
 * @author Suyash
 *
 */

public class Gallery extends JPanel{
	
	ArrayList<JLabel> gallery;	//arraylist containing doily images
	
	//constructor method
	public Gallery(){
        super();
        gallery = new ArrayList<JLabel>();
        setLayout(new GridLayout(4, 3));
        setVisible(true);   
    }
	
	//getter method for gallery arraylist containing doilies
    public ArrayList<JLabel> getGallery() {
		return gallery;
	}
    
    //method to delete doily images from gallery arraylist
    public void deleteGallery(int index) {
		gallery.remove(index);
		remove(index);
	}
    
    //method to add doily images to gallery arraylist
    public void addGallery(JLabel image){
    	gallery.add(image);
    	add(image);
    }
    
    public void clearGallery() {
    	gallery.removeAll(gallery);
    	removeAll();
    }
}
