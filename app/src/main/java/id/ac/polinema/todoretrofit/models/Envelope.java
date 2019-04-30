package id.ac.polinema.todoretrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Envelope<T> {

	@SerializedName("statusCode")
	@Expose
	private Integer statusCode;
	@SerializedName("error")
	@Expose
	private String error;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("pagination")
	@Expose
	private Pagination pagination;
	@SerializedName("data")
	@Expose
	private T data;

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
