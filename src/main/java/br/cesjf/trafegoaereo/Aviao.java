package br.cesjf.trafegoaereo;

public class Aviao extends Thread {
    
    // Declaração de veriáveis e objetos
    private final int id;
    // Variável para controle do estado taxiando, para saber se é para pouso ou decolagem
    private boolean pousando;
    private Estado estado;

    // Construtor
    public Aviao(int id) {
        this.id = id;
        this.pousando = true;
        this.estado = Estado.CHEGOU;
    }
    
    @Override
    public void run() {
        
        // Variável de controle de quando deve ser finalizada a Thread
            boolean ativo = true;
            
            // Mantém em execução enquanto a Thread não finaliza
            while(ativo){
                
                try {
                    
                    // Análise do estado do avião
                    switch(estado){
                        
                        // Avião chegou no espaço aéreo
                        case CHEGOU:
                            // Impressão
                            System.out.println("O avião "+this.id+" chegou no espaço aéreo do aeroporto");
                            // Faz uma pausa para solicitar a aterrissagem
                            pausa();
                            // Altera o estado
                            this.estado = Estado.ATERRISSANDO;
                            break;

                        // Avião aterrissando
                        case ATERRISSANDO:
                            // Impressão
                            System.out.println("O avião "+this.id+" solicitou aterrissagem à torre de controle");
                            // Acessa o semáforo de pouso
                            Torre.pousar.acquire();
                            // Ao ser liberado o pouso
                            // Impressão
                            System.out.println("Aterrissagem permitida para o avião "+this.id);
                            System.out.println("O avião "+this.id+" está aterrissando");
                            // Executa uma pausa para a aterrissagem
                            aterrissando();
                            // Impressão
                            System.out.println("O avião "+this.id+" terminou a aterrissagem e a pista está livre");
                            // Altera o estado
                            this.estado = Estado.TAXIANDO;
                            // Impressão
                            System.out.println("O avião "+this.id+" está taxiando");
                            // Exclusão mútua da pista - Fim
                            Torre.pista.release();
                            break;

                        // Avião taxiando
                        case TAXIANDO:
                            // Executa uma pausa para taxiar
                            taxiando();
                            // Verifica se o avião está em processo de pouso ou decolagem
                            if(pousando) {
                                // Altera o estado
                                this.estado = Estado.ESTACIONADO;
                                // Altera o valor da variável de verificação
                                this.pousando = false;
                            } else {
                                // Altera o estado
                                this.estado = Estado.DECOLANDO;
                            }
                            break;
                            
                        // Avião estacionado
                        case ESTACIONADO:
                            // Impressão
                            System.out.println("O avião "+this.id+" está estacionado");
                            // Executa uma pausa com o avião estacionado
                            estacionado();
                            // Altera o estado
                            this.estado = Estado.TAXIANDO;
                            // Impressão
                            System.out.println("O avião "+this.id+" está taxiando");
                            break;
                            
                        case DECOLANDO:
                            // Impressão
                            System.out.println("O avião "+this.id+" solicitou decolagem à torre de controle");
                            // Acessa o semáforo de decolagem
                            Torre.decolar.acquire();
                            // Ao ser liberado a decolagem
                            // Impressão
                            System.out.println("Decolagem permitida para o avião "+this.id);
                            System.out.println("O avião "+this.id+" está decolando");
                            // Executa uma pausa para a decolagem
                            decolando();
                            // Impressão
                            System.out.println("O avião "+this.id+" terminou a decolagem e a pista está livre");
                            // Exclusão mútua da pista - Fim
                            Torre.pista.release();
                            // Altera o valor da variável para finalizar a Thread
                            ativo = false;
                            break;
                            
                    }
                
                } catch (InterruptedException ex) {}
                
            }
            
            
        
    }
    
    // Definição do tempo de pausa
    private static void pausa() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {}
    }
    
    // Definição do tempo de pausa
    private static void aterrissando() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {}
    }
    
    // Definição do tempo de pausa
    private static void taxiando() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {}
    }
    
    // Definição do tempo de pausa
    private static void estacionado() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {}
    }
    
    // Definição do tempo de pausa
    private static void decolando() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {}
    }
    
}
