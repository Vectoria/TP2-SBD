package pojo;

public class Condutor {
	private int condutorNIF;
	private int numID;
	private java.time.LocalDate dataNascimento;
	private Integer reputacao;
	private java.time.LocalDate dataEmissao;
	private java.time.LocalDate dataValidade;
	private String tipoHab;

	public int getCondutorNIF() {
		return condutorNIF;
	}

	public void setCondutorNIF(int condutorNIF) {
		this.condutorNIF = condutorNIF;
	}

	public int getNumID() {
		return numID;
	}

	public void setNumID(int numID) {
		this.numID = numID;
	}

	public java.time.LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(java.time.LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getReputacao() {
		return reputacao;
	}

	public void setReputacao(Integer reputacao) {
		this.reputacao = reputacao;
	}

	public java.time.LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(java.time.LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public java.time.LocalDate getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(java.time.LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getTipoHab() {
		return tipoHab;
	}

	public void setTipoHab(String tipoHab) {
		this.tipoHab = tipoHab;
	}
}
