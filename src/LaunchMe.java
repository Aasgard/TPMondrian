import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LaunchMe {

	/* Nombre de tuples */
	static int n = 10;
	/* Bornes min et max pour le QID n�1 */
	static int borneMinQID1 = 1;
	static int borneMaxQID1 = 3;
	/* Bornes min et max pour le QID n�2 */
	static int borneMinQID2 = 4;
	static int borneMaxQID2 = 5;
	/* Tableau contenant toutes les valeurs des donn�es sensibles possibles */
	static String[] tabMaladies = {"Leucemie","SIDA","Vitiligo","Biermer","Blennorragie","Hemochromatose"};
	/* Nombre de valeurs possibles pour la donn�e sensible */
	static int nbValeursSD = tabMaladies.length;
	/* Variable d�signant le k-anonymat choisi */
	static int k = 2;

	public static void main(String[] args) {
		
		/* List de DataLine, repr�sentant le jeu de donn�es */
		ArrayList<DataLine> dataList = new ArrayList<DataLine>();

		/* Cr�ation de n lignes (tuples) */
		for(int i = 1; i <= n; i++){
			/* Entre les bornes min et max, et un SD random */
			DataLine currentDL = new DataLine(RandomInt.randInt(borneMinQID1, borneMaxQID1), RandomInt.randInt(borneMinQID2, borneMaxQID2), tabMaladies[RandomInt.randInt(0, nbValeursSD-1)]);
			/* On ajoute la DataLine fraichement cr��e dans le tableau de donn�es */
			dataList.add(currentDL);
		}
		/* D�but Objet de la liste de tuples */
		System.out.println("@dataList : "+ dataList.toString() + "\n");

		try {
			/* Cr�ation du fichier dans lequel nous allons �crire nos donn�es */
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
			
			/* Appel de l'algorithme de Mondrian */
			Mondrian mond = new Mondrian();
			/* Impression du jeu final des classes d'�quivalence */
			mond.doMondrian(dataList, k);
			System.out.println("La liste a �t� parcourue enti�rement " + mond.getNbParcours() + " fois. \n");
			System.out.println("------------------------------------------------------------------------------ \n");
			System.out.println(mond.getRes().toString());
			System.out.println("\n------------------------------------------------------------------------------ \n");
			
			System.out.println("Si la taille d'un des ArrayList<DataLine> est inf�rieur strictement � k ou sup�rieur strictement � 2k-1, le jeu de donn�es initial ne respecte pas le K-Anonymat.");
		} catch (IOException e) {
			System.out.println("Erreur dans l'ecriture du fichier" + e.getMessage());
		}
	}
}
