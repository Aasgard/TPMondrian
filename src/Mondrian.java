import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Mondrian {
	
	private ArrayList<DataLine> data;
	private int k;
	private ArrayList<ArrayList<DataLine>> ensembleDecoupe;
	
	public Mondrian(ArrayList<DataLine> data, int k) {
		this.data = data;
		this.k = k;
		ensembleDecoupe = new ArrayList<ArrayList<DataLine>>();
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
		int cumulValeurs = 0;
		int cutValue = 0;
		Map<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(fs);
		
		double totalValues = 0;
		
		for(Entry<Integer, Integer> entry : sortedMap.entrySet()) {
			int value = entry.getValue();

			totalValues += value;
		}
		
		//System.out.println("Il y a " + (int)totalValues + " valeurs dans le HashMap.");
		//System.out.println("Map triée : " + sortedMap.toString());
		
		//System.out.println("Moitié du jeu de données : " + (Double)(totalValues/2));

		for(Entry<Integer, Integer> entry : sortedMap.entrySet()) {
			int key = entry.getKey();
			int value = entry.getValue();
			
			cumulValeurs = cumulValeurs + value;
			
			if(cumulValeurs >= Math.round(totalValues/2)){
				cutValue = key;
				break;
			}
		}
		
		//System.out.println("Médiane : " + cutValue);

		return cutValue;
	}
	
	public ArrayList<ArrayList<DataLine>> doMondrian(ArrayList<DataLine> data, int k){
		if(!isCutable(data, k)){
			System.out.println("Terminé ! Je renvoie le jeu de données en paramètre.");
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
			
			ensembleDecoupe.add(L);
			ensembleDecoupe.add(R);

			System.out.println("Colonne choisie : " + dimension);
			System.out.println("Jeu de Gauche (L) avec " + L.size() +" valeurs : " + L.toString());
			System.out.println("Jeu de Droite (L) avec " + R.size() +" valeurs : " + R.toString());
			System.out.println();
			
			if(L.size() >= k && R.size() >= k){
				doMondrian(L, k);
				doMondrian(R, k);
			}
		}
		
		return this.ensembleDecoupe;
	}
	
	public boolean isCutable(ArrayList<DataLine> data, int k){
		/*if(data.size() >= k){
			return true;
		}
		return false;*/
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
		
		if(L.size() >= k && R.size() >= k){
			return true;
		}else{
			return false;
		}
		
		
	}
	
}
