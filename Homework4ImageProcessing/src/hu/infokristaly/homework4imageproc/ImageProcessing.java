package hu.infokristaly.homework4imageproc;
import java.io.IOException;

public class ImageProcessing {

	public static void main(String[] args) throws IOException {
		ShowImageTask.run();
		RecurseRescaleImages rc = new RecurseRescaleImages("h:/20151007/képek/lehetséges képek","h:/20151007/képek/átméretezett","*.{jpg,jpeg,bmp,png}");
		rc.run();
		
	}

}
