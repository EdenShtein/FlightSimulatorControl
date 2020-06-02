package interpreter;
import java.util.*;

public class GenericFactory<Product> {
	
	private interface Creator<Product>{
		public Product create(); // no unhandled exceptions
	}
	
	Map<String,Creator> map;
	
	public GenericFactory(){
		map=new HashMap<>();
	}
	
	public void insertProduct(String key, Class<? extends Product> c) {
        map.put(key, new Creator<Product>(){
            @Override
            public Product create() {
                    try {
                            return c.newInstance();
                    } catch (InstantiationException e) {
                            e.printStackTrace();
                    } catch (IllegalAccessException e) {
                            e.printStackTrace();
                    }
                    return null;
            }
        });
	}
	
	public Product getNewProduct(String key){
        if (map.containsKey(key))
            return (Product)map.get(key).create();
		return null;
	}
}
