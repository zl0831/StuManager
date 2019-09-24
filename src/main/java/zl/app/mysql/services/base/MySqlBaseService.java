package zl.app.mysql.services.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import zl.app.annotation.TableAnnotation.Code;
import zl.app.annotation.TableAnnotation.Column;
import zl.app.annotation.TableAnnotation.ETableName;
import zl.app.annotation.TableAnnotation.EditColumns;
import zl.app.annotation.TableAnnotation.Id;
import zl.app.annotation.TableAnnotation.OrderList;
import zl.app.annotation.TableAnnotation.PageIndex;
import zl.app.annotation.TableAnnotation.PageSize;
import zl.app.annotation.TableAnnotation.Table;
import zl.app.annotation.TableAnnotation.Table_SQL;
import zl.app.annotation.TableAnnotation.strWhere;
import zl.app.enums.DBActionTypeEnum;
import zl.app.mysql.bean.base.BaseModel;
import zl.app.mysql.mapper.base.IMySqlBaseMapper;
import zl.app.utility.CString;
import zl.app.utility.CTypeConvert;

public class MySqlBaseService<T extends BaseModel> {

	private String BASE_Code = "Code";
	private String BASE_Code_VALUE = "Code_Value";

	private String BASE_TABLE_NAME = "TABLE_NAME";

	private String BASE_TABLE_SQL_VALUE = "TABLE_SQL";

	private String BASE_COLUMNS = "COLUMNS";
	private String BASE_VALUES = "VALUES";

	private String BASE_COLUMN = "COLUMN";
	private String BASE_COLUMN_Value = "COL_VALUE";

	private String BASE_DATA = "DATA";

	private String BASE_KEY_ID = "KEY_ID";// 主键名
	private String BASE_KEY_VALUE = "KEY_VALUE";// 主键值

	private String BASE_OrderList = "OrderList";// 需要更新的字段
	private String BASE_OrderList_Value = "OrderList_Value";

	private String BASE_strWhere = "strWhere";
	private String BASE_strWhere_Value = "strWhere_Value";

	private String BASE_PageSize = "PageSize";
	private String BASE_PageSize_Value = "PageSize_Value";

	private String BASE_PageIndex = "PageIndex";
	private String BASE_PageIndex_Value = "PageIndex_Value";

	private String BASE_EditColumns = "BASE_EditColumns";
	private String BASE_EditColumns_Value = "BASE_EditColumns_Value";

	private Integer Psize = 1;

	private IMySqlBaseMapper baseMapper;// 定义Mapper

	public void setBaseMapper(IMySqlBaseMapper baseMapper) {
		this.baseMapper = baseMapper;
	}

	private Object getInvokeValue(Object t, Method i) throws Exception {
		try {
			return i.invoke(t, null);
		} catch (IllegalAccessException e) {
			throw new Exception("Error Input Object! Error Invoke Get Method.", e);
		} catch (InvocationTargetException e) {
			throw new Exception("Error Input Object! Error Invoke Get Method.", e);
		}
	}

