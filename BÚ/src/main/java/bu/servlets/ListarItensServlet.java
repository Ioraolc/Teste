package bu.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.gson.Gson;

import bu.dao.ItemDAO;
import bu.modelo.Item;

@WebServlet(name = "ListarItensServlet", urlPatterns = { "/listarItens" })
public class ListarItensServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/json;charset=UTF-8");
		
		List<Item> itens = new ItemDAO().listarItens();
		
		String itensJson = new Gson().toJson(itens);
		
		resp.getWriter().write(itensJson);
		
	}
	
}
