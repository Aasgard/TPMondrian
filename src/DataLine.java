
public class DataLine {
	
	/* Attributs de la classes : deux QID et une SD */
	private int qid1;
	private int qid2;
	private String sd;
	
	/* Constrcteur de la DataLine */
	public DataLine(int qid1, int qid2, String sd) {
		this.qid1 = qid1;
		this.qid2 = qid2;
		this.sd = sd;
	}
	
	/* Renvoie la valeur de la colonne rentrée en paramètre */
	public int getColonne(int numcolonne){
		if(numcolonne == 1){
			return this.qid1;
		}else{ return this.qid2; }
	}
	
	/* Getters & Setters */
	public int getQid1() {
		return qid1;
	}
	
	public void setQid1(int qid1) {
		this.qid1 = qid1;
	}
	
	public int getQid2() {
		return qid2;
	}
	
	public void setQid2(int qid2) {
		this.qid2 = qid2;
	}
	
	public String getSd() {
		return sd;
	}
	
	public void setSd(String sd) {
		this.sd = sd;
	}
	
	/* Formattage de la DataLine permettant de l'insérer dans le CSV File */
	public String toCSV(){
		return this.qid1 + ", " + this.qid2 + "\n";
	}
	
	/* Formattage de la DataLine permettant de l'insérer dans le CSV File avec la maladie */
	public String toRawCSV(){
		return this.qid1 + ", " + this.qid2 + ", " + this.sd + "\n";
	}
	
	@Override
	public String toString() {
		//return "DataLine [qid1=" + qid1 + ", qid2=" + qid2 + ", sd=" + sd + "]";
		return "DL [" + qid1 + "," + qid2 + "]";
	}
	
}