	private Map<String, Object> transformObj(Object t, int type) {

		Map<String, Object> Columns_Map = new HashMap<String, Object>();

		try {

			// 获取表名
			if (null == t.getClass().getAnnotation(Table.class)) {

				throw new Exception("Error Input Object! Error @Table Annotation.");

			}

			String tableName = t.getClass().getAnnotation(Table.class).value();

			Columns_Map.put(BASE_TABLE_NAME, tableName);

			Method[] m = t.getClass().getMethods();
			if (null == m || m.length <= 0) {
				throw new Exception("Error Input Object! No Method.");
			}

			for (Method i : m) {

				// 主要是为了兼容动态访问表的场景
				if (null != i.getAnnotation(ETableName.class)) {

					String etableName = String.format("%s", i.invoke(t, null));
					if (!CString.IsNullOrEmpty(etableName)) {
						Columns_Map.put(BASE_TABLE_NAME, etableName);
						// System.out.println("etableName:" + etableName);
					}

				}

				if (null != i.getAnnotation(Code.class)) {
					Columns_Map.put(BASE_Code, i.getAnnotation(Code.class).value());
					Columns_Map.put(BASE_Code_VALUE, i.invoke(t, null));
					// System.out.println(i.invoke(t, null));
				}

				if (null != i.getAnnotation(strWhere.class)) {
					Columns_Map.put(BASE_strWhere, i.getAnnotation(strWhere.class).value());
					Columns_Map.put(BASE_strWhere_Value, i.invoke(t, null));
					// System.out.println(i.invoke(t, null));
				}

				if (null != i.getAnnotation(Table_SQL.class)) {

					Columns_Map.put(BASE_TABLE_SQL_VALUE, i.invoke(t, null));
					System.out.println(String.format("sql:%s", i.invoke(t, null)));
				}

				if (null != i.getAnnotation(OrderList.class)) {
					Columns_Map.put(BASE_OrderList, i.getAnnotation(OrderList.class).value());
					Columns_Map.put(BASE_OrderList_Value, i.invoke(t, null));
				}

				if (null != i.getAnnotation(PageSize.class)) {

					Columns_Map.put(this.BASE_PageSize, i.getAnnotation(PageSize.class).value());

					Psize = (Integer) i.invoke(t, null);
					Columns_Map.put(this.BASE_PageSize_Value, Psize);

					// System.out.println("Psize:"+Psize);
				}

				if (null != i.getAnnotation(PageIndex.class)) {
					Columns_Map.put(this.BASE_PageIndex, i.getAnnotation(PageIndex.class).value());

					Integer Pindex = (Integer) i.invoke(t, null);
					// System.out.println("前Pindex:"+Pindex);
					Pindex = (Pindex - 1) * Psize;
					// System.out.println("后Pindex:"+Pindex);
					Columns_Map.put(this.BASE_PageIndex_Value, Pindex);
				}

				if (null != i.getAnnotation(EditColumns.class)) {
					Columns_Map.put(this.BASE_EditColumns, i.getAnnotation(EditColumns.class).value());
					Columns_Map.put(this.BASE_EditColumns_Value, i.invoke(t, null));
				}

			}

			// System.out.println("BASE_EditColumns:"+Columns_Map.get(this.BASE_EditColumns));
			// System.out.println("BASE_EditColumns_Value:"+Columns_Map.get(this.BASE_EditColumns_Value));

			if (type == DBActionTypeEnum.Insert.getCode()) {
				// insert数据结构
				List k = new ArrayList();// 存放列名
				List v = new ArrayList();// 存放列值
				for (Method i : m) {

					if (null != i.getAnnotation(Id.class)) {
						Columns_Map.put(BASE_KEY_ID, i.getAnnotation(Id.class).value());
						Columns_Map.put(BASE_KEY_VALUE, i.invoke(t, null));
					}

					// 获取列名和值
					if (null != i.getAnnotation(Column.class)) {
						k.add(i.getAnnotation(Column.class).value());
						v.add(getInvokeValue(t, i));
						continue;
					}

				}
				if (k.size() != v.size()) {
					throw new Exception("Error Input Object! Internal Error.");
				}
				Columns_Map.put(BASE_COLUMNS, k);
				Columns_Map.put(BASE_VALUES, v);
				// System.out.println(String.format("k:%s---v:%s", k,v));
			}

			if (type == DBActionTypeEnum.Update.getCode()) {
				// update数据结构
				List d = new ArrayList();
				Map<String, Object> map = null;
				for (Method i : m) {
					map = new HashMap<>();
					if (null != i.getAnnotation(Column.class) && null != getInvokeValue(t, i)) {

						Object EditColumns_Value = Columns_Map.get(this.BASE_EditColumns_Value);

						if (EditColumns_Value != null) {

							if (EditColumns_Value.toString().trim().equals("*")) {
								map.put(this.BASE_COLUMN, i.getAnnotation(Column.class).value());
								map.put(this.BASE_COLUMN_Value, getInvokeValue(t, i));
								d.add(map);
								continue;
							}

							String Columns_Name = i.getAnnotation(Column.class).value();
							if (EditColumns_Value.toString().trim().toLowerCase()
									.contains(Columns_Name.trim().toLowerCase())) {
								map.put(this.BASE_COLUMN, i.getAnnotation(Column.class).value());
								map.put(this.BASE_COLUMN_Value, getInvokeValue(t, i));
								d.add(map);
								continue;
							}

						}

					}
					if (null != i.getAnnotation(Id.class)) {
						Columns_Map.put(BASE_KEY_ID, i.getAnnotation(Id.class).value());
						Columns_Map.put(BASE_KEY_VALUE, i.invoke(t, null));
					}

				}
				Columns_Map.put(this.BASE_DATA, d);

			}

			if (type == DBActionTypeEnum.Common.getCode()) {

				List k = new ArrayList();// 存放列名
				// common数据结构
				for (Method i : m) {

					// 获取列名和值
					if (null != i.getAnnotation(Column.class)) {
						k.add(i.getAnnotation(Column.class).value());
						continue;
					}

					if (null != i.getAnnotation(Id.class)) {
						Columns_Map.put(BASE_KEY_ID, i.getAnnotation(Id.class).value());
						Columns_Map.put(BASE_KEY_VALUE, i.invoke(t, null));
						// System.out.println("BASE_KEY_VALUE:"+Columns_Map.get(BASE_KEY_VALUE));
						k.add(i.getAnnotation(Id.class).value());

					}

					Columns_Map.put(BASE_COLUMNS, k);
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return Columns_Map;

	}

	@SuppressWarnings("unchecked")
	protected T getModelByID(Object obj) {

		Object Target_Object = null;

		try {

			Target_Object = obj.getClass().newInstance();

			Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());

			Map<String, Object> data = baseMapper.getModelByID(params);
			if (data == null) {
				Target_Object = null;
			} else {
				CTypeConvert.transMap2Bean(data, Target_Object);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return (T) Target_Object;

	}

	@SuppressWarnings("unchecked")
	protected T getModelBystrWhere(Object obj) {

		Object Target_Object = null;

		try {

			Target_Object = obj.getClass().newInstance();

			Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());

			Map<String, Object> data = baseMapper.getModelBystrWhere(params);

			if (data == null) {
				Target_Object = null;
			} else {
				CTypeConvert.transMap2Bean(data, Target_Object);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return (T) Target_Object;

	}

	protected List<Map<String, Object>> getListByStrWhere(Object obj) throws Exception {
		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());
		return baseMapper.getListByStrWhere(params);
	}

	protected List<Map<String, Object>> getListByPage_Fast(Object obj) {
		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());
		return baseMapper.getListByPage_Fast(params);
	}

	// 适用不带where查询的情况
	protected List<Map<String, Object>> getListByPage_Fast_NoWhere(Object obj) {

		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());
		return baseMapper.getListByPage_Fast_NoWhere(params);
	}

	// 适用不带where查询的情况
	protected List<Map<String, Object>> getListByPage_Slow_ManyTable(Object obj) {

		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());
		return baseMapper.getListByPage_Slow_ManyTable(params);
	}

	protected List<Map<String, Object>> getListByPage(Object obj) {

		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());
		return baseMapper.getListByPage(params);
	}

