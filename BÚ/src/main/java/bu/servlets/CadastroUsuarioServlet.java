package bu.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bu.dao.UsuarioDAO;
import bu.modelo.Usuario;
import bu.util.Erro;

@WebServlet(name = "CadastroUsuarioServlet", urlPatterns = { "/cadastroUsuario" })
public class CadastroUsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/json;charset=UTF-8");
		
		StringBuffer parametro = new StringBuffer();
		String line = null;
		
		BufferedReader reader = req.getReader();
		while ((line = reader.readLine()) != null)
			parametro.append(line.trim());
		
		
		Usuario usuario = new Gson().fromJson(parametro.toString(), Usuario.class);
		
		boolean cadastroRealizado = new UsuarioDAO().cadastrar(usuario);
		
		if (cadastroRealizado) {
			String usuarioJson = new Gson().toJson(usuario);
			resp.getWriter().write(usuarioJson);
		} else {
			Erro erro = new Erro();
			erro.setMensagem("Já existe um usuario com o email informado!");
			String erroJson = new Gson().toJson(erro);
			resp.getWriter().write(erroJson);
		}
		
	}
	
}
