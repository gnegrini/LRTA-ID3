
package sistema;

import ambiente.*;
import comuns.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author tacla
 */
public class Agente implements CoordenadasGeo {
    Model model;
    Problema prob;        
    Metabolismo met;
    Fruta fruit;
    
    boolean testar;         // indica se o agente está treinando ou testando um treinamento
    boolean manh;
    
    private ArrayList<Fruta> frutasComidas;     // armazena as frutas comidas
    private ArrayList<String> caminho;      // armazena os estados visitados        

    private float custoC;        // armazena o custoC atual do caminho       
    private boolean chegou;        // para a classe Main saber que chegou ao destino        
    private boolean vivo;     // para saber se o agente morreu ou esta vivo
    
    int[] acoesPossiveis;       // armazena as acoes possiveis de um dado estado
    int proxAcao;       // armazena a proxima acao a ser executada    
    
    Random rand;        // para decidir empates no LRTA*
         
    public Agente(Model mdl, boolean manh, boolean test) {
        this.model = mdl;        
        this.testar = test;
        this.manh = manh;
        
        // Inicializa as variaveis
	rand = new Random();       
        frutasComidas = new ArrayList<>();
        caminho = new ArrayList<>();        
        prob = new Problema();
        met = new Metabolismo();
        
       
        //reset();
        // Define os estados
	prob.defEstIni(8, 0);
	prob.defEstObj(2,6);        
	prob.defEstAtu(8,0);
        prob.calcularHeuristica(manh);            
    }
    
    
    /* 
     * Escolhe qual ação será executada em um ciclo de raciocínio
     */
    public int deliberar() {                                

        // nao atingiu objetivo
        if (!prob.testeObjetivo()) {
           
           // Decide a proxima acao e coloca na variavel proxAcao
            decidirAcao();
           
            custoC = custoC + calcularCustoC(proxAcao);
            
            // Atualiza o valor heuristico do estado atual atribuindo o menor Custo
            prob.updateValorHeuristico(lerSensor(), calcularCustoF(proxAcao));
            
            int[] suc  = prob.suc(lerSensor(), proxAcao);
            prob.defEstAtu(suc[0],suc[1]); // atualiza estado do agente
            
            // Executa a proxima acao
            executarIr(proxAcao);
            
//            // Atualiza a energia do Agente para descontar o passo andado
//            met.updateEnergia();

            // Adiciona o novo estado atual ao caminho
            caminho.add("[" + prob.estAtu[0] + "," + prob.estAtu[1] + "]");           
                       
            // Se nao é o estado final (fruta == null)
            if(!prob.testeObjetivo()) {
                // Pega a fruta da posicao atual para analisar            
                fruit = prob.creLab.getFrutaInPos(prob.estAtu);
                if(!testar)
                    frutasComidas.add(fruit);
            }
//            // remove a fruta dos labirintos (modelo e problema)           
//            if(met.comerOuGuardar()){
//                9
//            }
        
            if(testar && (met.getEnergia() < 75)){ // agente em modo de Teste e sem energia
                met.setEnergia(-18);
                vivo = false;
                return (-1);        // mohrreu, encerrar execucao
            }            
        }
        else{        //atingiu o objetivo
            chegou = true;
            return (-1);  
        }
        return 1; //segue o jogo        
    }
    
    /*
    * Atuador: solicita ao agente 'fisico' executar a acao. Atualiza estado.
    */
    public int executarIr(int direcao) {                
        model.ir(direcao);
        return 1; 
    }   
    
    // Sensor: lê posição atual do agente 
    public int[] lerSensor() {
        int[] posAtual = model.lerPos();
	return posAtual;
    }

    
    // Decide a proxima acao com base no LRTA*
    public void decidirAcao() {                
        
        // Indentifica acoes possiveis para do estado atual
        acoesPossiveis = prob.acoesPossiveis(prob.estAtu); 

        System.out.println("estado atual: (" + prob.estAtu[0] + "," + prob.estAtu[1]+")");
        //System.out.println("acoes possiveis: ");                       

        float menorCustoF = 50000;     //inicializando com um valor alto para o f(n')
        for (int i=0; i<acoesPossiveis.length; i++) {                       
            float custoF;      //custo f(n')
            
            // Verifica se a acao é possivel
            if (acoesPossiveis[i]!=-1){     
                // Calcula o custo f(n')
                custoF = calcularCustoF(i);
                
                //System.out.println("Acao: " + acao[i] + " Custo: " + custoF + " Menor Custo: " + menorCustoF);
                
                // Se o  custo f(n') eh menor que o melhor ateh agora,
                // atualiza o custo e a proxima acao
                if(custoF < menorCustoF){
                    menorCustoF = custoF;
                    proxAcao = i;                
                } else
                
                // Se o custo f(n') eh igual,
                // decide aleatoriamente se troca ou nao (50/50)                
                if((custoF == menorCustoF) && (rand.nextInt(2)== 1)){
                    proxAcao = i;
                    System.out.println("Acao Decidida no Empate");                    
                }
            }                
        }    
        System.out.println("Acao Decidida: " + acao[proxAcao]);
    }

    // Calcula o custo f(n') 
    // (soma do custo de fazer a acao do estado atual para chegar no vizinho com a heuristica)
    // f(n') = c(n, a, n') + h(n')
    private float calcularCustoF(int acao) {         
        float custoF;
        // encontra o estado sucessor (vizinho)
        int[] suc = prob.suc(lerSensor(), acao);
        // soma o custo heuristico do vizinho mais o custo de ir pra ele
        
        
        custoF = (float) prob.getValorHeuristico(suc) + calcularCustoC(acao);
        return custoF;
    }
    
    // Calcula o custo de um movimento (acao) horizontal/vertica ou diagonal
    private float calcularCustoC(int acao){        
        if (proxAcao%2==0)   	//direçoes N, L, S e O são numeros pares
            return 1; 
        else
            return (float) 1.5;
    }
    
    public ArrayList<Fruta> getFrutasComidas() {
        return frutasComidas;
    }

    public ArrayList<String> getCaminho() {
        return caminho;
    }

    public int getEnergia() {
        return met.getEnergia();
    }

    public float getCustoC() {
        return custoC;
    }

    public boolean isChegou() {
        return chegou;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void imprimirMatrizHeuristica() {
        prob.imprimirMatrizHeuristica();
    }

    // Funcao para configurar a primeira e novas execucoes
    public void reset() {
        
        //frutasComidas.clear();
        caminho.clear();        
        //prob = new Problema();
        //prob.criarLabirinto();               
        
        
        // Define os estados
	prob.defEstIni(8, 0);
	prob.defEstObj(2,6);        
	prob.defEstAtu(8,0);        
        
        // Posicao inicial do agente
        int[] pos = new int[]{8,0};        
        model.setPos(pos);
        
        // Sorteia um novo conjunto de frutas para as posicoes do lab
        model.sortearFrutas();
        model.getLab().delFrutaInPos(prob.estIni); // deleta a fruta no estado inicial
        model.getLab().delFrutaInPos(prob.estObj); // deleta a fruta no estado objetivo
        
        //met.resetMetabolismo();
        
        // Modelo = Creanca; atualiza as frutas do modelo para o problema
        prob.setCreLab(model.getLab());        
        
        // Custo zero já que nao andou ainda
        custoC = 0;
               
        // Comeca vivo e fora do destino
        vivo = true;                
        chegou = false;
    }

    void imprimirPosFrutas() {
        prob.imprimirPosFrutas();
    }
    
}
    

