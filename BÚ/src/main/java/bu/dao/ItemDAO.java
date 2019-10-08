package bu.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import bu.modelo.Item;
import bu.modelo.Usuario;

public class ItemDAO {

	public List<Item> listarItens(){
		List<Item> itens = new ArrayList<Item>();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query query = new Query("Item");
		Filter filtro = new FilterPredicate("disponivel", FilterOperator.EQUAL, true);
		query.setFilter(filtro);
		
		PreparedQuery pq = datastore.prepare(query);
		
		List<Entity> entidades = pq.asList(FetchOptions.Builder.withLimit(1000));
		for (Entity entidade : entidades) {
			Item item = new Item();
			item.setDescricao((String)entidade.getProperty("descricao"));
			item.setDetalhes((String)entidade.getProperty("detalhes"));
			item.setDisponivel((Boolean)entidade.getProperty("disponivel"));
			
			Usuario doador = new Usuario();
			doador.setEmail((String)entidade.getProperty("doador"));
			item.setDoador(doador);
			
			itens.add(item);
		}
		
		return itens;
	}
	
	public boolean cadastrar(Item item) {
		
		if (!this.existeItemCadastrado(item.getDescricao())) {
			Key chavePrimaria = KeyFactory.createKey("Item", item.getDescricao()); 
			
			Entity entidadeItem = new Entity(chavePrimaria);
			entidadeItem.setProperty("descricao", item.getDescricao());
			entidadeItem.setProperty("detalhes", item.getDetalhes());
			entidadeItem.setProperty("disponivel", item.getDisponivel());
			entidadeItem.setProperty("doador", item.getDoador().getEmail());
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(entidadeItem);
			
			return true;
		}
		
		return false;
	}
	
	public boolean atualizar(Item item) {
		
		Entity itemDB = this.getItem(item.getDescricao());
		
		if (itemDB != null) {
			
			itemDB.setProperty("descricao", item.getDescricao());
			itemDB.setProperty("detalhes", item.getDetalhes());
			itemDB.setProperty("disponivel", item.getDisponivel());
			itemDB.setProperty("doador", item.getDoador().getEmail());
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(itemDB);
			
			return true;
		}
		
		return false;
	}
	
	public Entity getItem(String descricao) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query query = new Query("Item");
		Filter filtro = new FilterPredicate("descricao", FilterOperator.EQUAL, descricao);
		query.setFilter(filtro);
		
		PreparedQuery pq = datastore.prepare(query);
		
		Entity entidade = pq.asSingleEntity();
		
		if (entidade != null) {
			return entidade;	
		}
		
		return null;
	}
	
	public boolean existeItemCadastrado(String descricao) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query query = new Query("Item");
		Filter filtro = new FilterPredicate("descricao", FilterOperator.EQUAL, descricao);
		query.setFilter(filtro);
		
		PreparedQuery pq = datastore.prepare(query);
		if (pq.asSingleEntity() == null) {
			return false;
		}
		
		return true;
	}
	
}
