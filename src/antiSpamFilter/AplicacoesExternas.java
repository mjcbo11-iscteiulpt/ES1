package antiSpamFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;


public class AplicacoesExternas {
	
	public AplicacoesExternas() {
//		PdfLatex();
		HvBoxplotR();
	}
	
	private void PdfLatex() {
		Process process;
		try {
			process = new ProcessBuilder(
					"pdflatex","experimentBaseDirectory/AntiSpamStudy/latex/AntiSpamStudy.tex").start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	private void HvBoxplotR(){	
		try {
			Runtime.getRuntime().exec("Rscript experimentBaseDirectory/AntiSpamStudy/R/HV.Boxplot.R");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		//		Process process;
//		try {
//
//			process = new ProcessBuilder(
//					"Rscript","HV.Boxplot.R").start();
//			InputStream is = process.getInputStream();
//			InputStreamReader isr = new InputStreamReader(is);
//			BufferedReader br = new BufferedReader(isr);
//			String line;
//			while ((line = br.readLine()) != null) {
//				System.out.println(line);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		}
	}
	

