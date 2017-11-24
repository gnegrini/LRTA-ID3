/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.util.Scanner; //get keyboard entry
import ambiente.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author tacla
 */
public class Main {
    private static Scanner scanner;
    public static void main(String args[]) {
        // Cria o labirinto = ambiente
        Model mdl = new Model(9, 9);              
        
        // Ciclo de execucao do sistema
        int execucoes = 40;
        int testar = 0;
        int manhattan = 1;        
        
        // Prepara os arquivos para salvar os dados
        FileWriter[] vec = new FileWriter[2];
        FileWriter fileFrutas = null;
        FileWriter fileDesempenho = null;               
        vec = abrirArquivos(fileFrutas, fileDesempenho);
        fileFrutas = vec[0];
        fileDesempenho = vec[1];               
        
        scanner = new Scanner(System.in);  
        
//        System.out.printf("Digite o numero de execucoes: ");
//        execucoes = scanner.nextInt();
//        System.out.printf("Digite 0 para Treinar ou 1 para Testar: ");
//        testar = scanner.nextInt();
//        System.out.printf("Digite 0 para Heuristica Zero ou 1 para Manhattan: ");
//        manhattan = scanner.nextInt();        
//        
//        System.out.printf("Configuracao -- Execucoes: %d, Modo %d, Heuristica: %d \n", execucoes, testar, manhattan);               
//        
        
        // Cria um agente com as caracteristicas do jogo e passa 'testar' e 'manhattan' como boolean        
        Agente ag = new Agente(mdl, (manhattan==1), (testar==1));

        System.out.println("Labirinto");        
        // ag.imprimirMatrizHeuristica();        
        
        for (int i = 0; i < execucoes; i++) {
            System.out.println("Execucao: " + (i+1));                                                
            ag.reset();         // reseta os atributos para uma nova execucao
            ag.imprimirPosFrutas();
            while (ag.deliberar() != -1) {                
                //mdl.desenhar();
                //ag.imprimirMatrizHeuristica();                
            }                                      
            
            
            // Gravar os dados do desempenho do agente (execucao, custo, energia, ...)
            gravarArquivoDesempenho(i, ag, fileDesempenho);
        }
        
        // Gravar as caracteristicas e energia das frutas comidas para aprendizado
        gravarArquivoFrutas(ag, fileFrutas);
        
        // Fecha os arquivos antes de encerrar
        fecharArquivos(fileFrutas, fileDesempenho);
    }

    private static void gravarArquivoFrutas(Agente ag, FileWriter fileWriter) {                
        try{
            for (Fruta frutaComida : ag.getFrutasComidas()) {                
                fileWriter.append(frutaComida.toString());
                fileWriter.append("\n");
                System.out.println(frutaComida);
            }
        }catch (IOException e) {
	            System.out.println("Error in Writing File !!!");
                    e.printStackTrace();
        }                  
    }

    private static void gravarArquivoDesempenho(int exec, Agente ag, FileWriter fileWriter) {
                
        final String COMMA_DELIMITER = ",";
        final String NEW_LINE_SEPARATOR = "\n";
        final String QUOTE = "\"";        
                
        try{
            fileWriter.append(String.valueOf(exec));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(ag.getCustoC()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(ag.getEnergia()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(ag.isChegou()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(ag.isVivo()));
            fileWriter.append(COMMA_DELIMITER);
            
            fileWriter.append(QUOTE);            
            
            String inicio = "[" + ag.prob.estIni[0] +"," + ag.prob.estIni[1] + "]";
            
            fileWriter.append(inicio);
            
            for (String passo : ag.getCaminho()) {                
                fileWriter.append(">");
                fileWriter.append(passo);                
            }
            fileWriter.append(QUOTE);            
            
            fileWriter.append(NEW_LINE_SEPARATOR);
                        
        }catch (IOException e) {
	    System.out.println("Error in Writing File !!!");
            e.printStackTrace();
        }
    }

    private static FileWriter[] abrirArquivos(FileWriter fileFrutas, FileWriter fileDesempenho) {
        FileWriter[] vec = new FileWriter[2];
        
        try{
            fileFrutas = new FileWriter("frutas.arff");
        } catch (IOException e) {
	            System.out.println("Error in Opening File Frutas!!!");
        }
        
        vec[0] = fileFrutas;
        
        try{
            fileDesempenho = new FileWriter("desempenho.csv");
        } catch (IOException e) {
	    System.out.println("Error in Opening File Desempenho !!!");
        }
        
        final String FILE_HEADER = "execucao,custo,energia,chegou,vivo,caminho";
        try{
            fileDesempenho.append(FILE_HEADER);
            fileDesempenho.append("\n");
        } catch (Exception ex) {
            System.out.println("Error in Writing File !!!");
            ex.printStackTrace();
        }
        
        vec[1] = fileDesempenho;
        return vec;
    }

    private static void fecharArquivos(FileWriter fileFrutas, FileWriter fileDesempenho) {
        try {
                fileFrutas.flush();
                fileFrutas.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileFrutas !!!");
                e.printStackTrace();
            }
        
        try {
                fileDesempenho.flush();
                fileDesempenho.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileDesempenho !!!");
                e.printStackTrace();
            }
    }
}
