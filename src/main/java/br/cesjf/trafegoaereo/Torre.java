package br.cesjf.trafegoaereo;

import java.util.concurrent.Semaphore;

public class Torre extends Thread {
    
    private final int id;
    // Exclusão mútua para a pista para somente um avião usar por vez
    public static final Semaphore pista = new Semaphore(1);
    // Semáforo de aviões aguardando para decolar
    public static final Semaphore decolar = new Semaphore(0);
    // Semáforo de aviões aguardando para pousar
    public static final Semaphore pousar = new Semaphore(0);

    // Construtor
    public Torre(int id) {
        this.id = id;
    }
    
    @Override
    public void run() {
        
        try {
            
            // Início da torre de controle
            System.out.println("Torre de controle iniciada");

            while(true){
                
                // Priorização das aterrissagens sob as decolagens
                if(Torre.pousar.getQueueLength() > 0) {
                    // Exclusão mútua da pista - Início
                    Torre.pista.acquire();
                    // Libera um avião para pouso
                    Torre.pousar.release();
                } else {
                    if (Torre.decolar.getQueueLength() > 0) {
                        // Exclusão mútua da pista - Início
                        Torre.pista.acquire();
                        // Libera um avião para decolagem
                        Torre.decolar.release();
                    }
                }

            }
        
        } catch (InterruptedException ex) {}
        
    }
    
}
