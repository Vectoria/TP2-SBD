package pojo;

import java.math.BigDecimal;

public class Cliente {
	private int clienteNIF;
	private String moedaPref;
	private String prefLingCult;
	private int contactoTel;
	private String email;
	private String nome;
	private int condutorNIF;
	private Integer codigo;
	private String rua;
	private int codigoPostalP1;
	private int codigoPostalP2;
	private int numeroPorta;
	private String nomeFreguesia;
	private String nomeConcelho;
	private String nomeDistrito;
	private double avaliacaoCliente;

	// Getters e Setters
	public int getClienteNIF() {
		return clienteNIF;
	}

	public void setClienteNIF(int clienteNIF) {
		this.clienteNIF = clienteNIF;
	}

	public String getMoedaPref() {
		return moedaPref;
	}

	public void setMoedaPref(String moedaPref) {
		this.moedaPref = moedaPref;
	}

	public String getPrefLingCult() {
		return prefLingCult;
	}

	public void setPrefLingCult(String prefLingCult) {
		this.prefLingCult = prefLingCult;
	}

	public int getContactoTel() {
		return contactoTel;
	}

	public void setContactoTel(int contactoTel) {
		this.contactoTel = contactoTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCondutorNIF() {
		return condutorNIF;
	}

	public void setCondutorNIF(int condutorNIF) {
		this.condutorNIF = condutorNIF;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

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

	public double getAvaliacaoCliente() {
		return avaliacaoCliente;
	}

	public void setAvaliacaoCliente(double avaliacaoCliente) {
		this.avaliacaoCliente = avaliacaoCliente;
	}
	
	public static BigDecimal conversao(BigDecimal custo, String moeda) {
		if (moeda.equalsIgnoreCase("Dol")) {
			return custo.multiply(new BigDecimal(1.02));
		}
		if (moeda.equalsIgnoreCase("Lib")) {
			return custo.multiply(new BigDecimal(0.83));
		}
		return custo;
	}
}
