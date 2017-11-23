/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.util.Random;

/**
 *
 * @author gnegrini
 */
public class Fruta {
    Random rand;
    int[] conjEnergia;
    String[] conjMadureza;
    String[] conjQuantidade;
    
    
    private final String madureza;
    private final String carboidratos;
    private final String fibras;
    private final String proteinas;
    private final String lipideos;
    private final int energia;
    
    public Fruta() {
        // Gera numero aleatorio para sortear o tipo da fruta
        rand = new Random();
        
        // Inicializa os conjuntos com valores possiveis para os atributos
        conjMadureza = new String[] {"verde", "madura", "podre"};
        conjQuantidade = new String[] {"pouca", "moderada", "alta"};
        conjEnergia = new int[]{5, 25, 50, 100};
        
        // Atribui valores aleatorios para os atributos da Fruta
        madureza = conjMadureza[rand.nextInt(3)];
        carboidratos = conjQuantidade[rand.nextInt(3)];
        fibras = conjQuantidade[rand.nextInt(3)];
        proteinas = conjQuantidade[rand.nextInt(3)];
        lipideos = conjQuantidade[rand.nextInt(3)];
        
        // Atribui um valor de energia
        energia = atribuirEnergia();        
    }

    
    // Retorna um valor de Energia conforme 
    // os atributos da Fruta e as regras do enunciado.
    private int atribuirEnergia(){        
        
        if (lipideos.equals("alta") || lipideos.equals("moderada")){
            if (carboidratos.equals("pouca")) {
                if (madureza.equals("podre")) {
                    return 5;
                }
                if (madureza.equals("verde")) {
                    return 25;
                }
                if (madureza.equals("madura")) {
                    return 50;
                }                
            }
            
            if (carboidratos.equals("alta") || carboidratos.equals("moderada")) {
                if (madureza.equals("podre")) {
                    return 25;
                }
                if (madureza.equals("verde")) {
                    return 50;
                }
                if (madureza.equals("madura")) {
                    return 100;
                }
            }
        }
        
        if (lipideos.equals("pouca")) {
            if (carboidratos.equals("pouca")) {
                if (proteinas.equals("alta") && fibras.equals("alta") && !madureza.equals("podre")){
                    return 50;
                }
            }
            
            if (carboidratos.equals("alta") || carboidratos.equals("moderada")) {
                if (madureza.equals("podre")) {
                    return 5;
                }
                if (madureza.equals("verde")) {
                    return 25;
                }
                if (madureza.equals("madura")) {
                    return 100;
                }
            }
        }                               
        
        return 5;
    }
    
    public String getMadureza() {
        return madureza;
    }

    public String getCarboidratos() {
        return carboidratos;
    }

    public String getFibras() {
        return fibras;
    }

    public String getProteinas() {
        return proteinas;
    }

    public String getLipideos() {
        return lipideos;
    }

    public int getEnergia() {
        return energia;
    }

    @Override
    public String toString() {
        return (madureza + ", " + carboidratos + ", " + fibras + ", " + proteinas + ", " + lipideos + ", " + energia);
    }
    
    
}