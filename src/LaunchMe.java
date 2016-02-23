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
	static int n = 100;
	/* Bornes min et max pour le QID n�1 */
	static int borneMinQID1 = 1;
	static int borneMaxQID1 = 5;
	/* Bornes min et max pour le QID n�2 */
	static int borneMinQID2 = 1;
	static int borneMaxQID2 = 5;
	/* Tableau contenant toutes les valeurs des donn�es sensibles possibles */
	static String[] tabMaladies = {"Leucemie","SIDA","Vitiligo","Biermer","Blennorragie","Hemochromatose"};
	/* Nombre de valeurs possibles pour la donn�e sensible */
	static int nbValeursSD = tabMaladies.length;
	static int k = 2;

	public static void main(String[] args) {
		
		ArrayList<DataLine> dataList = new ArrayList<DataLine>();

		/* Cr�ation de n lignes (tuples) */
		for(int i = 1; i <= n; i++){
			DataLine currentDL = new DataLine(RandomInt.randInt(borneMinQID1, borneMaxQID1), RandomInt.randInt(borneMinQID2, borneMaxQID2), tabMaladies[RandomInt.randInt(0, nbValeursSD-1)]);
			dataList.add(currentDL);
		}
		/* D�but Objet de la liste de tuples */
		//System.out.println(dataList.toString());

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

			System.out.println("Debut de l'algorithme de Mondrian ... \n");
			
			
			
			
			
			
			/* Appel de l'algorithme de Mondrian */
			Mondrian mond = new Mondrian(dataList, k);
			mond.doMondrian(dataList, k);
			
			
			
			
			
			
		} catch (IOException e) {
			System.out.println("Erreur dans l'ecriture du fichier" + e.getMessage());
		}
	}
}
