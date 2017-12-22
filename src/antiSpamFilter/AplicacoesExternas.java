package antiSpamFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

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

public class AplicacoesExternas {
	
	public AplicacoesExternas() {
		PdfLatex();
		HvBoxplotR();
	}

	public void PdfLatex() {		
		try {
			ProcessBuilder builder = new ProcessBuilder("pdflatex","AntiSpamStudy.tex");
			builder.directory( new File( "experimentBaseDirectory/AntiSpamStudy/latex" ).getAbsoluteFile() ); // this is where you set the root folder for the executable to run with
			builder.redirectErrorStream(true);
				Process process =  builder.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}

	public void HvBoxplotR(){	
		try {
		ProcessBuilder builder = new ProcessBuilder( "Rscript","HV.Boxplot.R");
		builder.directory( new File( "experimentBaseDirectory/AntiSpamStudy/R" ).getAbsoluteFile() ); // this is where you set the root folder for the executable to run with
		builder.redirectErrorStream(true);
			Process process =  builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
		
	}
	
