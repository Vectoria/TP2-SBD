package pojo;

public class Morada {
	private String rua;
	private int codigoPostalP1;
	private int codigoPostalP2;
	private int numeroPorta;
	private String nomeFreguesia;
	private String nomeConcelho;
	private String nomeDistrito;
	private String codigoPostalCompleto; // Gerado automaticamente no banco

	// Getters e Setters
	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getCodigoPostalP1() {
		return codigoPostalP1;
	}

	public void setCodigoPostalP1(int codigoPostalP1) {
		this.codigoPostalP1 = codigoPostalP1;
	}

	public int getCodigoPostalP2() {
		return codigoPostalP2;
	}

	public void setCodigoPostalP2(int codigoPostalP2) {
		this.codigoPostalP2 = codigoPostalP2;
	}

	public int getNumeroPorta() {
		return numeroPorta;
	}

	public void setNumeroPorta(int numeroPorta) {
		this.numeroPorta = numeroPorta;
	}

	public String getNomeFreguesia() {
		return nomeFreguesia;
	}

	public void setNomeFreguesia(String nomeFreguesia) {
		this.nomeFreguesia = nomeFreguesia;
	}

	public String getNomeConcelho() {
		return nomeConcelho;
	}

	public void setNomeConcelho(String nomeConcelho) {
		this.nomeConcelho = nomeConcelho;
	}

	public String getNomeDistrito() {
		return nomeDistrito;
	}

	public void setNomeDistrito(String nomeDistrito) {
		this.nomeDistrito = nomeDistrito;
	}

	public String getCodigoPostalCompleto() {
		return codigoPostalCompleto;
	}

	public void setCodigoPostalCompleto(String codigoPostalCompleto) {
		this.codigoPostalCompleto = codigoPostalCompleto;
	}
}
