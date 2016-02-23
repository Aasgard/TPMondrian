import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class LaunchMe {

	/* Nombre de tuples */
	static int n = 10;
	/* Bornes min et max pour le QID n�1 */
	static int borneMinQID1 = 1;
	static int borneMaxQID1 = 5;
	/* Bornes min et max pour le QID n�2 */
	static int borneMinQID2 = 1;
	static int borneMaxQID2 = 50;
	/* Tableau contenant toutes les valeurs des donn�es sensibles possibles */
	static String[] tabMaladies = {"Leucemie","SIDA","Vitiligo","Biermer","Blennorragie","Hemochromatose"};
	/* Nombre de valeurs possibles pour la donn�e sensible */
	static int nbValeursSD = tabMaladies.length;
	static int k = 2;

	public static void mondrian(ArrayList<DataLine> data, int k){
		if(data.size() < k || data.size() < 2*k-1){
			System.out.println("Algorithme de Mondrian terminé.");
		}else{
			int dimension = chooseDimension(data);
			HashMap<Integer,Integer> fs = frequencySet(data, dimension);
			int splitVal = findMedian(fs);
			// les valeurs et prendre la valeur du milieu
			ArrayList<DataLine> L = new ArrayList<DataLine>();
			ArrayList<DataLine> R = new ArrayList<DataLine>();

			for (DataLine dataLine : data) {
				if(dataLine.getColonne(dimension) <= splitVal){
					L.add(dataLine);
				}else{
					R.add(dataLine);
				}
			}

			System.out.println("Colonne choisie : " + dimension);
			System.out.println(fs.toString());
			System.out.println("Jeu coupé à partir de la donnée " + splitVal);
			System.out.println("Jeu de Gauche (L) : " + L.toString());
			System.out.println("Nombre à Gauche (L) : " + L.size());
			System.out.println("Jeu de Droite (R) : " + R.toString());
			System.out.println("Nombre à Droite (R) : " + R.size());
			
			mondrian(L, k);
			//mondrian(R, k);
		}
	}

	public static int chooseDimension(ArrayList<DataLine> data){
		int minQID1 = data.get(0).getQid1();
		int maxQID1 = data.get(0).getQid1();
		int minQID2 = data.get(0).getQid2();
		int maxQID2 = data.get(0).getQid2();
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

		if(maxQID1-minQID1 > maxQID2 - minQID2){ return 1; }else{ return 2; }
	}

	public static HashMap<Integer,Integer> frequencySet(ArrayList<DataLine> data, int dim){

		HashMap<Integer,Integer> fs = new HashMap<Integer,Integer>();

		for (DataLine dataLine : data) {
			if(!fs.containsKey(dataLine.getColonne(dim))){
				fs.put(dataLine.getColonne(dim),1);
			}else{ fs.put(dataLine.getColonne(dim), fs.get(dataLine.getColonne(dim)) + 1); }
		}

		return fs;
	}

	public static int findMedian(HashMap<Integer,Integer> fs){

		int cumulValeurs = 0;
		int cutValue = -1;
		Map<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(fs);
	//	System.out.println("Map triée : " + sortedMap.toString());

		for(Entry<Integer, Integer> entry : sortedMap.entrySet()) {
			int key = entry.getKey();
			int value = entry.getValue();

			cutValue = key;

			cumulValeurs += value;

			if(cumulValeurs >= (n/2)){
				break;
			}
		}

		return cutValue;
	}

	public static void main(String[] args) {

		/* Liste contenant toutes nos donn�es g�n�r�es al�atoirement */
		ArrayList<DataLine> dataList = new ArrayList<DataLine>();

		/* Cr�ation de n lignes (tuples) */
		for(int i = 1; i <= n; i++){
			DataLine currentDL = new DataLine(RandomInt.randInt(borneMinQID1, borneMaxQID1), RandomInt.randInt(borneMinQID2, borneMaxQID2), tabMaladies[RandomInt.randInt(0, nbValeursSD-1)]);
			dataList.add(currentDL);
		}
		/* D�but Objet de la liste de tuples */
		System.out.println(dataList.toString());

		System.out.println("Debut de l'ecriture dans le fichier ...");
		try {
			/* Création du fichier dans lequel nous allons �crire nos donn�es */
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("DataList.csv"),false));
			/* On ajoute nos noms de colonnes */
			bw.append("QID1, QID2 \n");
			/* Parcours de chaque tuple pour l'�crire dans le fichier */
			for (DataLine dataLine : dataList) {
				/* On ajoute chaque DataLine sous son format CSV */
				bw.append(dataLine.toCSV());
			}
			/* Application de l'�criture des donn�es */
			bw.flush();
			/* Fermeture du BW */
			bw.close();

			/* Cr�ation du fichier dans lequel nous allons �crire nos donn�es */
			BufferedWriter bwR = new BufferedWriter(new FileWriter(new File("RawDataList.csv"),false));
			/* On ajoute nos noms de colonnes */
			bwR.append("QID1, QID2, SD \n");
			/* Parcours de chaque tuple pour l'�crire dans le fichier */
			for (DataLine dataLine : dataList) {
				/* On ajoute chaque DataLine sous son format CSV */
				bwR.append(dataLine.toRawCSV());
			}
			/* Application de l'�criture des donn�es */
			bwR.flush();
			/* Fermeture du BW */
			bwR.close();
			System.out.println("Ecriture des colonnes effectuee avec succes !");

			System.out.println("Debut de l'algorithme de Mondrian ...");
			/* Appel de l'algorithme de Mondrian */
			LaunchMe.mondrian(dataList,k);
		} catch (IOException e) {
			System.out.println("Erreur dans l'ecriture du fichier" + e.getMessage());
		}
	}
}
