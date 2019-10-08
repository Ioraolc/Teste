package bu.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bu.dao.ItemDAO;
import bu.modelo.Item;
import bu.util.Erro;

@WebServlet(name = "CadastroItemServlet", urlPatterns = { "/cadastroItem" })
public class CadastroItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/json;charset=UTF-8");
		
		StringBuffer parametro = new StringBuffer();
		String line = null;
		
		BufferedReader reader = req.getReader();
		while ((line = reader.readLine()) != null)
			parametro.append(line.trim());
		
		
		Item item = new Gson().fromJson(parametro.toString(), Item.class);
		
		boolean cadastroRealizado = new ItemDAO().cadastrar(item);
		
		if (cadastroRealizado) {
			String itemJson = new Gson().toJson(item);
			resp.getWriter().write(itemJson);
		} else {
			Erro erro = new Erro();
			erro.setMensagem("J� existe um item com a mesma descri��o!");
			String erroJson = new Gson().toJson(erro);
			resp.getWriter().write(erroJson);
		}
		
	}
	
}
