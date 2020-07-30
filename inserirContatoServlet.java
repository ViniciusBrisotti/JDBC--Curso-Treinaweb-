package br.com.treinaweb.agenda.servlets.agenda;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.treinaweb.agenda.entidades.Contato;
import br.com.treinaweb.agenda.repositorios.impl.ContatoRepositorioJdbc;
import br.com.treinaweb.agenda.repositorios.interfaces.AgendaRepositorio;

@WebServlet(urlPatterns = {"/agenda/incluir"})
public class inserirContatoServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7948714246237153182L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/paginas/agenda/InserirContato.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AgendaRepositorio<Contato> agendaRepositorio = new ContatoRepositorioJdbc();
		Contato novoContato = new Contato();
		novoContato.setNome(request.getParameter("nome"));
		novoContato.setIdade(Integer.parseInt(request.getParameter("idade")));
		novoContato.setTelefone(request.getParameter("telefone"));
		try {
			agendaRepositorio.inserir(novoContato);
		} catch (IOException | SQLException e) {
			request.getSession().setAttribute("mensagemErro", e.getMessage());
		}
		response.sendRedirect(request.getContextPath() + "/agenda/listar");
	}
	
	

}
