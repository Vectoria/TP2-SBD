package pojo;

public class QualidadeServico {
	private int idComentario;
	private int clienteNIF;
	private int avaliacao;
	private String comentario;

	// Getters e Setters
	public int getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
	}

	public int getClienteNIF() {
		return clienteNIF;
	}

	public void setClienteNIF(int clienteNIF) {
		this.clienteNIF = clienteNIF;
	}

	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int avaliacao) {
		this.avaliacao = avaliacao;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}
