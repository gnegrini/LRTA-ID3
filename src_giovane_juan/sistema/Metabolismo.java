package sistema;

import java.util.ArrayList;

public class Metabolismo {
    
    private int energia;
    private final ArrayList<Fruta> frutasComidas;     // armazena as frutas comidas
    private final ArrayList<Fruta> frutasGuardadas;
    
    public Metabolismo() {
        frutasComidas = new ArrayList<>();
        frutasGuardadas = new ArrayList<>();        
    }
    
    public int getEnergia() {
        return energia;
    }

    public void resetMetabolismo() {
        this.energia = 350;
        frutasGuardadas.clear();
    }
    
    public void setEnergia(int energia) {
        this.energia = energia;
    }


    boolean comerOuGuardar(boolean testar, Fruta fruit, float distEst) {
        float energiaNec;
        int energiaFruta;
        
        // Se for modo treino, comer todas as frutas
        if(!testar){
            comer(fruit);
            return true;
        } 
        
        else    {
            // Com base na energia estimada necessaria e da fruta, decide se precisa aumentar ou 
            // diminuir energia para chegar ao destino;
            energiaNec = estEnergiaNecessaria(distEst);
            energiaFruta = estimarEnergiaFruta(fruit);            
            if (energiaNec > energia) {                
                // come se a energia fornecida pela fruta é maior que gasta para comer                
                if(energiaFruta > 15){
                    comer(fruit);
                    return true;
                }                
            } else {
                
                // Se tem mais que a energia necessaria, elemina energia
                if (energia >= (float) energiaNec){
                    System.out.println("Eleminando energia");
                    // Se gasta mais para comer do que ganha, come a fruta
                    if(energiaFruta < 15){
                        comer(fruit);
                    }
                    // Se nao, guarda ela
                    else 
                        guardar(fruit);
                    return true;
                }
            }
            
            // Demais casos, deixa a fruta no lugar
            return false;
        }
    }

    void comer(Fruta fruit){
        frutasComidas.add(fruit);
        
        // Adiciona a energia fornecida pela fruta e desconta o gasto por come-la
        energia = energia + fruit.getEnergia() - 15;
    }
    
    private void guardar(Fruta fruit) {
        frutasGuardadas.add(fruit);
    }
    
    // Desconta a energia do movimento e de carregar fruta (75 + 5*frutas guardadas [kcal]);
    void updateEnergia() {                      
        energia = energia - 75 -(frutasGuardadas.size() * 5);                               
    }

    public ArrayList<Fruta> getFrutasComidas() {
        return frutasComidas;
    }        

    public int estimarEnergiaFruta(Fruta fruit){
        //int energiaEst = 0;
        String madureza = fruit.getMadureza();
        String carboidratos = fruit.getCarboidratos();
        String lipideos = fruit.getLipideos();
        String proteinas = fruit.getProteinas();
        String fibras = fruit.getFibras();

        // APENAS PARA TESTEEEE
        return fruit.getEnergia();
    }

    // Estima a energia necessaria para chegar ao destino
    // com base na distancia heuristica e nas frutas carrengadas
    private float estEnergiaNecessaria(float distEst) {
        float energiaEstimada =0;
        
        energiaEstimada = (float) (distEst * 75) + (frutasGuardadas.size()*5);
                
        return (energiaEstimada);
        
    }

}
