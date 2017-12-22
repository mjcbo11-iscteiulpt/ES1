package antiSpamFilter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import antiSpamFilter.GraphicalInterface;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import javax.swing.DefaultListModel;

public class GraphicalInterfaceTest
{
	GraphicalInterface instance = new GraphicalInterface();

	/*
	 * Testing Constructor
	 */
	@Test
	public void test_method_GraphicalInterface()
	{
		System.out.println("Now Testing Method:GraphicalInterface");	

		assertNotNull(instance);
	}


	/*
	 * Testing AdicionarListasModelo
	 */
	@Test
	public void test_method_adicionarListasModelo()
	{
		System.out.println("Now Testing Method:adicionarListasModelo");		
		//Call Method
		instance.adicionarListasModelo();		
		//Check Test Verification Points
		assertEquals(335, instance.listaDePesos.size());
		assertEquals(335, instance.listaDeRegras.size());
		assertEquals(335, instance.listaDePesosNaoEditaveis.size());
		
	}

	/*
	 * Testing LabelsListener
	 */
	@Test
	public void test_method_labelsListener()
	{
		System.out.println("Now Testing Method:labelsListener");

		
		//Call Method
		instance.labelsListener();
		
		//Check Test Verification Points
		assertNotNull(instance.caminhoSpam.getText());
		assertNotNull(instance.caminhoHam.getText());
		assertEquals(335, instance.listaDeRegras.size());
		assertEquals(335, instance.listaDePesos.size());
		
	}

	/*
	 * Testing saveListener()
	 */
	@Test
	public void test_method_saveListener_4_branch_0()
	{
		System.out.println("Now Testing Method:saveListener");

		
		//Call Method
		instance.saveListener();
		
		//Check Test Verification Points
		assertEquals(335, instance.listaDePesos.size());
		assertEquals(335, instance.listaDePesosNaoEditaveis.size());
		assertNotNull(instance.painelDePaineis);
		
	}

	/*
	 * Testing gerarListener()
	 */
	@Test
	public void test_method_gerarListener_5_branch_0()
	{
		System.out.println("Now Testing Method:gerarListener");

		
		//Call Method
		instance.gerarListener();
		
		//Check Test Verification Points
		assertNotNull(instance.caminhoSpam.getText());
		assertNotNull(instance.caminhoHam.getText());
		assertEquals(335, instance.listaDeRegras.size());
		assertEquals(335, instance.listaDePesos.size());
		assertEquals(335, instance.listaDePesosNaoEditaveis.size());
		
	}

	/*
	 * Testing gerarPesosAleatorios() for: (int i=0;i<vetorDePesosALeatorio.length;i++)
	 */
	@Test
	public void test_method_GerarPesosAleatorios()
	{
		System.out.println("Now Testing Method:GerarPesosAleatorios");		
		//Get expected result and result
		
		String[] result = instance.GerarPesosAleatorios();
		
		//Check Return value
		assertEquals(335, result.length );
		
	}

	/*
	 * Testing GerarPesosAleatorios() for: Not (int i=0;i<vetorDePesosALeatorio.length;i++)
	 */
	@Test
	public void test_method_GerarPesosAleatorios_2()
	{
		System.out.println("Now Testing Method:GerarPesosAleatorios");

		
		//Get expected result and result
		String[] result = instance.GerarPesosAleatorios();
		
		//Check Return value
		assertEquals(335, result.length);
		
	}

	/*
	 * Testing JTabLe Listener()
	 */
	@Test
	public void test_method_JTableListener()
	{
		System.out.println("Now Testing Method:JTableListener");

		
		//Call Method
		instance.JTableListener();
		
		//Check Test Verification Points
		assertNotNull(instance.TabelaPesosEditaveis.getSize());
		assertEquals(335, instance.listaDePesos.size());
		assertNotNull(instance.TabelaPesosEditaveis);
		
	}

	/*
	 * Testing adicionarNaInterface()
	 */
	@Test
	public void test_method_adicionarNaInterface()
	{
		System.out.println("Now Testing Method:adicionarNaInterface");

		
		//Call Method
		instance.adicionarNaInterface();
		
		//Check Test Verification Points
		assertNotNull(instance.painelDeColunasNaoEditaveis);
		assertNotNull(instance.painelDeColunas);
		assertNotNull(instance.frame);
		
	}

	/*
	 * Testing PesosParaFicheiro while: ((line = bufRdr.readLine()) != null), if: ((splited = line.split("\\s+")).length<2)
	 */
	@Test
	public void test_method_PesosParaFicheiro() throws IOException
	{
		System.out.println("Now Testing Method:PesosParaFicheiro");

		String[] a = new String[335];
		Arrays.fill(a, "0");
		//Call Method
		instance.PesosParaFicheiro(a);
		
		//Check Test Verification Points
		assertNotNull(instance.caminhoRules.getText());
		
	}



	/*
	 * Testing Calcular while: ((line=br.readLine())!=null), for: (String regra:lista), for: (int i=0 ; i<listaDeRegras.getSize();i++), if: (regra.equals(listaDeRegras.getElementAt(i))), if: (painelDePaineis.getSelectedIndex()==0)
	 */
	@Test
	public void test_method_Calcular()
	{
		System.out.println("Now Testing Method:Calcular");

		//Get expected result and result		
		Object result = instance.Calcular( new DefaultListModel<String>(),  new DefaultListModel<String>(),  "rules.cf");
		
		//Check Return value
		assertNotNull(result);
		
	}




	/*
	 * Testing caminhoSpamListener()
	 */
	@Test
	public void test_method_caminhoSpamListener()
	{
		System.out.println("Now Testing Method:caminhoSpamListener");

		
		//Call Method
		instance.caminhoSpamListener();
		
		//Check Test Verification Points
		assertNotNull(instance.caminhoSpam);
		
	}

	/*
	 * Testing caminhoHamListener()
	 */
	@Test
	public void test_method_caminhoHamListener()
	{
		System.out.println("Now Testing Method:caminhoHamListener");

		
		//Call Method
		instance.caminhoHamListener();
		
		//Check Test Verification Points
		assertNotNull(instance.caminhoHam);
		
	}

	/*
	 * Testing caminhoRulerListener()
	 */
	@Test
	public void test_method_caminhoRulesListener()
	{
		System.out.println("Now Testing Method:caminhoRulesListener");

		
		//Call Method
		instance.caminhoRulesListener();
		
		//Check Test Verification Points
		assertNotNull(instance.caminhoRules);
		
	}
}


