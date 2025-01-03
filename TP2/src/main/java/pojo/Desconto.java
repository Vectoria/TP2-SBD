package pojo;

public class Desconto {
	private int codigo;
	private double valor;

	// Getters e Setters
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

	@Override
	public String toString() {
		return "Desconto{" + "codigo=" + codigo + ", valor=" + valor + '}';
	}
}
