public class SemaphoreImplClassHoare implements SemaphoreInterface {
    // Variable count représente le nombre de permis disponibles dans le sémaphore
    private int count = 0;
    // Variable waiting représente le nombre de threads en attente d'un permis
    private int waiting = 0;

    // Constructeur par défaut
    public SemaphoreImplClassHoare() {
    }

    // Méthode up() qui augmente le nombre de permis de 1 et notifie un thread en attente
    @Override
    public synchronized void up() {
        count++;
        notify();
    }

    // Méthode down() qui bloque le thread courant si aucun permis n'est disponible
    // Si count <= 0, cela signifie qu'aucun permis n'est disponible, donc on incrémente la valeur de waiting
    // et on met le thread courant en attente avec wait()
    // Si un permis est disponible, on décrémente count
    @Override
    public synchronized void down() {
        try {
            if (count <= 0) {
                waiting++;
                wait();
            }
            count--;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    // Méthode releaseAll() qui débloque tous les threads en attente sur le sémaphore
    // Stocke le nombre de threads en attente dans la variable nbThread
    // Réinitialise la valeur de waiting à 0 et notifie tous les threads en attente avec notifyAll()
    // Renvoie le nombre de threads qui ont été débloqués
    @Override
    public synchronized int releaseAll() {
        int nbThread = waiting;
        waiting = 0;
        notifyAll();
        return nbThread;
    }
}
