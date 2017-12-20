package antiSpamFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;

import org.uma.jmetal.solution.DoubleSolution;

/**
 * @author Miguel  Bento n�69515 Lei Pl
 *
 * @author Daniel Fernando n�72756 Lei Pl
 *
 * @author Jo�o Nuno n�73304
 *
 * @author Ricardo Lopes n�73384
 *
 */
public class AntiSpamConfigurationForProfessionalMailbox extends AntiSpamFilterProblem {
	
	ArrayList<String[]> listaDeHam;
	ArrayList<String[]> listaDeSpam;
	DefaultListModel listaDeRegras ;
	HashMap<String,Double> mapa;


	/**
	 * Método Construtor que cria um AntiSpamConfigurationForProfessionalMailbox
	 * 
	 * @param numberOfVariables
	 * @param listaDeHam
	 * @param listaDeSpam
	 * @param listaDeRegras
	 */
	  public AntiSpamConfigurationForProfessionalMailbox(Integer numberOfVariables,ArrayList<String[]> listaDeHam,ArrayList<String[]> listaDeSpam,DefaultListModel listaDeRegras) {
		super(numberOfVariables);
		this.listaDeHam=listaDeHam;
		this.listaDeSpam=listaDeSpam;
		this.listaDeRegras=listaDeRegras;
		mapa = new HashMap<String,Double>();
		setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(2);
	    setName("AntiSpamFilterProblemForProfessionalMailbox");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(-5.0);
	      upperLimit.add(5.0);
	      mapa.put((String)listaDeRegras.getElementAt(i), 0.0);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	  }

	  /**
	   * Método evaluate modificado
	   */
	  @Override
	  public void evaluate(DoubleSolution solution){ 
	    
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      mapa.put((String)listaDeRegras.getElementAt(i), solution.getVariableValue(i));
	    }
	    
	    int[] resposta = Calcular2();	    
	    
	    
	    solution.setObjective(0, resposta[0]);
	    solution.setObjective(1, resposta[1]);
	  }
	  
	  /**
	   * Método que calcula os FN e FP os retorna atravez de um array de inteiro que vai
	   * ser usado no método evaluate
	   * 
	   * @return
	   */
	  protected int[] Calcular2() {		  
			int[] resposta = new int[2];			
	            double total=0;
	            for(String[] linha : listaDeHam) {                
	                for(String regra:linha) {    	
	                	if(mapa.containsKey(regra)) {
	                		total+=mapa.get(regra);
	                	}
	                }               
		                if(total>5.0) {
		                	resposta[0]= resposta[0]+1;
		                			                }                
		                total=0;
	            }
	            for(String[] linha : listaDeSpam) {                
	                for(String regra:linha) {
	                	if(mapa.containsKey(regra)) {
	                		total+=mapa.get(regra);
	                	}
	                }                             
		                if(total<5.0) {
		                	resposta[1]= resposta[1]+1;
		                }                
	                total=0;
	            }              
			return resposta;
		}
	}
