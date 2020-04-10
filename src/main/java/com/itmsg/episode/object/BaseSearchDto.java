package com.itmsg.episode.object;

public abstract class BaseSearchDto extends BaseDto {

    protected String _method;
    protected String searchKey;
    protected String searchVal;

	public String get_method() {
		return this._method;
	}

	public void set_method(String _method) {
		this._method = _method;
	}

	public String getSearchKey() {
		return this.searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchVal() {
		return this.searchVal;
	}

	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}

    protected PaginationInfo paginationInfo;
    protected Integer firstRowIndex;
    protected Integer lastRowIndex;

	public PaginationInfo getPaginationInfo() {
		return this.paginationInfo;
	}
	
	public Integer getFirstRowIndex() {
		return this.firstRowIndex;
	}

	public Integer getLastRowIndex() {
		return this.lastRowIndex;
	}

    public void setPage(PaginationInfo pi) {
        this.paginationInfo = pi;
        this.firstRowIndex = pi.getFirstRowIndex();
        this.lastRowIndex = pi.getLastRowIndex();
    }
}
