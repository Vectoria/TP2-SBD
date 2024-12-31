package pojo;

public class LugarVeiculo {
	private String localidade;
	private int piso;
	private String fila;
	private int posFila;
	private String matricula;

	// Getters and Setters
	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public int getPiso() {
		return piso;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public String getFila() {
		return fila;
	}

	public void setFila(String fila) {
		this.fila = fila;
	}

	public int getPosFila() {
		return posFila;
	}

	public void setPosFila(int posFila) {
		this.posFila = posFila;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@Override
	public String toString() {
		return "LugarVeiculo{" + "localidade='" + localidade + '\'' + ", piso=" + piso + ", fila='" + fila + '\''
				+ ", posFila=" + posFila + ", matricula='" + matricula + '\'' + '}';
	}
}
