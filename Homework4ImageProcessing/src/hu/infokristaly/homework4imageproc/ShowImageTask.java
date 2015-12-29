package hu.infokristaly.homework4imageproc;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ShowImageTask {

	public static void run() throws IOException {		
		final Image image = ImageProcessorUtils.getImageFromResourceAsStream("images/Bumblebee.jpg", ShowImageTask.class);
		int width = ((BufferedImage)image).getWidth();
		int height = ((BufferedImage)image).getHeight();
		//final BufferedImage scaledImage = ImageProcessorUtils.getScaledImage((BufferedImage)image,640,480);
		final BufferedImage scaledImage = ImageProcessorUtils.getScaledImageWithImgscalr((BufferedImage)image,500,400);
		JFrame f = new JFrame();
		f.setLayout(new BorderLayout());
		
	    ImageJPanel panel = new ImageJPanel();
		panel.setImage(scaledImage);

		JButton b = new JButton("HIDE");
	    b.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e){
	                panel.setVisible(false);
	        }
	    });

		f.setSize(width, height+b.getHeight());
		f.add(panel, BorderLayout.CENTER);
		
	    
	    f.add(b,BorderLayout.SOUTH);
	    //f.pack();
	    f.setVisible(true);
	    f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);

	}
	
	public static BufferedImage drawLine(BufferedImage scaledImage) {
	       IntStream.range(0,100).forEach(i -> {
	            (scaledImage).setRGB(i, 100, 0);
	        });
	       return scaledImage;
	}
}
