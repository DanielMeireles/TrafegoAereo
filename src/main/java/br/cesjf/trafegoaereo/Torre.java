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
    // Exclusão mútua para sincronização da torre com a decolagem ou pouso do avião
    public static final Semaphore mutex = new Semaphore(0);

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
                    // Exclusão mútua para que o while não passe desse ponto até o avião terminar o pouso
                    Torre.mutex.acquire();
                    
                } else {
                    
                    if (Torre.decolar.getQueueLength() > 0) {
                        // Exclusão mútua da pista - Início
                        Torre.pista.acquire();
                        // Libera um avião para decolagem
                        Torre.decolar.release();
                        // Exclusão mútua para que o while não passe desse ponto até o avião terminar a  decolagem
                        Torre.mutex.acquire();
                    }
                    
                }

            }
        
        } catch (InterruptedException ex) {}
        
    }
    
}