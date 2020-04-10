package com.itmsg.episode.object;

import com.itmsg.episode.config.property.DejavuProperties;
import com.itmsg.episode.util.StringUtils;
import javax.servlet.http.HttpServletRequest;

public class PaginationInfo {

    /**
     * Required Fieldsd
     * - 이 필드들은 페이징 계산을 위해 반드시 초기에 세팅되어야 하는 필드 값들이다.
     * <p>
     * totalRowCount : 전체 게시물 건 수.(무조건 DB에서 갯수를 체크해서 읽어오는 값으로 -1로 세팅시 Integer.MAX_VALUE 갯수를 가지고 온다.)
     * currentPage : 현재 페이지 번호(생략시 파라미터의 세팅 값 참고)
     * rowPerPage : 한 페이지당 게시되는 게시물 건 수(생략시 파라미터의 세팅 값 참고)
     * pagePerBlock : 페이지 리스트에 게시되는 페이지 건수(생략시 파라미터의 세팅 값 참고)
     */

    private int currentPage;
    private int rowPerPage;
    private int pagePerBlock;
    private int totalRowCount;

	public int getCurrentPage() {
		return this.currentPage;
	}

	public int getRowPerPage() {
		return this.rowPerPage;
	}

	public int getPagePerBlock() {
		return this.pagePerBlock;
    }
    
	public int getTotalRowCount() {
		return this.totalRowCount;
    }
    
    /**
     * Not Required Fields
     * - 이 필드들은 Required Fields 값을 바탕으로 계산해서 정해지는 필드 값이다.
     * <p>
     * totalPageCount : 총 페이지 개수
     * firstIndexNo : 현재 페이지의 첫번째 행번호
     * lastPagePervBlock : 이전 블럭의 마지막 페이지
     * firstPageNextBlock : 다음 블럭의 첫 페이지
     * firstPageOnBlock : 페이지 블럭의 첫 페이지 번호
     * lastPageOnBlock : 페이지 블럭의 마지막 페이지 번호
     * firstRowIndex : 페이징 SQL의 조건절에 사용되는 시작 rownum
     * lastRowIndex : 페이징 SQL의 조건절에 사용되는 마지막 rownum
     * firstBlock : 현재 페이지 블럭이 첫번째 블럭인지 여부
     * lastBlock : 현재 페이지 블럭이 마지막 블럭인지 여부
     */

    private int totalPageCount;
    private int firstIndexNo;
    private int lastPagePervBlock;
    private int firstPageNextBlock;
    private int firstPageOnBlock;
    private int lastPageOnBlock;
    private int firstRowIndex;
    private int lastRowIndex;
    private boolean firstBlock;
    private boolean lastBlock;

	public int getTotalPageCount() {
		return this.totalPageCount;
	}

	public int getFirstIndexNo() {
		return this.firstIndexNo;
	}

	public int getLastPagePervBlock() {
		return this.lastPagePervBlock;
	}

	public int getFirstPageNextBlock() {
		return this.firstPageNextBlock;
	}

	public int getFirstPageOnBlock() {
		return this.firstPageOnBlock;
	}

	public int getLastPageOnBlock() {
		return this.lastPageOnBlock;
	}

	public int getFirstRowIndex() {
		return this.firstRowIndex;
	}

	public int getLastRowIndex() {
		return this.lastRowIndex;
	}

	public boolean getFirstBlock() {
		return this.firstBlock;
	}

	public boolean getLastBlock() {
		return this.lastBlock;
	}

    /**
     * 기본적인 생성자 전체 레코드수와 파라미터맵을 전달하면 된다.
     * @param totalRowCount
     * @param request
     */
    public PaginationInfo (int totalRowCount, HttpServletRequest request, DejavuProperties dejavuProperties) {

        this(totalRowCount,
                StringUtils.parseInt(request.getParameter("currentPage")),
                StringUtils.parseInt(request.getParameter("rowPerPage")),
                StringUtils.parseInt(request.getParameter("pagePerBlock")),
                dejavuProperties);
    }

    /**
     * 각 파라미터를 전부 수동으로 입력하는 생성자이다.
     * @param totalRowCount
     * @param currentPage
     * @param rowPerPage
     * @param pagePerBlock
     */
    public PaginationInfo (int totalRowCount, int currentPage, int rowPerPage, int pagePerBlock, DejavuProperties dejavuProperties) {

        this.totalRowCount = (0 <= totalRowCount) ? totalRowCount : Integer.MAX_VALUE;
        this.currentPage = (0 < currentPage) ? currentPage : 1;
        this.rowPerPage = (0 < rowPerPage) ? rowPerPage : dejavuProperties.getRowPerPage();
        this.pagePerBlock = (0 < pagePerBlock) ? pagePerBlock : dejavuProperties.getPagePerBlock();
        settingInfo(dejavuProperties);
    }

    private void settingInfo (DejavuProperties dejavuProperties) {

        totalPageCount = ((totalRowCount - 1) / rowPerPage) + 1;
        firstIndexNo = totalRowCount - (currentPage - 1) * rowPerPage;
        firstBlock = currentPage - pagePerBlock <= 0;
        firstPageOnBlock = ((currentPage - 1) / pagePerBlock) * pagePerBlock + 1;
        lastBlock = firstPageOnBlock + pagePerBlock > totalPageCount;
        lastPageOnBlock = lastBlock ? totalPageCount : firstPageOnBlock + pagePerBlock - 1;
        lastPagePervBlock = firstBlock ? 1 : firstPageOnBlock - 1;
        firstPageNextBlock = lastBlock ? totalPageCount : lastPageOnBlock + 1;
        switch (dejavuProperties.getSystemDbType()) {
            case "MySQL":
                firstRowIndex = currentPage <= 1 ? 0 : (currentPage - 1) * rowPerPage;
                lastRowIndex = rowPerPage;
                break;
            case "MS-SQL":
                firstRowIndex = currentPage;
                lastRowIndex = (firstRowIndex / rowPerPage - 1) * rowPerPage;
                break;
            default:
                firstRowIndex = currentPage * rowPerPage - (rowPerPage - 1);
                lastRowIndex = firstRowIndex + (rowPerPage - 1);
        }
    }

    @Override
    public String toString () {

        return "{currentPage=" + currentPage +
                ", rowPerPage=" + rowPerPage +
                ", pagePerBlock=" + pagePerBlock +
                ", totalRowCount=" + totalRowCount +
                ", totalPageCount=" + totalPageCount +
                ", firstIndexNo=" + firstIndexNo +
                ", lastPagePervBlock=" + lastPagePervBlock +
                ", firstPageNextBlock=" + firstPageNextBlock +
                ", firstPageOnBlock=" + firstPageOnBlock +
                ", lastPageOnBlock=" + lastPageOnBlock +
                ", firstRowIndex=" + firstRowIndex +
                ", lastRowIndex=" + lastRowIndex +
                ", firstBlock=" + firstBlock +
                ", lastBlock=" + lastBlock +
                '}';
    }
}
