package bu.dao;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import bu.modelo.Interesse;

public class InteresseDAO {

	public boolean cadastrar(Interesse interesse) {

		Entity entidadeInteresse = new Entity("Interesse");
		entidadeInteresse.setProperty("usuario", interesse.getInteressado().getEmail());
		entidadeInteresse.setProperty("detalhes", interesse.getItem().getDescricao());

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entidadeInteresse);

		return true;
		
	}

}
