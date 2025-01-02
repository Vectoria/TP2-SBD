package pojo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Aluguer {
	private LocalDateTime dhInicio;
	private LocalDateTime dhFim;
	private int clienteNIF;
	private Integer condutorNIF; // Nullable
	private String matricula;
	private String localidade;
	private LocalDateTime dhEntrega; // Nullable
	private BigDecimal custoFinal; // Nullable
	private String moedaPref; // Nullable
	private Integer codigo; // Nullable
	private LocalDate dataTarifa; // Nullable
	private BigDecimal valorDiaUtil; // Nullable
	private BigDecimal valorDiaNaoUtil; // Nullable
	private String qualidadeServicoAluguer; // Nullable

	// Getters e Setters
	public LocalDateTime getDhInicio() {
		return dhInicio;
	}

	public void setDhInicio(LocalDateTime dhInicio) {
		this.dhInicio = dhInicio;
	}

	public LocalDateTime getDhFim() {
		return dhFim;
	}

	public void setDhFim(LocalDateTime dhFim) {
		this.dhFim = dhFim;
	}

	public int getClienteNIF() {
		return clienteNIF;
	}

	public void setClienteNIF(int clienteNIF) {
		this.clienteNIF = clienteNIF;
	}

	public int getCondutorNIF() {
		return condutorNIF;
	}

	public void setCondutorNIF(int condutorNIF) {
		this.condutorNIF = condutorNIF;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public LocalDateTime getDhEntrega() {
		return dhEntrega;
	}

	public void setDhEntrega(LocalDateTime dhEntrega) {
		this.dhEntrega = dhEntrega;
	}

	public BigDecimal getCustoFinal() {
		return custoFinal;
	}

	public void setCustoFinal(BigDecimal custoFinal) {
		this.custoFinal = custoFinal;
	}

	public String getMoedaPref() {
		return moedaPref;
	}

	public void setMoedaPref(String moedaPref) {
		this.moedaPref = moedaPref;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public LocalDate getDataTarifa() {
		return dataTarifa;
	}

	public void setDataTarifa(LocalDate dataTarifa) {
		this.dataTarifa = dataTarifa;
	}

	public BigDecimal getValorDiaUtil() {
		return valorDiaUtil;
	}

	public void setValorDiaUtil(BigDecimal valorDiaUtil) {
		this.valorDiaUtil = valorDiaUtil;
	}

	public BigDecimal getValorDiaNaoUtil() {
		return valorDiaNaoUtil;
	}

	public void setValorDiaNaoUtil(BigDecimal valorDiaNaoUtil) {
		this.valorDiaNaoUtil = valorDiaNaoUtil;
	}

	public String getQualidadeServicoAluguer() {
		return qualidadeServicoAluguer;
	}

	public void setQualidadeServicoAluguer(String qualidadeServicoAluguer) {
		this.qualidadeServicoAluguer = qualidadeServicoAluguer;
	}
}
