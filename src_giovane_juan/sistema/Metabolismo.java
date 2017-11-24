package sistema;

public class Metabolismo {
    private int energia;

    public Metabolismo() {
        
    }
    
    public int getEnergia() {
        return energia;
    }

    public void resetMetabolismo() {
        this.energia = 250;
    }
    

    public void setEnergia(int energia) {
        this.energia = energia;
    }
    
    
}
