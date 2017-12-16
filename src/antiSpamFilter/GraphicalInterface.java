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
	Boolean inicio=true;
	
	
	public GraphicalInterface() {		
		AbrirInterface();
		AdicionarListeners();		
		
		
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
		
		
		inicio=false;

	}
	
	
	private void adicionarListasModelo() {
		System.out.println("adicionar Listas Modelo");
		//criar vetores que vao guardar regras e pesos
		String[] pesos = new String[335];
		String[] regras = new String[335];
		String[] pesosNaoEditaveis = new String[335];
		
		//atribuir aos vetores as regras e pesos
		for(int i = 0; i< listaDePesos.getSize();i++) {
			pesos[i]=String.valueOf(listaDePesos.getElementAt(i));
			regras[i]=String.valueOf(listaDeRegras.getElementAt(i));
			pesosNaoEditaveis[i]=String.valueOf(listaDePesosNaoEditaveis.getElementAt(i));
		}
		
		//criar matriz com os 2 vetores
		String[][] matrizPr�Tabela = new String[pesos.length][2];
		String[][] matrizPr�TabelaNaoEditavel = new String[pesosNaoEditaveis.length][2];
		for(int i = 0; i< pesos.length;i++) {
			matrizPr�Tabela[i][0]=regras[i];
			matrizPr�Tabela[i][1]=pesos[i];
			matrizPr�TabelaNaoEditavel[i][0]=regras[i];
			matrizPr�TabelaNaoEditavel[i][1]=pesosNaoEditaveis[i];
		}
		
		// criar vetor com os nomes das colunas da TableModel
		Object[] nome = {"Regras","Pesos"};
		
		//criar TableModel com pesos edit�veis
		model = new DefaultTableModel(matrizPr�Tabela,nome) {
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
		TabelaPesosNaoEditaveis = new JTable(matrizPr�TabelaNaoEditavel,nome);
		TabelaPesosNaoEditaveis.setDefaultEditor(Object.class,null);
		//adicionar cor vermelha ao fundo
		TabelaPesosNaoEditaveis.setBackground(new Color(255,183,183));
		
	}
	
	
	private void adicionarCoisas() {
		painelDePaineis.add("Pesos Edit�veis", painelDeColunas);
		painelDePaineis.add("Pesos N�o Editaveis", painelDeColunasNaoEditaveis);		
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
	}
	
	
	private void textFieldListeners() {
		caminhoRulesListener();				
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
