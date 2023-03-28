import java.util.concurrent.Exchanger;

class Main {
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
                try {

                    data = exchanger.exchange(data);
                    print(iter, "Bob Exchange completed");

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
                try {

                    data = exchanger.exchange(data);
                    print(iter, "Alice Exchange completed");


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