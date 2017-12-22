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


public class GraphicalInterface{
	
	protected JFrame frame ;
	protected JPanel painelDeColunas ;
	protected JPanel painelDeColunasNaoEditaveis ;
	protected JPanel painelDeCaminhos;
	protected JPanel painelDeBotoes ;
	protected JTabbedPane painelDePaineis ;
	protected JTextField caminhoRules ;
	protected JLabel labelCaminhoRules ;
	protected JLabel labelCaminhoSpam ;
	protected JTextField caminhoSpam ;	
	protected JLabel labelCaminhoHam;
	protected JTextField caminhoHam ;
	protected JButton calculo ;		
	protected JLabel labelFP ;
	protected JLabel labelFN;
	protected JButton gerar ;
	protected JButton save;
	protected DefaultListModel<String> listaDeRegras ;
	protected DefaultListModel<String> listaDePesos ;
	protected DefaultListModel<String> listaDePesosNaoEditaveis ;
	protected DefaultTableModel model;
	protected JTable TabelaPesosEditaveis;
	protected JTable TabelaPesosNaoEditaveis;	
	protected ArrayList<String[]> listaDeHam;
	protected ArrayList<String[]> listaDeSpam;
	protected AntiSpamFilterAutomaticConfiguration JMetal;	
	protected Boolean inicio=true;
	protected AplicacoesExternas aplicacoes;
	protected FileChooser chooser;
	
	/**
	 * Construtor que vai abrir a interface grafica
	 * adicionar os listeners e carregar os ficheiros Spam e Ham para 
	 * as JTextFields
	 * 	 
	 */	
	public GraphicalInterface() {
		EscolherFicheiros();
		AbrirInterface();
		AdicionarListeners();		
		CarregarFicheiro(caminhoHam.getText(),listaDeHam);
		CarregarFicheiro(caminhoSpam.getText(),listaDeSpam);
		
	}
	
	/**
	 * Metodo que cria as TextFields e os FileChoosers
	 */
	private void EscolherFicheiros() {
		criarTextFields();
		chooser = new FileChooser("cf","Selecione o ficheiro rules");
		caminhoRules.setText(chooser.getPath());
		chooser = new FileChooser("log","Selecione o ficheiro Ham");
		caminhoHam.setText(chooser.getPath());
		chooser = new FileChooser("log","Selecione o ficheiro Spam");
		caminhoSpam.setText(chooser.getPath());

	}

	/**
	 * Metodo que cria a Frame, os Paineis, botões,Labels,TextFields e ListasModelo.	 * 
	 */	
	private void AbrirInterface() {
		criarFrame();
		criarPaineis();
		criarBotoes();
		criarLabels();
		criarListasModelo();
		adicionarListasModelo();
		adicionarCoisas();
		setVisible();
	}
	
	/**
	 * Metodo que inicializa a JFrame
	 */
	private void criarFrame() {
		frame = new JFrame("AntiSpamFilterForProfessionalMailbox");		
	}
	
	/**
	 * Metodo que inicializa os JPanels da interface e define os layouts
	 */
	private void criarPaineis() {
		painelDeColunas = new JPanel();
		painelDeColunasNaoEditaveis = new JPanel();
		painelDeCaminhos = new JPanel();
		painelDeBotoes = new JPanel();		
		painelDePaineis = new JTabbedPane();				
		definirLayouts();		
	}
	
	/**
	 * Metodo que inicializa os 3 Botões da interface.
	 */
	private void criarBotoes() {
		calculo = new JButton("Calcular");		
		gerar = new JButton("Gerar");
		save = new JButton("SAVE");

	}
	
	/**
	 * Metodo que inicializa as Lebels da interface.
	 */
	private void criarLabels() {
		labelCaminhoRules = new JLabel("Caminho rules file ");
		labelCaminhoSpam = new JLabel("Caminho Spam file ");
		labelCaminhoHam = new JLabel("Caminho Ham file ");
		labelFP = new JLabel("Label FP");
		labelFN = new JLabel("Label FN");
	}
	
	/**
	 * Metodo que inicializa as JTextFields com os caminhos para os 3 ficheiros
	 * e verifica se os mesmos existem.
	 */
	private void criarTextFields() {
		caminhoRules = new JTextField();
		caminhoSpam = new JTextField();
		caminhoHam = new JTextField();
	}
	
