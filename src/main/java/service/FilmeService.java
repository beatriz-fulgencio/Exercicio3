package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.ProdutoDAO;
import model.Produto;
import spark.Request;
import spark.Response;


public class FilmeService {

	private FilmeDAO FilmeDAO = new FilmeDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_genero = 1;
	private final int FORM_ORDERBY_Nome = 2;
	private final int FORM_ORDERBY_Tempo = 3;
	
	
	public FilmeService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Filme(), FORM_ORDERBY_Genero);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Filme(), orderBy);
	}

	
	public void makeForm(int tipo, Filme filme, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umFilme = "";
		if(tipo != FORM_INSERT) {
			umFilme += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umFilme += "\t\t<tr>";
			umFilme += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/Filme/list/1\">Novo Filme</a></b></font></td>";
			umFilme += "\t\t</tr>";
			umFilme += "\t</table>";
			umFilme += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/Filme/";
			String name, genero, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Filme";
				genero = "HRV, X1, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + filme.getNome();
				name = "Atualizar Produto (filme.getNome() + ")";
				genero = filme.getGenero();
				buttonLabel = "Atualizar";
			}
			umFilme+= "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umFilme+= "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umFilme+= "\t\t<tr>";
			umFilme+= "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umFilme+= "\t\t</tr>";
			umFilme+= "\t\t<tr>";
			umFilme+= "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umFilme+= "\t\t</tr>";
			umFilme+= "\t\t<tr>";
			umFilme+= "\t\t\t<td>&nbsp;genero: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ genero +"\"></td>";
			umFilme+= "\t\t\t<td>Tempo: <input class=\"input--register\" type=\"text\" name=\"Tempo\" value=\""+ Filme.getTempo() +"\"></td>";
			umFilme+= "\t\t</tr>";
			umFilme+= "\t\t<tr>";
			umFilme+= "\t\t\t<td>Ano: <input class=\"input--register\" type=\"text\" name=\"ano\" value=\""+ produto.getAno() + "\"></td>";
			umFilme+= "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umFilme+= "\t\t</tr>";
			umFilme+= "\t</table>";
			umFilme+= "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umFilme += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umFilme += "\t\t<tr>";
			umFilme += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Filme (ID " + produto.getNome() + ")</b></font></td>";
			umFilme += "\t\t</tr>";
			umFilme += "\t\t<tr>";
			umFilme += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umFilme += "\t\t</tr>";
			umFilme += "\t\t<tr>";
			umFilme += "\t\t\t<td>&nbsp;genero: "+ produto.getgenero() +"</td>";
			umFilme += "\t\t\t<td>Tempo: "+ produto.getTempo() +"</td>";
			umFilme += "\t\t</tr>";
			umFilme += "\t\t<tr>";
			umFilme += "\t\t\t<td>&nbsp;Ano: "+ produto.getAno() + "</td>";
			umFilme += "\t\t\t<td>&nbsp;</td>";
			umFilme += "\t\t</tr>";
			umFilme += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-Filme>", umFilme);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Filmes</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/Filme/list/" + FORM_ORDERBY_genero + "\"><b>genero</b></a></td>\n" +
        		"\t<td><a href=\"/Filme/list/" + FORM_ORDERBY_Nome + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/Filme/list/" + FORM_ORDERBY_Tempo + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Filme> filmes;
		if (orderBy == FORM_ORDERBY_genero) {                 	filmes = FilmeDAO.getOrderBygenero();
		} else if (orderBy == FORM_ORDERBY_Nome) {		filmes = FilmeDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_Tempo) {			filmes = FilmeDAO.getOrderByTempo();
		} else {											filmes = FilmeDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Filme c : filmes) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + c.getNome() + "</td>\n" +
            		  "\t<td>" + c.getgenero() + "</td>\n" +
            		  "\t<td>" + c.getTempo() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/" + c.getNome() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/update/" + c.getNome() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteProduto('" + c.getNome() + "', '" + c.getgenero() + "', '" + c.getTempo() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR- Filme>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String genero = request.queryParams("genero");
		double tempo = Double.parseDouble(request.queryParams("tempo"));
		int ano = Integer.parseInt(request.queryParams("ano"));
		
		String resp = "";
		
		Filme filme = new Filme(genero, tempo, ano);
		
		if(FilmeDAO.insert(filme) == true) {
            resp = "Filme (" + genero + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Filme (" +genero + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		String nome = request.queryParams("nome");		
		Filme filme = (Filme) FilmeDAO.get(Nome);
		
		if (filme != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, filme, FORM_ORDERBY_genero);
        } else {
            response.status(404); // 404 Not found
            String resp = "Filme " + Nome + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		String nome = request.queryParams("Nome");		
		Filme filme = (Filme) FilmeDAO.get(nome);
		
		if (filme != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, filme, FORM_ORDERBY_genero);
        } else {
            response.status(404); // 404 Not found
            String resp = "Filme " + nome + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
		String nome = request.queryParams("Nome");		
		Filme filme = FilmeDAO.get(nome);
        String resp = "";       

        if (Filme != null) {
        	Filme.setgenero(request.queryParams("genero"));
        	Filme.setTempo(Double.parseDouble(request.queryParams("tempo")));
        	Filme.setAno(Integer.parseInt(request.queryParams("ano")));
        	FilmeDAO.update(filme);
        	response.status(200); // success
            resp = "filme (Nome " + filme.getNome() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "filme (Nome \" + filme.getNome() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
		String nome = request.queryParams("Nome");		
		Filme filme = FilmeDAO.get(nome);
        String resp = "";       

        if (filme != null) {
            FilmeDAO.delete(nome);
            response.status(200); // success
            resp = "Filme (" + nome + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Filme (" + nome + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}