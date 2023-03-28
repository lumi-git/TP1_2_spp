# TP1_2_spp

## Introduction
Le but de ce TP est d'implémenter quelques principes de la programmation Threadé.


## Exercice 1 : mise en place d'un sémaphore

Vous trouverz l'implémentation du sémaphore dans le fichier `SemaphoreImplClassHoare.java`.


Nous avons implémenter deux versions du sémaphore

- Un avec une linked list, appelée implémentation de Mesa, utilisée notamment en java.

- et une autre avec un counter, permetant de garder en mémoire le nombre de thread qui sont en wait.

### Implémentation de Mesa

