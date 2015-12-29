package hu.infokristaly.homework4imageproc;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImageJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7808174120282505064L;

	private Image image;
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
