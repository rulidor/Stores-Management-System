package ServiceLayer;

public class ResponseT<T> extends Response {
	
	private T obj;
	
	public ResponseT(T obj) {
		super();
		this.obj =obj;
	}
	
	public ResponseT(String msg) {
		super(msg);
		obj = null;
	}

	public T getObj() {
		return obj;
	}

}
