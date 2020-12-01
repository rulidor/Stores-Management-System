package ServiceLayer;

public class Response {
	
	private boolean errorOccured;
	private String message;
	
	public Response() {
		errorOccured = false;
		message = "Done";
	}
	
	public Response(String msg) {
		errorOccured = true;
		message = msg;
	}

	public boolean isErrorOccured() {
		return errorOccured;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String msg){
		message = msg;
	}

}
