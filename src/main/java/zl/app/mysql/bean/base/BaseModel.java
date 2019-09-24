package zl.app.mysql.bean.base;

import java.io.Serializable;

import zl.app.annotation.TableAnnotation.Code;
import zl.app.annotation.TableAnnotation.ETableName;
import zl.app.annotation.TableAnnotation.EditColumns;
import zl.app.annotation.TableAnnotation.OrderList;
import zl.app.annotation.TableAnnotation.PageIndex;
import zl.app.annotation.TableAnnotation.PageSize;
import zl.app.annotation.TableAnnotation.Table_SQL;
import zl.app.annotation.TableAnnotation.strWhere;

public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public BaseModel() {

		this.PageSize = 50;
		this.PageIndex = 1;
	}

	private int PageSize;
	private int PageIndex;
    private int Code;
    
	private String StrWhere = "";
	private String OrderList = "";
	private String EditColumns = "";

	private String NewTableName = "";
    private String Table_SQL="";
	
    

	@Code(value = "")
	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}
	
	
    @Table_SQL(value = "")
	public String getTable_SQL() {
		return Table_SQL;
	}

	public void setTable_SQL(String table_SQL) {
		Table_SQL = table_SQL;
	}

	@ETableName(value = "")
	public String getNewTableName() {
		return NewTableName;
	}

	public void setNewTableName(String newTableName) {
		NewTableName = newTableName;
	}

	@PageSize(value = "10")
	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	@PageIndex(value = "1")
	public int getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(int pageIndex) {
		PageIndex = pageIndex;
	}

	@strWhere(value = "")
	public String getStrWhere() {
		return StrWhere;
	}

	public void setStrWhere(String strWhere) {
		StrWhere = strWhere;
	}

	@OrderList(value = "")
	public String getOrderList() {
		return OrderList;
	}

	public void setOrderList(String orderList) {
		OrderList = orderList;
	}

	@EditColumns(value = "")
	public String getEditColumns() {
		return EditColumns;
	}

	public void setEditColumns(String editColumns) {
		EditColumns = editColumns;
	}

}