import java.util.LinkedList;

public class SemaphoreImplClass implements SemaphoreInterface {

  // Nombre de permis disponibles dans le sémaphore
  private int permits;
  // Liste des threads en attente sur le sémaphore
  private final LinkedList<Thread> waitingThreads;

  // Constructeur qui initialise le nombre de permis à 0 et crée une nouvelle LinkedList pour les threads en attente
  public SemaphoreImplClass() {
    permits = 0;
    waitingThreads = new LinkedList<>();
  }

  // Méthode up() qui augmente le nombre de permis de 1
  // Si des threads sont en attente, elle interrompt le premier thread de la liste
  // Si la liste est vide, elle appelle notify() pour réveiller d'autres threads en attente
  @Override
  public synchronized void up() {
    permits++;
    if (!waitingThreads.isEmpty()) {
      waitingThreads.removeFirst().interrupt();
    } else {
      notify();
    }
  }

  // Méthode down() qui bloque le thread courant s'il n'y a pas de permis disponibles
  // Ajoute le thread courant à la liste des threads en attente et appelle wait() pour attendre qu'un permis soit disponible
  // Lorsque le thread courant est interrompu, il vérifie s'il dispose d'un permis et, le cas échéant, se retire de la liste des threads en attente
  // Décrémente enfin le nombre de permis de 1
  @Override
  public synchronized void down() {
    while (permits == 0) {
      try {
        waitingThreads.add(Thread.currentThread());
        wait();
      } catch (InterruptedException e) {
        if (permits > 0) {
          waitingThreads.remove(Thread.currentThread());
          break;
        }
      }
    }
    permits--;
  }

  /**Méthode releaseAll() qui débloque tous les threads en attente sur le sémaphore
  Parcourt la liste des threads en attente, interrompt chaque thread en attente, puis vide la liste
  Incrémente ensuite le nombre de permis du nombre de threads libérés et renvoie ce nombre
  **/
  @Override
  public synchronized int releaseAll() {
    int releasedThreads = waitingThreads.size();
    for (Thread waitingThread : waitingThreads) {
      waitingThread.interrupt();
    }
    waitingThreads.clear();
    permits += releasedThreads;
    return releasedThreads;
  }
}
