package br.com.treinaweb.agenda.servlets.agenda;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.treinaweb.agenda.entidades.Contato;
import br.com.treinaweb.agenda.repositorios.impl.ContatoRepositorioJdbc;
import br.com.treinaweb.agenda.repositorios.interfaces.AgendaRepositorio;

@WebServlet(urlPatterns = {"/agenda/editar"})
public class AlterarContatoServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7530616792721410336L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idContato = Integer.parseInt(request.getParameter("id"));
		AgendaRepositorio<Contato> agendaRepositorio = new ContatoRepositorioJdbc();
		try {
			List<Contato> contatos = agendaRepositorio.selecionar();
			var contatoSelecionado = contatos.stream().filter(c -> c.getId() == idContato).findFirst();
			if(contatoSelecionado.isPresent()) {
				request.setAttribute("contato", contatoSelecionado.get());
			} else {
				request.getSession().setAttribute("mensagemErro", "Este contato não existe");
				response.sendRedirect("/agenda/listar");
			}
			
		} catch (SQLException e) {
			request.getSession().setAttribute("mensagemErro", e.getMessage());
			response.sendRedirect("/agenda/listar");
		} 
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/paginas/agenda/AlterarContato.jsp");
		dispatcher.forward(request, response);
	}
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Contato contatoAlterado = new Contato();
		contatoAlterado.setNome(request.getParameter("nome"));
		contatoAlterado.setIdade(Integer.parseInt(request.getParameter("idade")));
		contatoAlterado.setTelefone(request.getParameter("telefone"));
		contatoAlterado.setId(Integer.parseInt(request.getParameter("id")));
		AgendaRepositorio<Contato> agendaRepositorio = new ContatoRepositorioJdbc();
		try {
			agendaRepositorio.atualizar(contatoAlterado);
		}  catch (SQLException e) {
			request.getSession().setAttribute("mensagemErro1", e.getMessage());
		}
		response.sendRedirect(request.getContextPath() +  "/agenda/listar");
	}
	
	

}