	protected List<Map<String, Object>> getListByPage_Slow(Object obj) {
		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());
		return baseMapper.getListByPage_Slow(params);
	}

	protected int getCount(Object obj) {
		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());
		return baseMapper.getCount(params);
	}

	protected int getCountBySql(Object obj) {
		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());
		return baseMapper.getCountBySql(params);
	}

	protected int deleteById(T obj) throws Exception {
		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());
		return baseMapper.deleteById(params);

	}

	protected int deletebyWhere(T obj) {
		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());
		return baseMapper.deletebyWhere(params);
	}

	protected int update(T obj) {
		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Update.getCode());
		return baseMapper.update(params);
	}

	protected long insert(T obj) {

		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Insert.getCode());

		baseMapper.insert(params);

		Optional<Entry<String, Object>> item = params.entrySet().stream().filter(c -> c.getKey().equals("Code"))
				.findFirst();
		if (item.isPresent()) {

			var data = item.get();
			if (data != null) {
				return Long.valueOf(String.format("%s", data.getValue()));
			}
		}

		return 0;

	}

	// 判断表名是否存在
	protected int existsTable(Object obj) {
		Map<String, Object> params = transformObj(obj, DBActionTypeEnum.Common.getCode());
		return baseMapper.existsTable(params);
	}
}