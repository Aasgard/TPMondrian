# TPMondrian - SBD
##Utilisation de l'algorithme de Mondrian (k-Anonymat)

###Objectif du TP

L'objectif de ce TP est l'utilisation de l'algorithme de Mondrian, qui à terme, permet de regrouper les données en classes d'équivalence afin d'anonymiser ces dernières. Le procédé s'essectue en plusieurs étapes.

###Etapes

####Prérequis

Le jeu de données final est constitué de **deux quasi-identifiants (QID1 et QID2)** et d'**une donnée sensible (SD)**.

#####Création du jeu de données

Les **QID sont générés aléatoirement** pour chaque donnée. SD est choisi aléatoirement dans un tableau de maladie (simulation de donnée sensible). On définit alors les bornes des nombres aléatoires générés ainsi que le nombre de données à créer.

#####Algorithme de Mondrian

Une fois les données crées, on peut passer à la suite, l'algorithme de Mondrian. Celui-ci va scinder le jeu de données en plusieurs groupes de données respectant la condition suivante :

>La cardinalité de chaque groupe de données doit être supérieure ou égale au nombre k fourni.

Le nombre k désigne le nombre minimum de de données que doit comporter chaque groupe. Par exemple, pour satisfaire le 2-Anonymat, chaque groupe doit avoir au moins 2 éléments.

#####Etapes intégrant l'algorithme

######Condition de continuité

Pour continuer l'analyse de l'algorithme, il faut que la cardinalité des ensembles un tour en avance soit supérieur ou égal à k.

######chooseDimension()

Cette première étape permet de déterminer la dimension (colonne) sur laquelle faire la coupe. Pour ce faire, il faut trouver la plus grande étendue entre les deux QID (différence entre la valeur maximale et la valeur minimale de chaque colonne).
Le réssultat sera stocké dans une variable et sera utilisé juste après.

######frequencySet(partition, dimension)

La seconde étape consiste à regrouper les valeurs indetiques entre elles sur la colonne de la dimension choisie (équivalent du GROUP BY SQL).
Par exemple, si la dimension est 1, on regroupe les données indentiques sur la colonne 1 et on indique en face son nombre d'occurences.
Pour faciliter la suite, on peut éventuellement le trier par ordre croissante des clefs.

######findMedian(frequencySet)

Cette étape a pour but de trouver la médiane du jeu de données. Il faut alors organiser les données par ordre croissante des clefs (si celà n'a pas été fait à l'étape précédente), pour ensuite faire le cumul des de leurs valeurs. Une fois que cette somme dépasse la moitié du nombre de données présentes dans le tableau, on renvoie alors la clef responsable.

######Scindement des données

Pour constituer nos deux parties de données (Gauche et Droite), on range dans la partie Gauche tout les couples dont la colonne dimension est inférieure ou égale à la médiane, et a Droite tout les couples supérieurs.

######Récursivité

Pour terminer la fonction, il faut appliquer cette algorithme de façon récursive aux deux parties (Gauche et Droite).

#####Résultat final

Pour terminer l'analyse, pour que le jeu de données initial soit considéré comme K-Anonyme, il faut que la taille d'aucune Classe d'Equivalence soit inférieure strictement à n ou supérieur strictement à (k*2+1).
