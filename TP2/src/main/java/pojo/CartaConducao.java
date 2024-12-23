package pojo;

public class CartaConducao {
	private int numID;
	private String tipoHab;
	private java.time.LocalDate dataValidade;
	private java.time.LocalDate dataEmissao;

	public int getNumID() {
		return numID;
	}

	public void setNumID(int numID) {
		this.numID = numID;
	}

	public String getTipoHab() {
		return tipoHab;
	}

	public void setTipoHab(String tipoHab) {
		this.tipoHab = tipoHab;
	}

	public java.time.LocalDate getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(java.time.LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}

	public java.time.LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(java.time.LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
}
