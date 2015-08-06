/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datos.quimica;

import java.math.BigDecimal;

/**
 *
 * @author Andrés Sarmiento Tobón <ansarmientoto at unal.edu.co>
 */
public class Elemento {
    
    private static final Elemento[] elementos;
    
    static {
        elementos = new Elemento[] {
            new Elemento(1, "Hidrógeno", "H", "IA", 1, 
                new BigDecimal("1.00797"), 1, Estado.GAS, 
                Propiedad.OTRONOMETAL),
            new Elemento(2, "Helio", "He", "VIIIA", 18, 
                new BigDecimal("4.0026"), 1, Estado.GAS, 
                Propiedad.GASNOBLE),
            new Elemento(3, "Litio", "Li", "IA", 1, 
                new BigDecimal("6.9412"), 2, Estado.SOLIDO, 
                Propiedad.ALCALINO),
            new Elemento(4, "Berilio", "Be", "IIA", 2, 
                new BigDecimal("9.0122"), 2, Estado.SOLIDO, 
                Propiedad.ALCALINOTERREO),
            new Elemento(5, "Boro", "B", "IIIA", 13, 
                new BigDecimal("10.8117"), 2, Estado.SOLIDO, 
                Propiedad.SEMIMETAL),
            new Elemento(6, "Carbono", "C", "IVA", 14, 
                new BigDecimal("12.011"), 2, Estado.SOLIDO, 
                Propiedad.OTRONOMETAL),
            new Elemento(7, "Nitrógeno", "N", "VA", 15, 
                new BigDecimal("14.0067"), 2, Estado.GAS, 
                Propiedad.OTRONOMETAL),
            new Elemento(8, "Oxígeno", "O", "VIA", 16, 
                new BigDecimal("15.9994"), 2, Estado.GAS, 
                Propiedad.OTRONOMETAL),
            new Elemento(9, "Flúor", "F", "VIIA", 17, 
                new BigDecimal("18.998"), 2, Estado.GAS, 
                Propiedad.HALOGENO),
            new Elemento(10, "Neón", "Ne", "VIIIA", 18, 
                new BigDecimal("4.0026"), 2, Estado.GAS, 
                Propiedad.GASNOBLE),
            new Elemento(11, "Sodio", "Na", "IA", 1, 
                new BigDecimal("22.990"), 3, Estado.SOLIDO, 
                Propiedad.ALCALINO),
            new Elemento(12, "Magnesio", "Mg", "IIA", 2, 
                new BigDecimal("24.305"), 3, Estado.SOLIDO, 
                Propiedad.ALCALINOTERREO),
            new Elemento(13, "Aluminio", "Al", "IIIA", 13, 
                new BigDecimal("26.982"), 3, Estado.SOLIDO, 
                Propiedad.METALBASICO),
            new Elemento(14, "Silicio", "Si", "IVA", 14, 
                new BigDecimal("28.086"), 3, Estado.SOLIDO, 
                Propiedad.SEMIMETAL),
            new Elemento(15, "Fósforo", "P", "VA", 15, 
                new BigDecimal("30.974"), 3, Estado.SOLIDO, 
                Propiedad.OTRONOMETAL),
            new Elemento(16, "Azufre", "S", "VIA", 16, 
                new BigDecimal("32.0666"), 3, Estado.SOLIDO, 
                Propiedad.OTRONOMETAL),
            new Elemento(17, "Cloro", "Cl", "VIIA", 17, 
                new BigDecimal("35.453"), 3, Estado.GAS, 
                Propiedad.HALOGENO),
            new Elemento(18, "Argón", "Ar", "VIIIA", 18, 
                new BigDecimal("39.9481"), 3, Estado.GAS, 
                Propiedad.GASNOBLE),
            new Elemento(19, "Potasio", "K", "IA", 1, 
                new BigDecimal("39.098"), 4, Estado.SOLIDO, 
                Propiedad.ALCALINO),
            new Elemento(20, "Calcio", "Ca", "IIA", 2, 
                new BigDecimal("40.0784"), 4, Estado.SOLIDO, 
                Propiedad.ALCALINOTERREO),
            new Elemento(21, "Escandio", "Sc", "IIIB", 3, 
                new BigDecimal("44.956"), 4, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(22, "Titanio", "Ti", "IVB", 4, 
                new BigDecimal("47.8671"), 4, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(23, "Vanadio", "V", "VB", 5, 
                new BigDecimal("50.9421"), 4, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(24, "Cromo", "Cr", "VIB", 6, 
                new BigDecimal("51.996"), 4, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(25, "Manganeso", "Mn", "VIIB", 7, 
                new BigDecimal("54.938"), 4, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(26, "Hierro", "Fe", "VIIIB", 8, 
                new BigDecimal("55.8452"), 4, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(27, "Cobalto", "Co", "VIIIB", 9, 
                new BigDecimal("58.933"), 4, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(28, "Níquel", "Ni", "VIIIB", 10, 
                new BigDecimal("58.693"), 4, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(29, "Cobre", "Cu", "IB", 11, 
                new BigDecimal("63.5463"), 4, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(30, "Zinc", "Zn", "IIB", 12, 
                new BigDecimal("65.392"), 4, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(31, "Galio", "Ga", "IIIA", 13, 
                new BigDecimal("69.7231"), 4, Estado.SOLIDO, 
                Propiedad.METALBASICO),
            new Elemento(32, "Germanio", "Ge", "IVA", 14, 
                new BigDecimal("72.612"), 4, Estado.SOLIDO, 
                Propiedad.SEMIMETAL),
            new Elemento(33, "Arsénico", "As", "VA", 15, 
                new BigDecimal("74.922"), 4, Estado.SOLIDO, 
                Propiedad.SEMIMETAL),
            new Elemento(34, "Selenio", "Se", "VIA", 16, 
                new BigDecimal("78.963"), 4, Estado.SOLIDO, 
                Propiedad.OTRONOMETAL),
            new Elemento(35, "Bromo", "Br", "VIIA", 17, 
                new BigDecimal("79.9041"), 4, Estado.LIQUIDO, 
                Propiedad.HALOGENO),
            new Elemento(36, "Kriptón", "Kr", "VIIIA", 18, 
                new BigDecimal("83.801"), 4, Estado.GAS, 
                Propiedad.GASNOBLE),
            new Elemento(37, "Rubidio", "Rb", "IA", 1, 
                new BigDecimal("85.468"), 5, Estado.SOLIDO, 
                Propiedad.ALCALINO),
            new Elemento(38, "Estroncio", "Sr", "IIA", 2, 
                new BigDecimal("87.621"), 5, Estado.SOLIDO, 
                Propiedad.ALCALINOTERREO),
            new Elemento(39, "Itrio", "Y", "IIIB", 3, 
                new BigDecimal("88.906"), 5, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(40, "Zirconio", "Zr", "IVB", 4, 
                new BigDecimal("91.2242"), 5, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(41, "Niobio", "Nb", "VB", 5, 
                new BigDecimal("92.906"), 5, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(42, "Molibdeno", "Mo", "VIB", 6, 
                new BigDecimal("95.941"), 5, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(43, "Tecnecio", "Tc", "VIIB", 7, 
                new BigDecimal("97.907"), 5, Estado.SOLIDO, 
                Propiedad.METALTRANSICION, Presencia.RASTRORADIOISOTOPO),
            new Elemento(44, "Rutenio", "Ru", "VIIIB", 8, 
                new BigDecimal("101.072"), 5, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(45, "Rodio", "Rh", "VIIIB", 9, 
                new BigDecimal("102.906"), 5, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(46, "Paladio", "Pd", "VIIIB", 10, 
                new BigDecimal("106.421"), 5, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(47, "Plata", "Ag", "IB", 11, 
                new BigDecimal("107.868"), 5, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(48, "Cadmio", "Cd", "IIB", 12, 
                new BigDecimal("112.4118"), 5, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(49, "Indio", "In", "IIIA", 13, 
                new BigDecimal("114.8183"), 5, Estado.SOLIDO, 
                Propiedad.METALBASICO),
            new Elemento(50, "Estaño", "Sn", "IVA", 14, 
                new BigDecimal("118.7107"), 5, Estado.SOLIDO, 
                Propiedad.METALBASICO),
            new Elemento(51, "Antimonio", "Sb", "VA", 15, 
                new BigDecimal("121.7601"), 5, Estado.SOLIDO, 
                Propiedad.SEMIMETAL),
            new Elemento(52, "Telurio", "Te", "VIA", 16, 
                new BigDecimal("127.603"), 5, Estado.SOLIDO, 
                Propiedad.SEMIMETAL),
            new Elemento(53, "Yodo", "I", "VIIA", 17, 
                new BigDecimal("126.904"), 5, Estado.SOLIDO, 
                Propiedad.HALOGENO),
            new Elemento(54, "Xenón", "Xe", "VIIIA", 18, 
                new BigDecimal("131.292"), 5, Estado.SOLIDO, 
                Propiedad.GASNOBLE),
            new Elemento(55, "Cesio", "Cs", "IA", 1, 
                new BigDecimal("132.905"), 6, Estado.SOLIDO, 
                Propiedad.ALCALINO),
            new Elemento(56, "Bario", "Ba", "IIA", 2, 
                new BigDecimal("137.3277"), 6, Estado.SOLIDO, 
                Propiedad.ALCALINOTERREO),
            new Elemento(57, "Lantano", "La", "IIIB", 3, 
                new BigDecimal("138.905"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(58, "Cerio", "Ce", "IIIB", 3, 
                new BigDecimal("140.1161"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(59, "Praseodimio", "Pr", "IIIB", 3, 
                new BigDecimal("140.908"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(60, "Neodimio", "Nd", "IIIB", 3, 
                new BigDecimal("144.2423"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(61, "Prometio", "Pm", "IIIB", 3, 
                new BigDecimal("144.913"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO, Presencia.RASTRORADIOISOTOPO),
            new Elemento(62, "Samario", "Sm", "IIIB", 3, 
                new BigDecimal("150.362"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(63, "Europio", "Eu", "IIIB", 3, 
                new BigDecimal("151.9641"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(64, "Gadolinio", "Gd", "IIIB", 3, 
                new BigDecimal("157.253"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(65, "Terbio", "Tb", "IIIB", 3, 
                new BigDecimal("158.925"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(66, "Disprosio", "Dy", "IIIB", 3, 
                new BigDecimal("162.5001"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(67, "Holmio", "Ho", "IIIB", 3, 
                new BigDecimal("164.930"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(68, "Erbio", "Er", "IIIB", 3, 
                new BigDecimal("167.2593"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(69, "Tulio", "Tm", "IIIB", 3, 
                new BigDecimal("168.934"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(70, "Iterbio", "Yb", "IIIB", 3, 
                new BigDecimal("173.043"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(71, "Lutecio", "Lu", "IIIB", 3, 
                new BigDecimal("174.9671"), 6, Estado.SOLIDO, 
                Propiedad.LANTANIDO),
            new Elemento(72, "Hafnio", "Hf", "IVB", 4, 
                new BigDecimal("178.492"), 6, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(73, "Tantalio", "Ta", "VB", 5, 
                new BigDecimal("180.948"), 6, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(74, "Wolframio", "W", "VIB", 6, 
                new BigDecimal("183.841"), 6, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(75, "Renio", "Re", "VIIB", 7, 
                new BigDecimal("186.2071"), 6, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(76, "Osmio", "Os", "VIIIB", 8, 
                new BigDecimal("190.233"), 6, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(77, "Iridio", "Ir", "VIIIB", 9, 
                new BigDecimal("192.2173"), 6, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(78, "Platino", "Pt", "VIIIB", 10, 
                new BigDecimal("195.0849"), 6, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(79, "Oro", "Au", "IB", 11, 
                new BigDecimal("196.967"), 6, Estado.SOLIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(80, "Mercurio", "Hg", "IIB", 12, 
                new BigDecimal("200.592"), 6, Estado.LIQUIDO, 
                Propiedad.METALTRANSICION),
            new Elemento(81, "Talio", "Tl", "IIIA", 13, 
                new BigDecimal("204.383"), 6, Estado.SOLIDO, 
                Propiedad.METALBASICO),
            new Elemento(82, "Plomo", "Pb", "IVA", 14, 
                new BigDecimal("207.21"), 6, Estado.SOLIDO, 
                Propiedad.METALBASICO),
            new Elemento(83, "Bismuto", "Bi", "VA", 15, 
                new BigDecimal("208.980"), 6, Estado.SOLIDO, 
                Propiedad.METALBASICO),
            new Elemento(84, "Polonio", "Po", "VIA", 16, 
                new BigDecimal("208.982"), 6, Estado.SOLIDO, 
                Propiedad.SEMIMETAL, Presencia.RASTRORADIOISOTOPO),
            new Elemento(85, "Astato", "At", "VIIA", 17, 
                new BigDecimal("209.987"), 6, Estado.SOLIDO, 
                Propiedad.HALOGENO),
            new Elemento(86, "Radón", "Rn", "VIIIA", 18, 
                new BigDecimal("222.018"), 6, Estado.SOLIDO, 
                Propiedad.GASNOBLE),
            new Elemento(87, "Francio", "Fr", "IA", 1, 
                new BigDecimal("223.020"), 7, Estado.SOLIDO, 
                Propiedad.ALCALINO, Presencia.RASTRORADIOISOTOPO),
            new Elemento(88, "Radio", "Ra", "IIA", 2, 
                new BigDecimal("226.0254"), 7, Estado.SOLIDO, 
                Propiedad.ALCALINOTERREO, Presencia.RASTRORADIOISOTOPO),
            new Elemento(89, "Actinio", "Ac", "IIIB", 3, 
                new BigDecimal("227.027"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.RASTRORADIOISOTOPO),
            new Elemento(90, "Torio", "Th", "IIIB", 3, 
                new BigDecimal("232.038"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO),
            new Elemento(91, "Protactinio", "Pa", "IIIB", 3, 
                new BigDecimal("231.036"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.RASTRORADIOISOTOPO),
            new Elemento(92, "Uranio", "U", "IIIB", 3, 
                new BigDecimal("238.029"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO),
            new Elemento(93, "Neptunio", "Np", "IIIB", 3, 
                new BigDecimal("237.048"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.RASTRORADIOISOTOPO),
            new Elemento(94, "Plutonio", "Pu", "IIIB", 3, 
                new BigDecimal("244.064"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO),
            new Elemento(95, "Americio", "Am", "IIIB", 3, 
                new BigDecimal("243.061"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.SINTETICO),
            new Elemento(96, "Curio", "Cm", "IIIB", 3, 
                new BigDecimal("247.070"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.SINTETICO),
            new Elemento(97, "Berkelio", "Bk", "IIIB", 3, 
                new BigDecimal("247.070"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.SINTETICO),
            new Elemento(98, "Californio", "Cf", "IIIB", 3, 
                new BigDecimal("251.080"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.SINTETICO),
            new Elemento(99, "Einstenio", "Es", "IIIB", 3, 
                new BigDecimal("252.083"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.SINTETICO),
            new Elemento(100, "Fermio", "Fm", "IIIB", 3, 
                new BigDecimal("257.095"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.SINTETICO),
            new Elemento(101, "Mendelevio", "Md", "IIIB", 3, 
                new BigDecimal("258.098"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.SINTETICO),
            new Elemento(102, "Nobelio", "No", "IIIB", 3, 
                new BigDecimal("259.101"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.SINTETICO),
            new Elemento(103, "Lawrencio", "Lr", "IIIB", 3, 
                new BigDecimal("262.110"), 7, Estado.SOLIDO, 
                Propiedad.ACTINIDO, Presencia.SINTETICO),
            new Elemento(104, "Rutherfordio", "Rf", "IVB", 4, 
                new BigDecimal("263.113"), 7, Estado.DESCONOCIDO, 
                Propiedad.METALTRANSICION, Presencia.SINTETICO),
            new Elemento(105, "Dubnio", "Db", "VB", 5, 
                new BigDecimal("262.114"), 7, Estado.DESCONOCIDO, 
                Propiedad.METALTRANSICION, Presencia.SINTETICO),
            new Elemento(106, "Seaborgio", "Sg", "VIB", 6, 
                new BigDecimal("266.122"), 7, Estado.DESCONOCIDO, 
                Propiedad.METALTRANSICION, Presencia.SINTETICO),
            new Elemento(107, "Bohrio", "Bh", "VIIB", 7, 
                new BigDecimal("264.1247"), 7, Estado.DESCONOCIDO, 
                Propiedad.METALTRANSICION, Presencia.SINTETICO),
            new Elemento(108, "Hassio", "Hs", "VIIIB", 8, 
                new BigDecimal("269.134"), 7, Estado.DESCONOCIDO, 
                Propiedad.METALTRANSICION, Presencia.SINTETICO),
            new Elemento(109, "Meitnerio", "Mt", "VIIIB", 9, 
                new BigDecimal("268.139"), 7, Estado.DESCONOCIDO, 
                Propiedad.METALTRANSICION, Presencia.SINTETICO),
            new Elemento(110, "Darmstadio", "Ds", "VIIIB", 10, 
                new BigDecimal("272.146"), 7, Estado.DESCONOCIDO, 
                Propiedad.METALTRANSICION, Presencia.SINTETICO),
            new Elemento(111, "Roentgenio", "Rg", "IB", 11, 
                new BigDecimal("272.154"), 7, Estado.DESCONOCIDO, 
                Propiedad.METALTRANSICION, Presencia.SINTETICO),
            new Elemento(112, "Copernicio", "Cn", "IIB", 12, 
                new BigDecimal("277"), 7, Estado.DESCONOCIDO, 
                Propiedad.METALTRANSICION, Presencia.SINTETICO),
            new Elemento(113, "Ununtrio", "Uut", "IIIA", 13, 
                new BigDecimal("284"), 7, Estado.DESCONOCIDO, 
                Propiedad.DESCONOCIDO, Presencia.SINTETICO),
            new Elemento(114, "Flevorio", "Fl", "IVA", 14, 
                new BigDecimal("289"), 7, Estado.DESCONOCIDO, 
                Propiedad.METALTRANSICION, Presencia.SINTETICO),
            new Elemento(115, "Ununpentio", "Uup", "VA", 15, 
                new BigDecimal("288"), 7, Estado.DESCONOCIDO, 
                Propiedad.DESCONOCIDO, Presencia.SINTETICO),
            new Elemento(116, "Livermorio", "Lv", "VIA", 16, 
                new BigDecimal("292"), 7, Estado.DESCONOCIDO, 
                Propiedad.METALTRANSICION, Presencia.SINTETICO),
            new Elemento(117, "Ununseptio", "Uus", "VIIA", 17, 
                new BigDecimal("294"), 7, Estado.DESCONOCIDO, 
                Propiedad.DESCONOCIDO, Presencia.SINTETICO),
            new Elemento(118, "Ununoctio", "Uuo", "VIIIA", 18, 
                new BigDecimal("294"), 7, Estado.DESCONOCIDO, 
                Propiedad.DESCONOCIDO, Presencia.SINTETICO)
        };
    }
    
    private int numero = 0;
    private String nombre = "";
    private String simbolo = "";
    private String grupo = "";
    private BigDecimal peso = BigDecimal.ZERO;
    private int periodo = 0;
    private int grupoNuevo = 0;
    
    //Tipo
    private boolean tierraRara = false;
    private boolean alcalino = false;
    private boolean alcalinoTerreo = false;
    private boolean metalTransicion = false;
    private boolean lantanidos = false;
    private boolean actinidos = false;
    private boolean metalBasico = false;
    private boolean otroNoMetal = false;
    private boolean gasNoble = false;
    private boolean halogeno = false;
    private boolean semiMetal = false;
    private boolean metal = false;
    private boolean noMetal = false;
    
    //Estado
    private boolean solido = false;
    private boolean liquido = false;
    private boolean gas = false;
    private boolean primordial = false;
    private boolean rastroRadioisotopico = false;
    private boolean sintetico = false;

    public static Elemento[] obtenerElementos() {
        Elemento[] copiaElementos = new Elemento[elementos.length];
        System.arraycopy(elementos, 0, copiaElementos, 0, elementos.length);
        return copiaElementos;
    }
    
    public Elemento() {} 
    
    public Elemento(int numero, String nombre, String simbolo, String grupo, 
            int grupoNuevo, BigDecimal peso, int periodo, Estado estado, 
            Propiedad propiedad) {
        this(numero, nombre, simbolo, grupo, grupoNuevo, peso, periodo, estado,
                propiedad, Presencia.PRIMORDIAL);
    }
    
    public Elemento(int numero, String nombre, String simbolo, String grupo, 
            int grupoNuevo, BigDecimal peso, int periodo, Estado estado, 
            Propiedad propiedad, Presencia presencia) {
        this.numero = numero;
        this.nombre = nombre.intern();
        this.simbolo = simbolo.intern();
        this.grupo = grupo.intern();
        this.grupoNuevo = grupoNuevo;
        
        if(peso != null) {
            this.peso = peso;
        }
        
        this.periodo = periodo;
        
        switch(estado) {
            case GAS: gas = true;
                break;
            case LIQUIDO: liquido = true;
                break;
            case SOLIDO: solido = true;
                break;
            default: break;
        }
        
        switch(presencia) {
            case PRIMORDIAL: primordial = true;
                break;
            case RASTRORADIOISOTOPO: rastroRadioisotopico = true;
                break;
            case SINTETICO: sintetico = true;
                break;
            default: break;
        }
        
        switch(propiedad) {
            case ACTINIDO: actinidos = true;
                break;
            case ALCALINO: alcalino = true;
                break;
            case ALCALINOTERREO: alcalinoTerreo = true;
                break;
            case GASNOBLE: gasNoble = true;
                break;
            case HALOGENO: halogeno = true;
                break;
            case LANTANIDO: lantanidos = true;
                break;
            case METALBASICO: metalBasico = true;
                break;
            case METALTRANSICION: metalTransicion = true;
                break;
            case OTRONOMETAL: otroNoMetal = true;
                break;
            case SEMIMETAL: semiMetal = true;
                break;
            default: break;
                
        }
        
        //Saber si es metal o no
        switch(propiedad) {
            case ALCALINO:
            case ALCALINOTERREO:
            case ACTINIDO:
            case LANTANIDO:
            case METALTRANSICION:
            case METALBASICO:
                metal = true;
                break;
            case OTRONOMETAL:
            case GASNOBLE:
            case HALOGENO:
                noMetal = true;
                break;
            default: break;
        }
    }
    
    /**
     * Método principal de Elemento.
     * 
     * @param args Los argumentos del programa.
     */
    public static void main(String[] args) {
        for (Elemento elemento : elementos) {
            System.out.println(elemento);
        }
    }

    public static Elemento buscarElemento(int numeroElemento) {
        numeroElemento--;
        
        if(numeroElemento < 0 || numeroElemento >= elementos.length) {
            return null;
        } else {
            return elementos[numeroElemento];
        }
    }
    
    public static Elemento buscarElemento(String nombreSimbolo) {
        if(nombreSimbolo == null) {
            return null;
        } else {
            nombreSimbolo = nombreSimbolo.intern();
            
            for (Elemento elemento : elementos) {
                if(elemento.nombre.equalsIgnoreCase(nombreSimbolo) 
                        || elemento.simbolo.equalsIgnoreCase(nombreSimbolo)) {
                    return elemento;
                }
            }
            
            return null;
        }
    }
    
    public Estado obtenerEstado() {
        if(gas) {
            return Estado.GAS;
        } else if(liquido) {
            return Estado.LIQUIDO;
        } else if(solido) {
            return Estado.SOLIDO;
        } else {
            return Estado.DESCONOCIDO;
        }
    }
    
    public Presencia obtenerPresenciaNatural() {
        if(primordial) {
            return Presencia.PRIMORDIAL;
        } else if(rastroRadioisotopico) {
            return Presencia.RASTRORADIOISOTOPO;
        } else if(sintetico) {
            return Presencia.SINTETICO;
        } else {
            return Presencia.DESCONOCIDO;
        }
    }
    
    public Propiedad obtenerPropiedadQuimica() {
        if(metal) {
            if(alcalino) {
                return Propiedad.ALCALINO;
            } else if(alcalinoTerreo) {
                return Propiedad.ALCALINOTERREO;
            } else if(lantanidos) {
                return Propiedad.LANTANIDO;
            } else if(actinidos) {
                return Propiedad.ACTINIDO;
            } else if(metalTransicion) {
                return Propiedad.METALTRANSICION;
            } else if(metalBasico) {
                return Propiedad.METALBASICO;
            } else {
                return Propiedad.DESCONOCIDO;
            }
        } else if(semiMetal) {
            return Propiedad.SEMIMETAL;
        } else if(noMetal) {
            if(otroNoMetal) {
                return Propiedad.OTRONOMETAL;
            } else if(halogeno) {
                return Propiedad.HALOGENO;
            } else if(gasNoble) {
                return Propiedad.GASNOBLE;
            } else {
                return Propiedad.DESCONOCIDO;
            }
        } else {
            return Propiedad.DESCONOCIDO;
        }
    }
    
    public int obtenerNumero() {
        return numero;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public String obtenerSimbolo() {
        return simbolo;
    }

    public String obtenerGrupo() {
        return grupo;
    }

    public BigDecimal obtenerPeso() {
        return peso;
    }

    public int obtenerPeriodo() {
        return periodo;
    }

    public int obtenerGrupoNuevo() {
        return grupoNuevo;
    }

    public boolean esTierraRara() {
        return tierraRara;
    }

    public boolean esAlcalino() {
        return alcalino;
    }

    public boolean esAlcalinoTerreo() {
        return alcalinoTerreo;
    }

    public boolean esMetalTransicion() {
        return metalTransicion;
    }

    public boolean esLantanidos() {
        return lantanidos;
    }

    public boolean esActinidos() {
        return actinidos;
    }

    public boolean esMetalBasico() {
        return metalBasico;
    }

    public boolean esOtroNoMetal() {
        return otroNoMetal;
    }

    public boolean esGasNoble() {
        return gasNoble;
    }

    public boolean esHalogeno() {
        return halogeno;
    }

    public boolean esSemiMetal() {
        return semiMetal;
    }

    public boolean esMetal() {
        return metal;
    }

    public boolean esNoMetal() {
        return noMetal;
    }

    public boolean esSolido() {
        return solido;
    }

    public boolean esLiquido() {
        return liquido;
    }

    public boolean esGas() {
        return gas;
    }

    public boolean esRastroRadioisotopico() {
        return rastroRadioisotopico;
    }

    public boolean esSintetico() {
        return sintetico;
    }

    @Override
    public String toString() {
        StringBuilder salida = new StringBuilder(50);
        salida = salida.append("{nombre: ").append(nombre).append(", Símbolo: ")
                .append(simbolo).append(", Número: ").append(numero)
                .append(", Peso: ");
        
        if(peso == BigDecimal.ZERO || peso == null || peso.compareTo(BigDecimal
                .ZERO) == 0) {
            salida = salida.append("Desconocido");
        } else {
            salida = salida.append(peso.toString());
        }
        
        salida = salida.append(", Grupo: ").append(grupoNuevo).append(" (")
                .append(grupo).append("), Período: ").append(periodo)
                .append(", Propiedad: ");
        
        //Propiedades Químicas
        if(metal) {
            if(alcalino) {
                salida = salida.append("Alcalino");
            } else if(alcalinoTerreo) {
                salida = salida.append("Alcalinotérreo");
            } else if(lantanidos) {
                salida = salida.append("Lantanido");
            } else if(actinidos) {
                salida = salida.append("Actinido");
            } else if(metalTransicion) {
                salida = salida.append("Metal de Transición");
            } else if(metalBasico) {
                salida = salida.append("Metal Básico(o del Bloque P)");
            } else {
                salida = salida.append("Metal Desconocido");
            }
        } else if(semiMetal) {
            salida = salida.append("Semimetal");
        } else if(noMetal) {
            if(otroNoMetal) {
                salida = salida.append("No Metal");
            } else if(halogeno) {
                salida = salida.append("Halógeno");
            } else if(gasNoble) {
                salida = salida.append("Gas Noble");
            } else {
                salida = salida.append("No Metal Desconocido");
            }
        } else {
            salida = salida.append("Desconocida");
        }
        
        salida = salida.append(", Presencia Natural: ");
        
        //Presencia Natural
        if(primordial) {
            salida = salida.append("Primordial");
        } else if(rastroRadioisotopico) {
            salida = salida.append("Rastro Radioisótopo");
        } else if(sintetico) {
            salida = salida.append("Sintético");
        } else {
            salida = salida.append("Desconocida");
        }
        
        salida = salida.append(", Estado(en condiciones normales): ");
        
        //Estado (en condiciones normales)
        if(gas) {
            salida = salida.append("Gaseoso");
        } else if(liquido) {
            salida = salida.append("Líquido");
        } else if(solido) {
            salida = salida.append("Sólido");
        } else {
            salida = salida.append("Desconocido");
        }
        
        return salida.append("}").toString().intern();
    }
}
