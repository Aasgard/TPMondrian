import java.util.ArrayList;

public class ResultatJDD extends ArrayList<ArrayList<DataLine>>{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ArrayList<DataLine>> res;
	
	public ResultatJDD(){
		this.res = new ArrayList<ArrayList<DataLine>>();
	}
	
	public boolean add(ArrayList<DataLine> aldl){
		this.res.add(aldl);
		return true;
	}
	
	public ArrayList<DataLine> get(int index){
		return this.res.get(index);
	}
	
	@Override
	public String toString(){
		String response = "";
		int i = 0;
		for (ArrayList<DataLine> arrayList : this.res) {
			i++;
			response += "CE n°" + i + " [ " + arrayList.size() +" éléments ]: " + arrayList.toString() + "\n";
		}
		response += "\nTotal de " + i + " CE";
		return response;
	}

}
