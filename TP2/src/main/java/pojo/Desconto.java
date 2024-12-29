package pojo;

public class Desconto {
	private int codigo;
	private double valor;
	private int nVezesUsadas;

	// Getters and Setters
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getNVezesUsadas() {
		return nVezesUsadas;
	}

	public void setNVezesUsadas(int nVezesUsadas) {
		this.nVezesUsadas = nVezesUsadas;
	}

	@Override
	public String toString() {
		return "Desconto{" + "codigo=" + codigo + ", valor=" + valor + ", nVezesUsadas=" + nVezesUsadas + '}';
	}
}
