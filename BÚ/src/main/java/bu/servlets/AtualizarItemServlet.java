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

@WebServlet(name = "AtualizarItemServlet", urlPatterns = { "/atualizarItem" })
public class AtualizarItemServlet extends HttpServlet {

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
		
		boolean atualizacaoRealizada = new ItemDAO().atualizar(item);
		
		if (atualizacaoRealizada) {
			String itemJson = new Gson().toJson(item);
			resp.getWriter().write(itemJson);
		} else {
			Erro erro = new Erro();
			erro.setMensagem("Não existe um item com a mesma descrição!");
			String erroJson = new Gson().toJson(erro);
			resp.getWriter().write(erroJson);
		}
		
	}
	
}
