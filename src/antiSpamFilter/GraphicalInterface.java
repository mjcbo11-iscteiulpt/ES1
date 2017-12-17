package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.uma.jmetal.solution.DoubleSolution;


public class GraphicalInterface{
	
	JFrame frame ;
	JPanel painelDeColunas ;
	JPanel painelDeColunasNaoEditaveis ;
	JPanel painelDeCaminhos;
	JPanel painelDeBotoes ;
	JTabbedPane painelDePaineis ;
	JPanel painelCF ;
	JTextField caminhoRules ;
	JLabel labelCaminhoRules ;
	JPanel painelSpam ;
	JLabel labelCaminhoSpam ;
	JTextField caminhoSpam ;
	JPanel painelHam ;
	JLabel labelCaminhoHam;
	JTextField caminhoHam ;
	JButton calculo ;		
	JLabel labelFP ;
	JLabel labelFN;
	JButton gerar ;
	DefaultListModel<String> listaDeRegras ;
	DefaultListModel<String> listaDePesos ;
	DefaultListModel<String> listaDePesosNaoEditaveis ;
	DefaultTableModel model;
	JTable TabelaPesosEditaveis;
	JTable TabelaPesosNaoEditaveis;	
	ArrayList<String[]> listaDeHam;
	ArrayList<String[]> listaDeSpam;
	AntiSpamFilterProblem JMetal;	
	Boolean inicio=true;
	
	
	public GraphicalInterface() {		
		AbrirInterface();
		AdicionarListeners();		
		CarregarFicheiro(caminhoHam.getText(),listaDeHam);
		CarregarFicheiro(caminhoSpam.getText(),listaDeSpam);
		
	}
	
		
	private void AbrirInterface() {
		criarFrame();
		criarPaineis();
		criarBotoes();
		criarLabels();
		criarTextFields();
		criarListasModelo();
		adicionarListasModelo();
		adicionarCoisas();
		setVisible();
	}
	
	
	private void criarFrame() {
		frame = new JFrame("AntiSpamFilterForProfessionalMailbox");		
	}
	
	
	private void criarPaineis() {
		painelDeColunas = new JPanel();
		painelDeColunasNaoEditaveis = new JPanel();
		painelDeCaminhos = new JPanel();
		painelDeBotoes = new JPanel();		
		painelDePaineis = new JTabbedPane();
		painelCF = new JPanel();
		painelSpam = new JPanel();
		painelHam = new JPanel();		
		definirLayouts();		
	}
	
	
	private void criarBotoes() {
		calculo = new JButton("Calcular");		
		gerar = new JButton("Gerar");

	}
	
	
	private void criarLabels() {
		labelCaminhoRules = new JLabel("Caminho rules file =>");
		labelCaminhoSpam = new JLabel("Caminho Spam file =>");
		labelCaminhoHam = new JLabel("Caminho Ham file =>");
		labelFP = new JLabel("Label FP");
		labelFN = new JLabel("Label FN");
	}
	
	
	private void criarTextFields() {
		caminhoRules = new JTextField("rules.cf");
		caminhoSpam = new JTextField("spam.log");
		caminhoHam = new JTextField("ham.log");

	}
	
	
	
	
	private void criarListasModelo() {
		System.out.println("criar Listas Modelo");
		//criar lista de Regras
		listaDeRegras = new DefaultListModel();
				
		if(inicio) {
		//criar lista de Pesos
		listaDePesos = new DefaultListModel();
		}
		//criar lista de Pesos Nao Editaveis
		listaDePesosNaoEditaveis = new DefaultListModel();
		
		//Passar para a lista o que está no ficheiro
		ReadFile(caminhoRules.getText(),inicio);
		inicio=false;

	}
	
	
	private void adicionarListasModelo() {
		System.out.println("adicionar Listas Modelo");
		//criar vetores que vao guardar regras e pesos
		String[] pesos = new String[listaDePesos.getSize()];
		String[] regras = new String[listaDeRegras.getSize()];
		String[] pesosNaoEditaveis = new String[listaDePesosNaoEditaveis.getSize()];
		
		//atribuir aos vetores as regras e pesos
		for(int i = 0; i< listaDePesos.getSize();i++) {
			pesos[i]=String.valueOf(listaDePesos.getElementAt(i));
			regras[i]=String.valueOf(listaDeRegras.getElementAt(i));
			pesosNaoEditaveis[i]=String.valueOf(listaDePesosNaoEditaveis.getElementAt(i));
		}
		
		//criar matriz com os 2 vetores
		String[][] matrizPreTabela = new String[pesos.length][2];
		String[][] matrizPreTabelaNaoEditavel = new String[pesosNaoEditaveis.length][2];
		for(int i = 0; i< pesos.length;i++) {
			matrizPreTabela[i][0]=regras[i];
			matrizPreTabela[i][1]=pesos[i];
			matrizPreTabelaNaoEditavel[i][0]=regras[i];
			matrizPreTabelaNaoEditavel[i][1]=pesosNaoEditaveis[i];
		}
		
		// criar vetor com os nomes das colunas da TableModel
		Object[] nome = {"Regras","Pesos"};
		
		//criar TableModel com pesos editáveis
		model = new DefaultTableModel(matrizPreTabela,nome) {
			boolean[] canEdit = new boolean[]{
                    false,true
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
		};		
		//criar JTable		
		TabelaPesosEditaveis = new JTable(model);
		//adicionar a cor verde ao fundo da JTable
		TabelaPesosEditaveis.setBackground(new Color(165,255,165));
		
		//criar segunda JTable para pesos nao editaveis
		TabelaPesosNaoEditaveis = new JTable(matrizPreTabelaNaoEditavel,nome);
		TabelaPesosNaoEditaveis.setDefaultEditor(Object.class,null);
		//adicionar cor vermelha ao fundo
		TabelaPesosNaoEditaveis.setBackground(new Color(255,183,183));
		
	}
	
	
	private void adicionarCoisas() {
		painelDePaineis.add("Pesos Editáveis", painelDeColunas);
		painelDePaineis.add("Pesos Não Editaveis", painelDeColunasNaoEditaveis);		
		painelCF.add(labelCaminhoRules);
		painelCF.add(caminhoRules);
		painelSpam.add(labelCaminhoSpam);
		painelSpam.add(caminhoSpam);
		painelHam.add(labelCaminhoHam);
		painelHam.add(caminhoHam);
		painelDeCaminhos.add(painelCF);
		painelDeCaminhos.add(painelSpam);
		painelDeCaminhos.add(painelHam);
		painelDeBotoes.add(calculo);
		painelDeBotoes.add(labelFP);
		painelDeBotoes.add(labelFN);
		painelDeBotoes.add(gerar);
		frame.add(painelDePaineis,BorderLayout.CENTER);
		frame.add(painelDeCaminhos, BorderLayout.NORTH);
		frame.add(painelDeBotoes, BorderLayout.SOUTH);
		//adicionar JTable ao painel
		painelDeColunasNaoEditaveis.add(new JScrollPane(TabelaPesosNaoEditaveis));
		//adicionar JTable ao painel 
		painelDeColunas.add(new JScrollPane(TabelaPesosEditaveis));	
		listaDeHam = new ArrayList<String[]>();
		listaDeSpam = new ArrayList<String[]>();
	}
	
	
	private void setVisible() {
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setVisible(true);
		
	}

	private void AdicionarListeners() {
		textFieldListeners();
		labelsListener();
		gerarListener();
		JTableListener();
		
	}
	
	
	private void textFieldListeners() {
		caminhoRulesListener();				
	}
	
	
	private void labelsListener() {
		calculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				labelFN.setText("FN = "+Calcular(listaDeRegras,listaDePesos,caminhoHam.getText()));
				labelFP.setText("FP = "+Calcular(listaDeRegras,listaDePesos,caminhoSpam.getText()));				
			}
		});		
	}
	
	
	
	
	
	private void gerarListener() {
		gerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				JMetal = new AntiSpamFilterProblem();
			}
		});	
	}
	
	
	private void JTableListener() {
		TabelaPesosEditaveis.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				System.out.println("Entrou");
				int row=TabelaPesosEditaveis.getSelectedRow();
				String value=(String)TabelaPesosEditaveis.getValueAt(row, 1);
				listaDePesos.set(row, value);
			}
		});
	}
	
	
	private void CarregarFicheiro(String file,ArrayList<String[]> lista) {
		
		FileReader fr = null;
        BufferedReader br = null;          
        try { 
            fr = new FileReader (file);
            br = new BufferedReader(fr);           
            String line;
            String[] splitted; 
            String[] splittedWithoutFirst;
            while((line=br.readLine())!=null) {
            	splitted = line.split("\\s+");
            	splittedWithoutFirst = Arrays.copyOfRange(splitted, 1, splitted.length);
                lista.add(splittedWithoutFirst);                  
                }} catch(Exception e) {            
                	e.printStackTrace();
                } finally {
                    try{
                        if( null != fr ) {
                            fr.close();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }         
        
        }
	
	
	protected void adicionarNaInterface() {
		System.out.println("Adicionar na Interface");
		//adicionar JTable ao painel
		painelDeColunasNaoEditaveis.removeAll();
		painelDeColunasNaoEditaveis.add(new JScrollPane(TabelaPesosNaoEditaveis));
		//adicionar JTable ao painel 
		painelDeColunas.removeAll();
		painelDeColunas.add(new JScrollPane(TabelaPesosEditaveis));
		frame.revalidate();
		frame.repaint();
		
	}

	
	

	
	protected int Calcular(DefaultListModel<String> listaDeRegras2, DefaultListModel<String> listaDePesos2, String ficheiro) {		
		FileReader fr = null;
        BufferedReader br = null;
        int Falsos=0;        
        try {            
            fr = new FileReader (ficheiro);
            br = new BufferedReader(fr);            
            
            String line;
            String[] splitted;
            List<String> lista;
            double total=0;
            while((line=br.readLine())!=null) {
                splitted = line.split("\\s+");
                lista= new LinkedList<String>(Arrays.asList(splitted));
                lista.remove(0);
                for(String regra:lista) {
                	for(int i=0 ; i<listaDeRegras.getSize();i++) {
                		if(regra.equals(listaDeRegras.getElementAt(i))) {
                			if(painelDePaineis.getSelectedIndex()==0) {
                				total+=Double.parseDouble((String) listaDePesos.getElementAt(i));
                			}else {
                				total+=Double.parseDouble((String) listaDePesosNaoEditaveis.getElementAt(i));
                			}
                		}
                	}                	
                }
                if(ficheiro.equals(caminhoHam.getText())) {
	                if(total>5.0) {
	                	Falsos++;
	                	System.out.println("mais um ponto "+total);
	                }
                }else if(ficheiro.equals(caminhoSpam.getText())) {
                	if(total<5.0) {
	                	Falsos++;
	                	System.out.println("mais um ponto "+total);
	                }
                }
                total=0;
            }                  
        } catch(Exception e) {            
        	e.printStackTrace();
        } finally {
            try{
                if( null != fr ) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		return Falsos;
	}
	
	

	
	private void caminhoRulesListener() {
		caminhoRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Caminho CF mudado para "+"\""+caminhoRules.getText()+"\"");	
				inicio=true;
				criarListasModelo();
				adicionarListasModelo();
				adicionarNaInterface();
			}
		});		
	}

	
	private void ReadFile( String ficheiro, Boolean inicio) {
		FileReader fr = null;
        BufferedReader br = null;
        listaDeRegras.removeAllElements();
        try {
        	labelFP.setText("Label FP");
    		labelFN.setText("Label FN");
            fr = new FileReader (ficheiro);
            br = new BufferedReader(fr);            
            
            String line;
            String[] lineSPlitted;
            while((line=br.readLine())!=null) {
            	lineSPlitted=line.split("\\s+");	            	
                listaDeRegras.addElement(lineSPlitted[0]); 
                if(inicio==true) {
	                if(lineSPlitted.length>1) {
	                listaDePesos.addElement(lineSPlitted[1]);
	                listaDePesosNaoEditaveis.addElement(lineSPlitted[1]);
	                }else {
	                	listaDePesos.addElement("0");
	                	listaDePesosNaoEditaveis.addElement("0");
	                }
                }else {
	                listaDePesosNaoEditaveis.addElement(lineSPlitted[1]); 

                }
            }            
        } catch(Exception e) {
            listaDeRegras.removeAllElements();
            listaDeRegras.addElement("Este caminho não Existe!!!");
            listaDePesos.addElement(" ");
            listaDePesosNaoEditaveis.addElement(" ");
        } finally {
            try{
                if( null != fr ) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

	}

	
	private void definirLayouts() {
		frame.setLayout(new BorderLayout());
		painelDeCaminhos.setLayout(new GridLayout(1,3,20,10));
		painelDeBotoes.setLayout(new FlowLayout(FlowLayout.CENTER,60,20));
		painelDeColunas.setLayout(new GridLayout(1,1));
		painelDeColunasNaoEditaveis.setLayout(new GridLayout(1,1));		
	}

	
	
		

	
	public static void main(String [] args){
		GraphicalInterface interfaceGrafica = new GraphicalInterface();
	}
	
}