import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LaunchMe {
	
	/* Nombre de tuples */
	static int n = 30;
	/* Bornes min et max pour le QID n°1 */
	static int borneMinQID1 = 1;
	static int borneMaxQID1 = 7;
	/* Bornes min et max pour le QID n°2 */
	static int borneMinQID2 = 2;
	static int borneMaxQID2 = 13;
	/* Tableau contenant toutes les valeurs des données sensibles possibles */
	static String[] tabMaladies = {"Leucémie","SIDA","Vitiligo","Biermer","Blennorragie","Hémochromatose"};
	/* Nombre de valeurs possibles pour la donnée sensible */
	static int nbValeursSD = tabMaladies.length;
	static int k;
	
	public static void mondrian(ArrayList<DataLine> data){
		System.out.println(data.toString());
	}
	
	public static void main(String[] args) {
		
		/* Liste contenant toutes nos données générées aléatoirement */
		ArrayList<DataLine> dataList = new ArrayList<DataLine>();
		
		/* Création de n lignes (tuples) */
		for(int i = 0; i <= n; i++){
			DataLine currentDL = new DataLine(RandomInt.randInt(borneMinQID1, borneMaxQID1), RandomInt.randInt(borneMinQID2, borneMaxQID2), tabMaladies[RandomInt.randInt(0, nbValeursSD-1)]);
			dataList.add(currentDL);
		}
		/* Début Objet de la liste de tuples */
		System.out.println(dataList.toString());
		
		System.out.println("Début de l'écriture dans le fichier ...");
		try {
			/* Création du fichier dans lequel nous allons écrire nos données */
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("DataList.csv"),false));
			/* On ajoute nos noms de colonnes */
			bw.append("QID1, QID2 \n");
			/* Parcours de chaque tuple pour l'écrire dans le fichier */
			for (DataLine dataLine : dataList) {
				/* On ajoute chaque DataLine sous son format CSV */
				bw.append(dataLine.toCSV());
			}
			/* Application de l'écriture des données */
			bw.flush();
			/* Fermeture du BW */
			bw.close();
			System.out.println("Ecriture des colonnes effectuée avec succès !");
			
			System.out.println("Début de l'algorithme de Mondrian ...");
			/* Appel de l'algorithme de Mondrian */
			LaunchMe.mondrian(dataList);
		} catch (IOException e) {
			System.out.println("Erreur dans l'écriture du fichier" + e.getMessage());
		}
	}

}