	/**
	 * Metodo que verifica se o caminho dos 3 ficheiros e valido e preenche com cor 
	 * verde ou vermelha.
	 */
	private void VerificarFicheiros() {
		File file = new File(caminhoRules.getText());		
		if(file.exists()) {
			caminhoRules.setBackground(new Color(165,255,165));
		}else {
			caminhoRules.setBackground(new Color(255,183,183));
		}
		file = new File(caminhoSpam.getText());
		if(file.exists()) {
			caminhoSpam.setBackground(new Color(165,255,165));
		}else {
			caminhoSpam.setBackground(new Color(255,183,183));
		}
		file = new File(caminhoHam.getText());
		if(file.exists()) {
			caminhoHam.setBackground(new Color(165,255,165));
		}else {
			caminhoHam.setBackground(new Color(255,183,183));
		}
	}

	/**
	 * Metodo que inicializa as ListModels
	 */
	private void criarListasModelo() {
		//criar lista de Regras
		listaDeRegras = new DefaultListModel();
				
		if(inicio) {
		//criar lista de Pesos
		listaDePesos = new DefaultListModel();
		}
		//criar lista de Pesos Nao Editaveis
		listaDePesosNaoEditaveis = new DefaultListModel();
		
		//Passar para a lista o que esta no ficheiro
		ReadFile(caminhoRules.getText(),inicio);
		inicio=false;

	}
	
	/**
	 * Metodo que preenche as ModelLists e JTables
	 */
	public void adicionarListasModelo() {
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
		String[][] matrizPreabela = new String[pesos.length][2];
		String[][] matrizPreTabelaNaoEditavel = new String[pesosNaoEditaveis.length][2];
		for(int i = 0; i< pesos.length;i++) {
			matrizPreabela[i][0]=regras[i];
			matrizPreabela[i][1]=pesos[i];
			matrizPreTabelaNaoEditavel[i][0]=regras[i];
			matrizPreTabelaNaoEditavel[i][1]=pesosNaoEditaveis[i];
		}
		
		// criar vetor com os nomes das colunas da TableModel
		Object[] nome = {"Regras","Pesos"};
		
		//criar TableModel com pesos editaveis
		model = new DefaultTableModel(matrizPreabela,nome) {
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
	
	/**
	 * Metodo que adiciona Componentes aos Paineis
	 */
	private void adicionarCoisas() {
		painelDePaineis.add("Pesos Editaveis", painelDeColunas);
		painelDePaineis.add("Pesos Não Editaveis", painelDeColunasNaoEditaveis);		

		painelDeCaminhos.add(labelCaminhoRules);
		painelDeCaminhos.add(labelCaminhoHam);
		painelDeCaminhos.add(labelCaminhoSpam);
		painelDeCaminhos.add(caminhoRules);
		painelDeCaminhos.add(caminhoHam);
		painelDeCaminhos.add(caminhoSpam);

		painelDeBotoes.add(calculo);
		painelDeBotoes.add(labelFP);
		painelDeBotoes.add(labelFN);
		painelDeBotoes.add(gerar);
		painelDeBotoes.add(save);
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
	
	/**
	 * Metodo para tornar a interface visivel
	 */
	private void setVisible() {
		frame.setSize(800,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setVisible(true);
		
	}

	/**
	 * Metodo que cria os ActionListeners
	 */
	private void AdicionarListeners() {
		textFieldListeners();
		labelsListener();
		gerarListener();
		saveListener();
		JTableListener();
		
	}
	
	/**
	 * Metodo que cria os Listeners dos JTextFields
	 */
	private void textFieldListeners() {
		caminhoRulesListener();
		caminhoHamListener();
		caminhoSpamListener();		
	}
	
	/**
	 * Metodo Listener do botão "Calculo" que vai activar o calculo dos FN e FP
	 * da lista de email no caso de os 3 ficheiros serem validos
	 */
	public void labelsListener() {
		calculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File spamFile = new File(caminhoSpam.getText());
				File hamFile = new File(caminhoHam.getText());

				if(listaDeRegras.getSize()>1 && spamFile.exists() && hamFile.exists()) {
				labelFN.setText("FN = "+Calcular(listaDeRegras,listaDePesos,caminhoHam.getText()));
				labelFP.setText("FP = "+Calcular(listaDeRegras,listaDePesos,caminhoSpam.getText()));
				}else {
					labelFN.setText("Inexistente");
					labelFP.setText("Ficheiro");
				}
			}
		});		
	}
	
	/**
	 * Metodo Listener do botão "Save" que vai guardar no ficheiro "rules.cf" os pesos
	 * das regras da JTable que se encontra selecionada
	 */
	public void saveListener() {
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				try {
				String[] pesos=new String[listaDePesos.getSize()];
				if(painelDePaineis.getSelectedIndex()==0) {
					for(int i=0;i<listaDePesos.getSize();i++) {
						pesos[i]=listaDePesos.getElementAt(i);
					}
    			}else {
    				for(int i=0;i<listaDePesosNaoEditaveis.getSize();i++) {
						pesos[i]=listaDePesosNaoEditaveis.getElementAt(i);
					}
    			}
					PesosParaFicheiro(pesos);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});		
	}
	
