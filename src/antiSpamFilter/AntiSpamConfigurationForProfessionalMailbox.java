package antiSpamFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;

import org.uma.jmetal.solution.DoubleSolution;



public class AntiSpamConfigurationForProfessionalMailbox extends AntiSpamFilterProblem {
	
	ArrayList<String[]> listaDeHam;
	ArrayList<String[]> listaDeSpam;
	DefaultListModel listaDeRegras ;
	HashMap<String,Double> mapa;


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

	  
	  @Override
	  public void evaluate(DoubleSolution solution){ 
	    
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      mapa.put((String)listaDeRegras.getElementAt(i), solution.getVariableValue(i));
	    }
	    
	    int[] resposta = new int[2];	    
	    
	    
	    solution.setObjective(0, resposta[0]);
	    solution.setObjective(1, resposta[1]);
	  }
	  
	  
	  
	}
