package Picture.Models;

public class Pages {
	private String CURPGS;
	private String TTLPGS;
	private String NUMPPGS;
	public Pages(){
		CURPGS="0";
		TTLPGS="100";
		NUMPPGS="12";
	}
	public String getCURPGS() {
		return CURPGS;
	}
	public void setCURPGS(String cURPGS) {
		CURPGS = cURPGS;
	}
	public String getTTLPGS() {
		return TTLPGS;
	}
	public void setTTLPGS(String tTLPGS) {
		TTLPGS = tTLPGS;
	}
	public String getNUMPPGS() {
		return NUMPPGS;
	}
	public void setNUMPPGS(String nUMPPGS) {
		NUMPPGS = nUMPPGS;
	}
	
	

}