	/**
	 * Metodo Listener do botao "gerar" que vai correr o algoritmo NSGAII atravez do JMetal
	 * e adicionar o resultado Optimo na JTable de valores nao editaveis
	 */
	public void gerarListener() {
		gerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File spamFile = new File(caminhoSpam.getText());
				File hamFile = new File(caminhoHam.getText());

				if(listaDeRegras.getSize()>1 && spamFile.exists() && hamFile.exists()) {
					if(painelDePaineis.getSelectedIndex()==1) {
				JMetal = new AntiSpamFilterAutomaticConfiguration(listaDeRegras.getSize(),listaDeHam,listaDeSpam,listaDeRegras);
				String[] vetorPesosOptimo = LerFicheiroDePesosOptimizados();
				
				listaDePesosNaoEditaveis.removeAllElements();
				for(int i=0;i<vetorPesosOptimo.length;i++) {
					listaDePesosNaoEditaveis.add(i,vetorPesosOptimo[i]);
				}

				adicionarListasModelo();
				adicionarNaInterface();
				JTableListener();
				aplicacoes = new AplicacoesExternas();
					}else {
						String[] vetorPesosAleatorio=GerarPesosAleatorios();
						listaDePesos.removeAllElements();
						for(int i=0;i<vetorPesosAleatorio.length;i++) {
							listaDePesos.add(i,vetorPesosAleatorio[i]);
						}
						adicionarListasModelo();
						adicionarNaInterface();
						JTableListener();
					}
				}else {
					labelFN.setText("Inexistente");
					labelFP.setText("Ficheiro");
				}
			}
		});	
	}
	
	/**
	 * Metodo que gera Pesos Aleatorios na Tabela Editavel
	 * @return Devolve um array de Strings com os Pesos aleatorio entre -5 e 5
	 */
	public String[] GerarPesosAleatorios(){
		String[] vetorDePesosALeatorio = new String[335];
		Random random = new Random();
		for(int i=0;i<vetorDePesosALeatorio.length;i++) {
			double a = ((random.nextDouble()*10)-5.0);
			vetorDePesosALeatorio[i]=a+"";
		}
		return vetorDePesosALeatorio;
		
	}
	/**
	 * Metodo Listener da JTableEditavel  que actualiza a ListModel de Pesos editaveis
	 */
	public void JTableListener() {
		TabelaPesosEditaveis.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				int row=TabelaPesosEditaveis.getSelectedRow();
				String value=(String)TabelaPesosEditaveis.getValueAt(row, 1);
				listaDePesos.set(row, value);
			}
		});
	}
	
	/**
	 * Metodo que vai ler os ficheiros "ham" e "spam" e adiciona-os a uma Lista de Vectores de Regras
	 * 
	 * @param Ficheiro
	 * @param Lista que vai guardar a informacao do ficheiro
	 */
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
	
	/**
	 * Metodo que vai actualizar a interface quando as Tablas se alteram
	 */
	public void adicionarNaInterface() {
		//adicionar JTable ao painel
		painelDeColunasNaoEditaveis.removeAll();
		painelDeColunasNaoEditaveis.add(new JScrollPane(TabelaPesosNaoEditaveis));
		//adicionar JTable ao painel 
		painelDeColunas.removeAll();
		painelDeColunas.add(new JScrollPane(TabelaPesosEditaveis));
		frame.revalidate();
		frame.repaint();
		
	}

	/**
	 * Metodo que passa o vetor de pesos optimos gerado pelo JMetal para o ficheiro "rules.cf"
	 * @param pesos
	 * @throws IOException
	 */
	public void PesosParaFicheiro(String[] pesos) throws IOException {	  

		try {
		BufferedReader bufRdr = new BufferedReader(new FileReader(caminhoRules.getText()));
		PrintWriter writer = new PrintWriter("rules2.cf", "UTF-8");
		String line="",newline="";
		String[] splited;
		int contador=0;
			while((line = bufRdr.readLine()) != null){
				double a = Double.parseDouble(pesos[contador]);				
				if((splited = line.split("\\s+")).length<2) {					
				    newline = line +" "+ a;				   
				}else {
					splited = line.split("\\s+");					
					newline = splited[0] +" "+ a;				    
				}				
				writer.write(newline);
			    writer.println();
			    contador++;
				}
			writer.close();
			bufRdr.close();
				File ficheiroAEliminar = new File(caminhoRules.getText());
				ficheiroAEliminar.delete();
				ficheiroAEliminar = new File("rules2.cf");
				File ficheiroNovo = new File(caminhoRules.getText());
				ficheiroAEliminar.renameTo(ficheiroNovo);
				} catch (IOException r) {
						r.printStackTrace();
		}
	}

	/**
	 * Metodo que calcula os FP e FN da JTable selecionada e retorna-os num array
	 * @param listaDeRegras2
	 * @param listaDePesos2
	 * @param ficheiro
	 * @return Devolve FN ou FP 
	 */
	public int Calcular(DefaultListModel<String> listaDeRegras2, DefaultListModel<String> listaDePesos2, String ficheiro) {		
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
	                }
                }else if(ficheiro.equals(caminhoSpam.getText())) {
                	if(total<5.0) {
	                	Falsos++;
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
	
	/**
	 * Metodo Listener da JTextField Spam que verifica se o caminho e valido
	 */
	public void caminhoSpamListener() {
		caminhoSpam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerificarFicheiros();				
			}
		});		
	}

	/**
	 * Metodo Listener da JTextField Ham que verifica se o caminho e valido
	 */
	public void caminhoHamListener() {
		caminhoHam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerificarFicheiros();
			}
		});		
	}

	/**
	 * Metodo Listener da JTextField Rules que verifica se o caminho e valido, e se for
	 * actualiza as tabelas
	 */
	public void caminhoRulesListener() {
		caminhoRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inicio=true;
				VerificarFicheiros();
				criarListasModelo();
				adicionarListasModelo();
				adicionarNaInterface();
			}
		});		
	}

	/**
	 * Metodo que vai ler o ficheiro "rules.cf" e atribuir as regras e os pesos (se existirem) as tabelas
	 * @param ficheiro
	 * @param inicio
	 */
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

	/**
	 * Metodo que vai definir os layouts da frame e dos paineis
	 */
	private void definirLayouts() {
		frame.setLayout(new BorderLayout());
		painelDeCaminhos.setLayout(new GridLayout(2,3,30,10));
		painelDeBotoes.setLayout(new FlowLayout(FlowLayout.CENTER,60,20));
		painelDeColunas.setLayout(new GridLayout(1,1));
		painelDeColunasNaoEditaveis.setLayout(new GridLayout(1,1));		
	}

	/**
	 * Metodo que vai Ler o ficheiro de pesos optimizados e retorna-o num array de strings
	 * @return Devolde um array de String com os Pesos Optimos
	 */
	private String[] LerFicheiroDePesosOptimizados(){
		FileReader fr = null;
        BufferedReader br = null;
        ArrayList<String[]> listaDosVetoresDePesos= new ArrayList<String[]>();
        int FileIndex=0;
        try {
            fr = new FileReader ("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblemForProfessionalMailbox.rs");
            br = new BufferedReader(fr); 
            
            String line;
            String[] lineSPlitted;
            double[] stringToDouble;
            while((line=br.readLine())!=null) {
            	lineSPlitted=line.split("\\s+");
            	listaDosVetoresDePesos.add(lineSPlitted);
            }  
            fr = new FileReader ("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblemForProfessionalMailbox.rf");

            br = new BufferedReader(fr);
            double menor=0;
            double FN=0;
            int contador=0;
            while((line=br.readLine())!=null) {
            	lineSPlitted=line.split("\\s+");
            	double[] actual = {Double.parseDouble(lineSPlitted[0]),Double.parseDouble(lineSPlitted[1])};            	
            	if(contador==0) {
            		menor=actual[0];
            		FN=actual[1];
            		FileIndex=contador;
            	}else if(actual[0]<menor) {            		
            		menor=actual[0];
            		FN=actual[1];
            		FileIndex=contador;
            	}else if(actual[0]==menor && actual[1]<FN) {
            		menor=actual[0];
            		FN=actual[1];
            		FileIndex=contador;
            	}
            	contador++;
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
        return listaDosVetoresDePesos.get(FileIndex);		
	}
		

	/**
	 * Metodo main que inicia a interface
	 * @param args
	 */
	public static void main(String [] args){
		GraphicalInterface interfaceGrafica = new GraphicalInterface();
	}
	
}
