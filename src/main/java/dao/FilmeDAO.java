package dao;

import model.filme;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class FilmeDAO extends DAO {	
	public FilmeDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Filme filme) {
		boolean status = false;
		try {
			String sql = "INSERT INTO filme (genero, diretor, ano) "
		               + "VALUES ('" + filme.getGenero() + "', "
		               + filme.getDiretor() + ", " + filme.getAno() + ", ?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Filme get(int tempo) {
		Filme filme = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE id="+tempo;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 filme = new Filme(rs.getInt("ano"), rs.getString("tempo"), rs.getString("genero"), rs.getDouble("diretor"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return filme;
	}
	
	
	public List<Filme> get() {
		return get("");
	}

	
	public List<Filme> getOrderByTempo() {
		return get("tempo");		
	}
	
	
	public List<Filme> getOrderByGenero() {
		return get("genero");		
	}
	
	
	public List<Filme> getOrderByDiretor() {
		return get("diretor");		
	}
	
	
	private List<Filme> get(String orderBy) {
		List<Filme> filmes = new ArrayList<Filme>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM filme" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Filme c = new Filme(rs.getInt("ano"), rs.getString("tempo"), rs.getString("genero"), rs.getDouble("diretor"));
	            filmes.add(c);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return  filmes;
	}
	
	
	public boolean update(Filme filme) {
		boolean status = false;
		try {  
			String sql = "UPDATE produto SET genero = '" + filme.getGenero() + "', "
					   + "diretor = " + filme.getDiretor() + ", " 
					   + "ano = " + filme.getAno() + ",";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int tempo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM produto WHERE id = " + tempo);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}