package io.khaminfo.askmore.exceptions;

public class CostumExeptionResponse {

    private String error;
    
    public CostumExeptionResponse(String projectIdentifier) {
        this.error = projectIdentifier;
    }
    public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	

  
}
