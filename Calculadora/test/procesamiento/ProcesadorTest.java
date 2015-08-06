/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package procesamiento;

import datos.FuncionOculta;
import java.math.BigDecimal;
import java.math.MathContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Andrés Sarmiento Tobón <ansarmientoto at unal.edu.co>
 */
public class ProcesadorTest {
    
    /**
     *
     */
    public ProcesadorTest() {
    }
    
    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }
    
    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     *
     */
    @Before
    public void setUp() {
    }
    
    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of evaluar method, of class Procesador.
     * @throws Exception 
     */
    @Test
    public void testEvaluar() throws Exception {
        System.out.println("evaluar");
        String cadena = "";
        String[] listaVariables = null;
        BigDecimal[] valoresVariables = null;
        BigDecimal expResult = null;
        BigDecimal result =
                Procesador.evaluar(cadena, listaVariables, valoresVariables);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of procesar method, of class Procesador.
     * @throws Exception 
     */
    @Test
    public void testProcesar() throws Exception {
        System.out.println("procesar");
        String cadena = "";
        Object expResult = null;
        Object result = Procesador.procesar(cadena);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evaluarRPN method, of class Procesador.
     * @throws Exception 
     */
    @Test
    public void testEvaluarRPN() throws Exception {
        System.out.println("evaluarRPN");
        String[] rpn = null;
        Object expResult = null;
        Object result = Procesador.evaluarRPN(rpn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convertirRPN method, of class Procesador.
     * @throws Exception 
     */
    @Test
    public void testConvertirRPN() throws Exception {
        System.out.println("convertirRPN");
        String cadena = "";
        String[] expResult = null;
        String[] result = Procesador.convertirRPN(cadena);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of extraerVariablesOcultas method, of class Procesador.
     */
    @Test
    public void testExtraerVariablesOcultas() {
        System.out.println("extraerVariablesOcultas");
        String cadena = "";
        FuncionOculta[] expResult = null;
        FuncionOculta[] result = Procesador.extraerVariablesOcultas(cadena);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of encontrarCeros method, of class Procesador.
     * @throws Exception 
     */
    @Test
    public void testEncontrarCeros_3args() throws Exception {
        System.out.println("encontrarCeros");
        String cadena = "";
        String[] variables = null;
        BigDecimal[] inicio = null;
        BigDecimal[] expResult = null;
        BigDecimal[] result =
                Procesador.encontrarCeros(cadena, variables, inicio);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of encontrarCeros method, of class Procesador.
     * @throws Exception 
     */
    @Test
    public void testEncontrarCeros_String() throws Exception {
        System.out.println("encontrarCeros");
        String cadena = "";
        Object[] expResult = null;
        Object[] result = Procesador.encontrarCeros(cadena);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of esNumero method, of class Procesador.
     */
    @Test
    public void testEsNumero() {
        System.out.println("esNumero");
        String item = "";
        boolean expResult = false;
        boolean result = Procesador.esNumero(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of esFuncion method, of class Procesador.
     */
    @Test
    public void testEsFuncion() {
        System.out.println("esFuncion");
        String item = "";
        boolean expResult = false;
        boolean result = Procesador.esFuncion(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of esVariable method, of class Procesador.
     */
    @Test
    public void testEsVariable() {
        System.out.println("esVariable");
        String item = "";
        boolean expResult = false;
        boolean result = Procesador.esVariable(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of esParentesisDerecho method, of class Procesador.
     */
    @Test
    public void testEsParentesisDerecho() {
        System.out.println("esParentesisDerecho");
        String item = "";
        boolean expResult = false;
        boolean result = Procesador.esParentesisDerecho(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of esParentesisIzquierdo method, of class Procesador.
     */
    @Test
    public void testEsParentesisIzquierdo() {
        System.out.println("esParentesisIzquierdo");
        String item = "";
        boolean expResult = false;
        boolean result = Procesador.esParentesisIzquierdo(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of esOperador method, of class Procesador.
     */
    @Test
    public void testEsOperador() {
        System.out.println("esOperador");
        String item = "";
        boolean expResult = false;
        boolean result = Procesador.esOperador(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of esSeparadorArgumento method, of class Procesador.
     */
    @Test
    public void testEsSeparadorArgumento() {
        System.out.println("esSeparadorArgumento");
        String item = "";
        boolean expResult = false;
        boolean result = Procesador.esSeparadorArgumento(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of esOperadorAsociativoIzquierdo method, of class Procesador.
     */
    @Test
    public void testEsOperadorAsociativoIzquierdo() {
        System.out.println("esOperadorAsociativoIzquierdo");
        String item = "";
        boolean expResult = false;
        boolean result = Procesador.esOperadorAsociativoIzquierdo(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of esOperadorAsociativoDerecho method, of class Procesador.
     */
    @Test
    public void testEsOperadorAsociativoDerecho() {
        System.out.println("esOperadorAsociativoDerecho");
        String item = "";
        boolean expResult = false;
        boolean result = Procesador.esOperadorAsociativoDerecho(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tieneOperadorMenorIgualPrecedencia method, of class Procesador.
     */
    @Test
    public void testTieneOperadorMenorIgualPrecedencia() {
        System.out.println("tieneOperadorMenorIgualPrecedencia");
        String item = "";
        String operadorPila = "";
        boolean expResult = false;
        boolean result =
                Procesador.tieneOperadorMenorIgualPrecedencia(item, operadorPila);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tieneOperadorMenorPrecedencia method, of class Procesador.
     */
    @Test
    public void testTieneOperadorMenorPrecedencia() {
        System.out.println("tieneOperadorMenorPrecedencia");
        String item = "";
        String operadorPila = "";
        boolean expResult = false;
        boolean result =
                Procesador.tieneOperadorMenorPrecedencia(item, operadorPila);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of potenciacion method, of class Procesador.
     * @throws Exception 
     */
    @Test
    public void testPotenciacion() throws Exception {
        System.out.println("potenciacion");
        BigDecimal base = null;
        BigDecimal exponente = null;
        MathContext contexto = null;
        BigDecimal expResult = null;
        BigDecimal result = Procesador.potenciacion(base, exponente, contexto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Procesador.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Procesador.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cambiarEscalaArreglo method, of class Procesador.
     */
    @Test
    public void testCambiarEscalaArreglo() {
        System.out.println("cambiarEscalaArreglo");
        BigDecimal[] arreglo = null;
        MathContext escala = null;
        BigDecimal[] expResult = null;
        BigDecimal[] result = Procesador.cambiarEscalaArreglo(arreglo, escala);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of objetoABigDecimal method, of class Procesador.
     */
    @Test
    public void testObjetoABigDecimal() {
        System.out.println("objetoABigDecimal");
        Object[] arreglo = null;
        BigDecimal[] expResult = null;
        BigDecimal[] result = Procesador.objetoABigDecimal(arreglo);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerVariablesCerosActuales method, of class Procesador.
     */
    @Test
    public void testObtenerVariablesCerosActuales() {
        System.out.println("obtenerVariablesCerosActuales");
        String[] expResult = null;
        String[] result = Procesador.obtenerVariablesCerosActuales();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evaluarExpresionPunto method, of class Procesador.
     */
    @Test
    public void testEvaluarExpresionPunto() {
        System.out.println("evaluarExpresionPunto");
        String[] expresionRPN = null;
        BigDecimal punto = null;
        BigDecimal expResult = null;
        BigDecimal result =
                Procesador.evaluarExpresionPunto(expresionRPN, punto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
