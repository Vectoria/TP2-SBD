package pojo;

import java.time.LocalDateTime;

public class Intervencao {
	private int numKM;
	private String matricula;
	private LocalDateTime dhRegisto;
	private String tipoInt;
	private double custoInt;

	// Getters e Setters
	public int getNumKM() {
		return numKM;
	}

	public void setNumKM(int numKM) {
		this.numKM = numKM;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public LocalDateTime getDhRegisto() {
		return dhRegisto;
	}

	public void setDhRegisto(LocalDateTime dhRegisto) {
		this.dhRegisto = dhRegisto;
	}

	public String getTipoInt() {
		return tipoInt;
	}

	public void setTipoInt(String tipoInt) {
		this.tipoInt = tipoInt;
	}

	public double getCustoInt() {
		return custoInt;
	}

	public void setCustoInt(double custoInt) {
		this.custoInt = custoInt;
	}
}
