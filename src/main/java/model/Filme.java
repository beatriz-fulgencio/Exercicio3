package model;

public class Filme {
	private int ano;
	private String nome;
	private String genero;
	private double tempo;
	
	public Filme() {
		ano = 00;
		nome = "";
		genero = "";
		tempo = 0.00;
	}

	public Filme(int ano, String nome, String genero, double tempo) {
		setAno(ano);
		setNome(nome);
		setgenero(genero);
		setTempo(tempo);
	}		
	
	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String Nome) {
		this.nome = nome;
	}
	
	public String getgenero() {
		return genero;
	}
	
	public void setgenero(String genero) {
		this.genero = genero;
	}

	public double getTempo() {
		return tempo;
	}

	public void setTempo(double tempo) {
		this.tempo = tempo;
	}



	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Filme: " + nome + "  gênero: " + genero + "   Ano: " + ano + "   Tempo(sec): "
				+ tempo ;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getNome() == ((Filme) obj).getNome());
	}	
}