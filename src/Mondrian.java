import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Mondrian {
	
	private ArrayList<DataLine> data;
	private int k;
	
	public Mondrian(ArrayList<DataLine> data, int k) {
		this.data = data;
		this.k = k;
	}
	
	public int chooseDimension(ArrayList<DataLine> data){
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
	
	public HashMap<Integer,Integer> frequencySet(ArrayList<DataLine> data, int dim){

		HashMap<Integer,Integer> fs = new HashMap<Integer,Integer>();

		for (DataLine dataLine : data) {
			if(!fs.containsKey(dataLine.getColonne(dim))){
				fs.put(dataLine.getColonne(dim),1);
			}else{ fs.put(dataLine.getColonne(dim), fs.get(dataLine.getColonne(dim)) + 1); }
		}

		return fs;
	}
	
	public int findMedian(HashMap<Integer,Integer> fs){
		System.out.println("Taille du HashMap en entrée : " + fs.size());
		int cumulValeurs = 0;
		int cutValue = -1;
		Map<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(fs);
		System.out.println("Map triée : " + sortedMap.toString());

		for(Entry<Integer, Integer> entry : sortedMap.entrySet()) {
			int key = entry.getKey();
			int value = entry.getValue();

			cutValue = key;
			int cm = cumulValeurs;
			cumulValeurs += value;

			if(cumulValeurs >= (sortedMap.size()/2)){
				break;
			}
		}

		return cutValue;
	}
	
	public void doMondrian(ArrayList<DataLine> data, int k){
		if(data.size() <= k){
			System.out.println("Terminé ! ");
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
			//System.out.println(fs.toString());
			System.out.println("Jeu coupé à  partir de la donnée " + splitVal);
			System.out.println("Jeu de Gauche (L) : " + L.toString());
			System.out.println("Nombre à  Gauche (L) : " + L.size());
			System.out.println("Jeu de Droite (R) : " + R.toString());
			System.out.println("Nombre à  Droite (R) : " + R.size() + "\n");
			
			if(!(L.size() <= k) && !(R.size() <= k)){
				doMondrian(L, k);
				doMondrian(R, k);
			}
		}
	}
	
}
