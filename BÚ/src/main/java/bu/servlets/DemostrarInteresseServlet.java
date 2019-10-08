package bu.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bu.dao.InteresseDAO;
import bu.modelo.Interesse;
import bu.util.Erro;

@WebServlet(name = "DemostrarInteresseServlet", urlPatterns = { "/demostrarInteresse" })
public class DemostrarInteresseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=UTF-8");

		StringBuffer parametro = new StringBuffer();
		String line = null;

		BufferedReader reader = req.getReader();
		while ((line = reader.readLine()) != null)
			parametro.append(line.trim());

		Interesse interesse = new Gson().fromJson(parametro.toString(), Interesse.class);

		boolean cadastroRealizado = new InteresseDAO().cadastrar(interesse);
		
		if (cadastroRealizado) {
			String itemJson = new Gson().toJson(interesse);
			resp.getWriter().write(itemJson);
		} else {
			Erro erro = new Erro();
			erro.setMensagem("Não foi possível cadastrar o interesse!");
			String erroJson = new Gson().toJson(erro);
			resp.getWriter().write(erroJson);
		}
	}

}
