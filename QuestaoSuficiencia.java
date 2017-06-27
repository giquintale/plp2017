package suficienciaConcorrencia;
/**
 *
 * @author Gigi
 */
public class QuestaoSuficiencia {

    private double hipotenusa, catOposto, catAdjacente, x, y, z, a;
    public final Object chave = new Object();
    public boolean flagHipotenusa = false;
    

    class T1 extends Thread {

        @Override
        public void run() {
            synchronized (chave) {
                try {
                    System.out.println("Thread T1 iniciando");
                    Thread.sleep(5000);                   
                    x = catOposto + catAdjacente;
                    System.out.println("Thread T1 terminando");
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    class T2 extends Thread {
       
        @Override
        public void run() {
            synchronized (chave) {
                try {
                    System.out.println("Thread T2 iniciando");
                    Thread.sleep(4000);
                    x = catAdjacente + catAdjacente;
                    System.out.println("Thread T2 terminando");
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    class T3 extends Thread {

        @Override
        public void run() {
            synchronized (chave) {
                try {
                    System.out.println("Thread T3 iniciando");
                    Thread.sleep(2000);
                    x = x + y;
                    System.out.println("Thread T3 terminando");
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    class T4 extends Thread {

        @Override
        public void run() {
            synchronized (chave) {
                while (flagHipotenusa){
                    try {
                        chave.wait();
                    } catch (InterruptedException ex) {}
                }
            try {
                System.out.println("Thread T4 iniciando");
                Thread.sleep(1000);
                hipotenusa = Math.sqrt(a);
                System.out.println("Thread T4 terminando");
                flagHipotenusa = true;
                chave.notifyAll();
            } catch (InterruptedException ex) {}
        }
        }
    }

    public void executar() {
        catOposto = 3;
        catAdjacente = 4;
        new T1().start();
        new T2().start();
        new T3().start();
        new T4().start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {}
        synchronized (chave) {
            while(!flagHipotenusa){
                try {
                    chave.wait();
                } catch (InterruptedException ex) {}
            }
            System.out.println("Hipotenusa" + hipotenusa);
            System.out.println("X" + x);
            flagHipotenusa = false;
            chave.notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new QuestaoSuficiencia().executar();
    }
    
    
    
    
}
