import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Mondrian {
	
	/* Objet permettant de simuler les classes d'�quivalence */
	private ResultatJDD ensembleDecoupe;
	private int nbParcoursJDD;
	
	/* Constructeur qui initialise les CE */
	public Mondrian() {
		this.ensembleDecoupe = new ResultatJDD();
		this.nbParcoursJDD = 0;
	}
	
	public ResultatJDD getRes(){
		return this.ensembleDecoupe;
	}
	
	/* Fonction permettant de retourner la colonne qui va servir de Dimension */
	/**
	 * @param data : jeu de donn�es g�n�r� al�atoirement
	 * @return La dimension du jeu de donn�es
	 */
	public int chooseDimension(ArrayList<DataLine> data){
		/* On initialise les variables min et max de chaque colonne � la premi�re valeur rencontr�e dans la liste de donn�es */
		int minQID1 = data.get(0).getQid1();
		int maxQID1 = data.get(0).getQid1();
		int minQID2 = data.get(0).getQid2();
		int maxQID2 = data.get(0).getQid2();
		/* On parcours les donn�es et on change le Min et/le Max si on rencontre plus petit / plus grand */
		for (DataLine dataLine : data) {
			if(minQID1 > dataLine.getQid1()){
				minQID1 = dataLine.getQid1();
			}
			else if(maxQID1 < dataLine.getQid1()){
				maxQID1 = dataLine.getQid1();
			}
			if(minQID2 > dataLine.getQid2()){
				minQID2 = dataLine.getQid2();
			}
			else if(maxQID2 < dataLine.getQid2()){
				maxQID2 = dataLine.getQid2();
			}
		}
		this.nbParcoursJDD++;

		/* On retourne la plus grande �tendue entre les deux colonnes (QID) */
		if(maxQID1-minQID1 > maxQID2 - minQID2){ return 1; }else{ return 2; }
	}
	
	/* Fonction permettant d'obtenir un tableau associatif (clef,valeur) */
	/* La clef est la colonne dimension et sa valeur le nombre d'occurences de cette derni�re */
	/**
	 * @param data : les donn�es de base (jeu de donn�es g�n�r� al�atoirement)
	 * @param dim : dimension du jeu de donn�es
	 * @return Renvoie un HashMap des QID unique sur la colonne dimension et son nombre d'occurences associ�
	 */
	public HashMap<Integer,Integer> frequencySet(ArrayList<DataLine> data, int dim){
		/* Cr�ation du HashMap de retour */
		HashMap<Integer,Integer> fs = new HashMap<Integer,Integer>();

		/* Parcours des donn�es en entr�e */
		for (DataLine dataLine : data) {
			/* Si la clef que l'on parcourt n'est pas dans le HashTable, on l'ajoute et on met son occurence � 1 */
			if(!fs.containsKey(dataLine.getColonne(dim))){
				fs.put(dataLine.getColonne(dim),1);
				/* Sinon (si il est pr�sent), on incr�mente de 1 son nombre d'occurences */
			}else{ fs.put(dataLine.getColonne(dim), fs.get(dataLine.getColonne(dim)) + 1); }
		}
		
		this.nbParcoursJDD++;

		/* Retour du HashTable compos� */
		return fs;
	}
	
	/* Fonction renvoyant lla m�diane de la s�rie statistique initiale */
	/**
	 * @param fs : une HashMap de clef/valeurs (valeurs uniques de la colonne dimension / nombre d'occurences)
	 * @return Retourne la m�diane de la s�rie statistique initiale
	 */
	public int findMedian(HashMap<Integer,Integer> fs){
		/* Initialisation de la future somme cumul�e des valeurs du HashTable */
		int cumulValeurs = 0;
		/* Initialisation de la valeur m�diane */
		int cutValue = 0;
		/* Transformation du HashMap non tri� en TreeMap (pour ranger les clefs par ordre croissant) */
		Map<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(fs);
		
		/* Initialisation du nombre de donn�es totales dans le HashTable */
		double totalValues = 0;
		
		/* Parcours du jeu de donn�es (TreeMap) */
		for(Entry<Integer, Integer> entry : sortedMap.entrySet()) {
			/* Stockage de la Value parcourue */
			int value = entry.getValue();
			/* On l'ajoute au nombre total de valeurs */
			totalValues += value;
		}
		this.nbParcoursJDD++;

		/* Parcours du jeu de donn�es (TreeMap) */
		for(Entry<Integer, Integer> entry : sortedMap.entrySet()) {
			/* Stockage de la clef de de sa valeur associ�e */
			int key = entry.getKey();
			int value = entry.getValue();
			
			/* On additionne les valeurs pour essayer de d�passer la moiti� de la s�rie (en terme de cardinalit�) */
			cumulValeurs = cumulValeurs + value;
			
			/* Quand on d�passe la moiti� de la s�rie */
			if(cumulValeurs >= Math.round(totalValues/2)){
				/* On stock la clef qui indique le d�passement de la somme cumul�e. Ce sera notre m�dianne */
				cutValue = key;
				break;
			}
		}
		this.nbParcoursJDD++;
		/* On retourne la m�diane */
		return cutValue;
	}
	
	/* Fonction principale de Mondrian. Elle int�gre les fonctions cr�es plus haut. */
	/**
	 * @param data : jeu de donn�es intiale (g�n�r� al�atoirement)
	 * @param k : k-anonymat souhait�
	 * @return Retourne un ensemble de donn�es (Stockage des diff�rentes zone du rep�re orthonormal stockant les pseudo classes d'�quivalence)
	 */
	public ArrayList<ArrayList<DataLine>> doMondrian(ArrayList<DataLine> data, int k){
		/* Si data n'est pas Mondrianisable */
		if(!isCutable(data, k)){
			/* On ajoute le data dans l'ensemble des classes d'�quivalence */
			this.ensembleDecoupe.add(data);
		}else{
			/* D�claration de la dimension */
			int dimension = chooseDimension(data);
			/* D�claration des fr�quences des clefs dimensions */
			HashMap<Integer,Integer> fs = frequencySet(data, dimension);
			/* D�claration de la m�diane de data */
			int splitVal = findMedian(fs);
			/* D�claration de deux ensembles pour la partie r�cursive (Gauche et Droite) */
			ArrayList<DataLine> L = new ArrayList<DataLine>();
			ArrayList<DataLine> R = new ArrayList<DataLine>();

			/* Parcours du jeu de donn�es */
			for (DataLine dataLine : data) {
				/* Si la valeur de l'�lement de la colonne dimension est inf�rieure ou �gale � celle de la dimension */
				if(dataLine.getColonne(dimension) <= splitVal){
					/* On l'ajoute � Gauche */
					L.add(dataLine);
				}else{
					/* Sinon on l'ajoute � Droite */
					R.add(dataLine);
				}
			}
			
			this.nbParcoursJDD++;

			System.out.println("Colonne dimension choisie : " + dimension);
			System.out.println("Jeu de Gauche (L) avec " + L.size() +" valeurs : " + L.toString());
			System.out.println("Jeu de Droite (R) avec " + R.size() +" valeurs : " + R.toString() + "\n");
			
			/* On appelle de fa�on r�cursive doMondrian sur la partie Gauche et Droite (L & R) */
			doMondrian(L, k);
			doMondrian(R, k);
		}
		
		/* On retourne le jeu de classes d'�quivalence */
		return this.ensembleDecoupe;
	}
	
	/* Fonction permettant de renvoyer si le jeu de donn�es en entr�e est coupable */
	/* Autrement dit, on essaye d'avoir un "Mondrian" d'avance afin de savoir un coup en avance si le jeu de donn�es en entr�e et d�coupable selone l'algo de Mondrian */
	/**
	 * @param data : jeu de donn�es servant � l'analyse
	 * @param k : k-anonymat choisi
	 * @return Retourne True ou False selon si le jeu de donn�es en entr�e peut �tre coup�
	 */
	public boolean isCutable(ArrayList<DataLine> data, int k){
		int dimension = chooseDimension(data);
		HashMap<Integer,Integer> fs = frequencySet(data, dimension);
		int splitVal = findMedian(fs);
		
		ArrayList<DataLine> L = new ArrayList<DataLine>();
		ArrayList<DataLine> R = new ArrayList<DataLine>();

		for (DataLine dataLine : data) {
			if(dataLine.getColonne(dimension) <= splitVal){
				L.add(dataLine);
			}else{
				R.add(dataLine);
			}
		}
		this.nbParcoursJDD++;
		
		/* Il faut que la taille soit sup�rieure � k */
		return (L.size() >= k) && (R.size() >= k);
	}
	
	/* Retourne le nombre de parcours du jeu de donn�es */
	public int getNbParcours(){
		return this.nbParcoursJDD;
	}
	
}
