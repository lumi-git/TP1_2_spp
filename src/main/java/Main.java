import java.util.concurrent.Exchanger;
import java.util.concurrent.locks.ReentrantLock;

class Main {
    static boolean AliceisOk = true;
    static boolean BobisOk = true;
    static Exchanger<String> exchanger = new Exchanger<String>();
    static String Pong = "pong";
    static String Ping = "ping";

    static int MaxIterations = 3;

    static class Bob implements Runnable {

        public void run() {
            int iter = 0;
            String data = Pong;
            for (int i = 0; i < MaxIterations; i++) {

                print(iter, "Bob has : " + data);
                print(iter, "Bob Going to sleep");
                BobisOk = true;
                try {
                    //bob has to wait for alice to be ready
                    while (! AliceisOk ){print(iter, "Bob Waiting for Alice");}
                    data = exchanger.exchange(data);
                    print(iter, "Bob Exchange completed");
                    BobisOk = false;

                } catch (InterruptedException ex) {

                }
                iter++;
            }
        }
    }

    static class Alice implements Runnable {
        int iter = 0;
        public void run() {
            String data = Ping;
            for (int i = 0; i < MaxIterations; i++) {

                print(iter, "Alice has : " + data);
                print(iter, "Alice Going to sleep ");
                AliceisOk = true;
                try {
                    //alice has to wait for bob to be ready
                    while (! BobisOk ){}
                    data = exchanger.exchange(data);
                    print(iter, "Alice Exchange completed");
                    AliceisOk = false;

                } catch (InterruptedException ex) {

                }
                iter++;
            }
        }
    }


    public static void main(String[] args){

        new Thread(new Alice()).start();

        new Thread(new Bob()).start();
    }



    public static void print(int iter,String s){
        System.out.println("Iterations :" + iter + " "+ s);
    }

}