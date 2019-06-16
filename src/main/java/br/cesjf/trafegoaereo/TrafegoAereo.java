package br.cesjf.trafegoaereo;

public class TrafegoAereo {

    public static void main(String[] args) {
        
        // Instanciação da torre de controle
        Torre torre = new Torre(1);
        torre.start();
        
        // Contador para o id de avião
        int i = 0;
        
        // Cliação dos aviões
        while(true) {
            
            // Instanciação do avião e inicialização de sua Thread
            Aviao aviao = new Aviao(i);
            aviao.start();
            
            // Adiciona valor ao contador
            i++;
            
            // Executa o método que da uma pausa entre as criações de aviões
            pausa();
        }
        
    }
    
    // Definição do tempo entre a geração dos aviões
    private static void pausa() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 6000));
        } catch (InterruptedException e) {}
    }
    
}
