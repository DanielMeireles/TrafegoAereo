package br.cesjf.trafegoaereo;

public class TrafegoAereo {

    public static void main(String[] args) {
        
        // Instanciação da torre de controle
        Torre torre = new Torre(1);
        // Inicialização da Thread da torre de controle
        torre.start();
        
        // Contador para o id de avião
        int i = 0;
        
        // Cliação dos aviões
        while(true) {
            
            // Executa o método que da uma pausa entre as criações de aviões
            pausa();
            
            // Adiciona valor ao contador
            i++;
            
            // Instanciação do avião
            Aviao aviao = new Aviao(i);
            // Inicialização da Thread do avião
            aviao.start();
            
        }
        
    }
    
    // Definição do tempo entre a geração dos aviões
    private static void pausa() {
        try {
            Thread.sleep((long) Math.round(Math.random() * 5000));
        } catch (InterruptedException e) {}
    }
    
}
