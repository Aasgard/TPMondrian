# TPMondrian - SBD
##Utilisation de l'algorithme de Mondrian (k-Anonymat)

###Objectif du TP

L'objectif de ce TP est l'utilisation de l'algorithme de Mondrian, qui à terme, permet de regrouper les données en classes d'équivalence afin d'anonymiser ces dernières. Le procédé s'essectue en plusieurs étapes.

###Etapes

####Prérequis

Le jeu de données final est constitué de deux quasi-identifiants (QID1 et QID2) et d'une donnée sensible (SD).

#####Création du jeu de données

Les **QID sont générés aléatoirement** pour chaque donnée. SD est choisi aléatoirement dans un tableau de maladie (simulation de donnée sensible). On définit alors les bornes des nombres aléatoires générés ainsi que le nombre de données à créer.

#####
