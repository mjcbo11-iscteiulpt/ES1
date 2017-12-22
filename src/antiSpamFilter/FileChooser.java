package antiSpamFilter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
	
	JFileChooser chooser;
	FileNameExtensionFilter filter;
	
	public FileChooser(String tipoDeFicheiro,String titulo) {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter(tipoDeFicheiro, tipoDeFicheiro);		
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle(titulo);
		int returnVal = chooser.showOpenDialog(null);
//		    if(returnVal == JFileChooser.APPROVE_OPTION) {
//		       System.out.println("You chose to open this file: " +chooser.getSelectedFile().getName());
//		       System.out.println(chooser.getSelectedFile());
//		    }
	}
	
	public String getPath() {
		//System.out.println(chooser.getSelectedFile().getAbsolutePath());
		return chooser.getSelectedFile().getAbsolutePath();
	}
	
	
	
	
	
	

}
