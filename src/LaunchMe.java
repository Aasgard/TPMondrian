import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LaunchMe {

	/* Nombre de tuples */
	static int n = 8;
	/* Bornes min et max pour le QID nï¿½1 */
	static int borneMinQID1 = 1;
	static int borneMaxQID1 = 10;
	/* Bornes min et max pour le QID nï¿½2 */
	static int borneMinQID2 = 1;
	static int borneMaxQID2 = 10;
	/* Tableau contenant toutes les valeurs des donnï¿½es sensibles possibles */
	static String[] tabMaladies = {"Leucemie","SIDA","Vitiligo","Biermer","Blennorragie","Hemochromatose"};
	/* Nombre de valeurs possibles pour la donnï¿½e sensible */
	static int nbValeursSD = tabMaladies.length;
	static int k = 2;

	public static void main(String[] args) {
		
		ArrayList<DataLine> dataList = new ArrayList<DataLine>();

		/* Création de n lignes (tuples) */
		for(int i = 1; i <= n; i++){
			DataLine currentDL = new DataLine(RandomInt.randInt(borneMinQID1, borneMaxQID1), RandomInt.randInt(borneMinQID2, borneMaxQID2), tabMaladies[RandomInt.randInt(0, nbValeursSD-1)]);
			dataList.add(currentDL);
		}
		/* Début Objet de la liste de tuples */
		System.out.println(dataList.toString() + "\n");

		System.out.println("Debut de l'ecriture dans le fichier ...");
		try {
			/* CrÃ©ation du fichier dans lequel nous allons ï¿½crire nos donnï¿½es */
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("DataList.csv"),false));
			/* On ajoute nos noms de colonnes */
			bw.append("QID1, QID2 \n");
			/* Parcours de chaque tuple pour l'ï¿½crire dans le fichier */
			for (DataLine dataLine : dataList) {
				/* On ajoute chaque DataLine sous son format CSV */
				bw.append(dataLine.toCSV());
			}
			/* Application de l'ï¿½criture des donnï¿½es */
			bw.flush();
			/* Fermeture du BW */
			bw.close();

			/* Crï¿½ation du fichier dans lequel nous allons ï¿½crire nos donnï¿½es */
			BufferedWriter bwR = new BufferedWriter(new FileWriter(new File("RawDataList.csv"),false));
			/* On ajoute nos noms de colonnes */
			bwR.append("QID1, QID2, SD \n");
			/* Parcours de chaque tuple pour l'ï¿½crire dans le fichier */
			for (DataLine dataLine : dataList) {
				/* On ajoute chaque DataLine sous son format CSV */
				bwR.append(dataLine.toRawCSV());
			}
			/* Application de l'ï¿½criture des donnï¿½es */
			bwR.flush();
			/* Fermeture du BW */
			bwR.close();
			System.out.println("Ecriture des colonnes effectuee avec succes !");

			System.out.println("Debut de l'algorithme de Mondrian ... \n");
			
			
			
			
			
			
			/* Appel de l'algorithme de Mondrian */
			Mondrian mond = new Mondrian(dataList, k);
			System.out.println(mond.doMondrian(dataList, k).toString());
			
			
			
			
			
			
		} catch (IOException e) {
			System.out.println("Erreur dans l'ecriture du fichier" + e.getMessage());
		}
	}
}
