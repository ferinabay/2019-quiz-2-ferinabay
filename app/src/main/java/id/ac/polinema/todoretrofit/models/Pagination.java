package id.ac.polinema.todoretrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {

	@SerializedName("page")
	@Expose
	private Integer page;
	@SerializedName("pageSize")
	@Expose
	private Integer pageSize;
	@SerializedName("rowCount")
	@Expose
	private Integer rowCount;
	@SerializedName("pageCount")
	@Expose
	private Integer pageCount;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
}
