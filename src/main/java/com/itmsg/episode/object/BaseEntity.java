package com.itmsg.episode.object;

public abstract class BaseEntity {

    protected String createId;   // 생성자ID
    protected String updateId;   // 변경자ID
    protected String useYn;      // 사용여부

	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getUpdateId() {
		return this.updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getUseYn() {
		return this.useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

    public String toString() {
        return "BaseEntity(super="+super.toString()+",createId="+createId+",updateId="+updateId+",useYn"+useYn;
    }

    public abstract String getKeys();
}
