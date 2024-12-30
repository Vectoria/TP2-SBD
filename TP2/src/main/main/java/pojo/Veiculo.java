package pojo;

import java.time.LocalDate;

public class Veiculo {
	private String matricula;
	private String midia;
	private String cor;
	private int numLugares;
	private double capacidadeCarga;
	private int numPortas;
	private int numEixos;
	private int potencia;
	private String combustivel;
	private String nomeMod;
	private String nomeMarca;
	private LocalDate dataTarifa;
	private double valorDiaUtil;
	private double valorDiaNaoUtil;
	private String tipoHab;

	// Getters e Setters
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMidia() {
		return midia;
	}

	public void setMidia(String midia) {
		this.midia = midia;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public int getNumLugares() {
		return numLugares;
	}

	public void setNumLugares(int numLugares) {
		this.numLugares = numLugares;
	}

	public double getCapacidadeCarga() {
		return capacidadeCarga;
	}

	public void setCapacidadeCarga(double capacidadeCarga) {
		this.capacidadeCarga = capacidadeCarga;
	}

	public int getNumPortas() {
		return numPortas;
	}

	public void setNumPortas(int numPortas) {
		this.numPortas = numPortas;
	}

	public int getNumEixos() {
		return numEixos;
	}

	public void setNumEixos(int numEixos) {
		this.numEixos = numEixos;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public String getCombustivel() {
		return combustivel;
	}

	public void setCombustivel(String combustivel) {
		this.combustivel = combustivel;
	}

	public String getNomeMod() {
		return nomeMod;
	}

	public void setNomeMod(String nomeMod) {
		this.nomeMod = nomeMod;
	}

	public String getNomeMarca() {
		return nomeMarca;
	}

	public void setNomeMarca(String nomeMarca) {
		this.nomeMarca = nomeMarca;
	}

	public LocalDate getDataTarifa() {
		return dataTarifa;
	}

	public void setDataTarifa(LocalDate dataTarifa) {
		this.dataTarifa = dataTarifa;
	}

	public double getValorDiaUtil() {
		return valorDiaUtil;
	}

	public void setValorDiaUtil(double valorDiaUtil) {
		this.valorDiaUtil = valorDiaUtil;
	}

	public double getValorDiaNaoUtil() {
		return valorDiaNaoUtil;
	}

	public void setValorDiaNaoUtil(double valorDiaNaoUtil) {
		this.valorDiaNaoUtil = valorDiaNaoUtil;
	}

	public String getTipoHab() {
		return tipoHab;
	}

	public void setTipoHab(String tipoHab) {
		this.tipoHab = tipoHab;
	}
}
