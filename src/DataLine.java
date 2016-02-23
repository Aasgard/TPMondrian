
public class DataLine {
	
	private int qid1;
	private int qid2;
	private String sd;
	
	public DataLine(int qid1, int qid2, String sd) {
		this.qid1 = qid1;
		this.qid2 = qid2;
		this.sd = sd;
	}
	
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
	
	public String toCSV(){
		return this.qid1 + ", " + this.qid2 + "\n";
	}
	
	@Override
	public String toString() {
		return "DataLine [qid1=" + qid1 + ", qid2=" + qid2 + ", sd=" + sd + "]";
	}
	
}