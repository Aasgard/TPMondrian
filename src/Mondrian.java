import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Mondrian {
	
	/* Objet permettant de simuler les classes d'équivalence */
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
	 * @param data : jeu de données généré aléatoirement
	 * @return La dimension du jeu de données
	 */
	public int chooseDimension(ArrayList<DataLine> data){
		/* On initialise les variables min et max de chaque colonne à la première valeur rencontrée dans la liste de données */
		int minQID1 = data.get(0).getQid1();
		int maxQID1 = data.get(0).getQid1();
		int minQID2 = data.get(0).getQid2();
		int maxQID2 = data.get(0).getQid2();
		/* On parcours les données et on change le Min et/le Max si on rencontre plus petit / plus grand */
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

		/* On retourne la plus grande étendue entre les deux colonnes (QID) */
		if(maxQID1-minQID1 > maxQID2 - minQID2){ return 1; }else{ return 2; }
	}
	
	/* Fonction permettant d'obtenir un tableau associatif (clef,valeur) */
	/* La clef est la colonne dimension et sa valeur le nombre d'occurences de cette dernière */
	/**
	 * @param data : les données de base (jeu de données généré aléatoirement)
	 * @param dim : dimension du jeu de données
	 * @return Renvoie un HashMap des QID unique sur la colonne dimension et son nombre d'occurences associé
	 */
	public HashMap<Integer,Integer> frequencySet(ArrayList<DataLine> data, int dim){
		/* Création du HashMap de retour */
		HashMap<Integer,Integer> fs = new HashMap<Integer,Integer>();

		/* Parcours des données en entrée */
		for (DataLine dataLine : data) {
			/* Si la clef que l'on parcourt n'est pas dans le HashTable, on l'ajoute et on met son occurence à 1 */
			if(!fs.containsKey(dataLine.getColonne(dim))){
				fs.put(dataLine.getColonne(dim),1);
				/* Sinon (si il est présent), on incrémente de 1 son nombre d'occurences */
			}else{ fs.put(dataLine.getColonne(dim), fs.get(dataLine.getColonne(dim)) + 1); }
		}
		
		this.nbParcoursJDD++;

		/* Retour du HashTable composé */
		return fs;
	}
	
	/* Fonction renvoyant lla médiane de la série statistique initiale */
	/**
	 * @param fs : une HashMap de clef/valeurs (valeurs uniques de la colonne dimension / nombre d'occurences)
	 * @return Retourne la médiane de la série statistique initiale
	 */
	public int findMedian(HashMap<Integer,Integer> fs){
		/* Initialisation de la future somme cumulée des valeurs du HashTable */
		int cumulValeurs = 0;
		/* Initialisation de la valeur médiane */
		int cutValue = 0;
		/* Transformation du HashMap non trié en TreeMap (pour ranger les clefs par ordre croissant) */
		Map<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(fs);
		
		/* Initialisation du nombre de données totales dans le HashTable */
		double totalValues = 0;
		
		/* Parcours du jeu de données (TreeMap) */
		for(Entry<Integer, Integer> entry : sortedMap.entrySet()) {
			/* Stockage de la Value parcourue */
			int value = entry.getValue();
			/* On l'ajoute au nombre total de valeurs */
			totalValues += value;
		}
		this.nbParcoursJDD++;

		/* Parcours du jeu de données (TreeMap) */
		for(Entry<Integer, Integer> entry : sortedMap.entrySet()) {
			/* Stockage de la clef de de sa valeur associée */
			int key = entry.getKey();
			int value = entry.getValue();
			
			/* On additionne les valeurs pour essayer de dépasser la moitié de la série (en terme de cardinalité) */
			cumulValeurs = cumulValeurs + value;
			
			/* Quand on dépasse la moitié de la série */
			if(cumulValeurs >= Math.round(totalValues/2)){
				/* On stock la clef qui indique le dépassement de la somme cumulée. Ce sera notre médianne */
				cutValue = key;
				break;
			}
		}
		this.nbParcoursJDD++;
		/* On retourne la médiane */
		return cutValue;
	}
	
	/* Fonction principale de Mondrian. Elle intègre les fonctions crées plus haut. */
	/**
	 * @param data : jeu de données intiale (généré aléatoirement)
	 * @param k : k-anonymat souhaité
	 * @return Retourne un ensemble de données (Stockage des différentes zone du repère orthonormal stockant les pseudo classes d'équivalence)
	 */
	public ArrayList<ArrayList<DataLine>> doMondrian(ArrayList<DataLine> data, int k){
		/* Si data n'est pas Mondrianisable */
		if(!isCutable(data, k)){
			/* On ajoute le data dans l'ensemble des classes d'équivalence */
			this.ensembleDecoupe.add(data);
		}else{
			/* Déclaration de la dimension */
			int dimension = chooseDimension(data);
			/* Déclaration des fréquences des clefs dimensions */
			HashMap<Integer,Integer> fs = frequencySet(data, dimension);
			/* Déclaration de la médiane de data */
			int splitVal = findMedian(fs);
			/* Déclaration de deux ensembles pour la partie récursive (Gauche et Droite) */
			ArrayList<DataLine> L = new ArrayList<DataLine>();
			ArrayList<DataLine> R = new ArrayList<DataLine>();

			/* Parcours du jeu de données */
			for (DataLine dataLine : data) {
				/* Si la valeur de l'élement de la colonne dimension est inférieure ou égale à celle de la dimension */
				if(dataLine.getColonne(dimension) <= splitVal){
					/* On l'ajoute à Gauche */
					L.add(dataLine);
				}else{
					/* Sinon on l'ajoute à Droite */
					R.add(dataLine);
				}
			}
			
			this.nbParcoursJDD++;

			System.out.println("Colonne dimension choisie : " + dimension);
			System.out.println("Jeu de Gauche (L) avec " + L.size() +" valeurs : " + L.toString());
			System.out.println("Jeu de Droite (R) avec " + R.size() +" valeurs : " + R.toString() + "\n");
			
			/* On appelle de façon récursive doMondrian sur la partie Gauche et Droite (L & R) */
			doMondrian(L, k);
			doMondrian(R, k);
		}
		
		/* On retourne le jeu de classes d'équivalence */
		return this.ensembleDecoupe;
	}
	
	/* Fonction permettant de renvoyer si le jeu de données en entrée est coupable */
	/* Autrement dit, on essaye d'avoir un "Mondrian" d'avance afin de savoir un coup en avance si le jeu de données en entrée et découpable selone l'algo de Mondrian */
	/**
	 * @param data : jeu de données servant à l'analyse
	 * @param k : k-anonymat choisi
	 * @return Retourne True ou False selon si le jeu de données en entrée peut être coupé
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
		
		/* Il faut que la taille soit supérieure à k */
		return (L.size() >= k) && (R.size() >= k);
	}
	
	/* Retourne le nombre de parcours du jeu de données */
	public int getNbParcours(){
		return this.nbParcoursJDD;
	}
	
}
