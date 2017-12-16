package antiSpamFilter;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;


import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



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
		
	
	private void caminhoRulesListener() {
		caminhoRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Caminho CF mudado para "+"\""+caminhoRules.getText()+"\"");					
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