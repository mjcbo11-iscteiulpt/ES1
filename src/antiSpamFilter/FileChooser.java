package antiSpamFilter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Miguel  Bento n69515 Lei Pl
 *
 * @author Daniel Fernando n72756 Lei Pl
 *
 * @author Joao Nuno n73304
 *
 * @author Ricardo Lopes n73384
 *
 */

public class FileChooser {
	
	protected JFileChooser chooser;
	protected FileNameExtensionFilter filter;
	protected String tipoDeFicheiro ;
	protected String titulo;
	protected String path;
	
	public FileChooser(String tipoDeFicheiro,String titulo) {
		this.tipoDeFicheiro=tipoDeFicheiro;
		this.titulo=titulo;
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter(tipoDeFicheiro, tipoDeFicheiro);		
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle(titulo);		
		int returnVal = chooser.showOpenDialog(null);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	path = chooser.getSelectedFile().getAbsolutePath();		      
		    }else {
		    	System.exit(0);
		    }
	}
	
	public String getPath() {
		return path;
	}
	
	
	
	
	
	

}