package zl.app.utility;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Map.Entry;

public class CTypeConvert {

	public static void transMap2Bean(Map<String, Object> map, Object target_object) {

		if (map == null || target_object == null) {
			return;
		}

		try {

			Class target_model = target_object.getClass();
			Field[] target_fs = target_model.getDeclaredFields();
			for (int i = 0; i < target_fs.length; i++) {

				Field target_field = target_fs[i];
				target_field.setAccessible(true);

				for (Entry<String, Object> entry : map.entrySet()) {

					// System.out.println(entry.getKey() + ":" + entry.getValue());

					if (entry.getKey().trim().toLowerCase().equals(target_field.getName().trim().toLowerCase())) {
						target_field.set(target_object, entry.getValue());
						break;
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void Model_SetValue_ModelReq(Object srouce_object, Object target_object) throws Exception {
		if (srouce_object == null) {
			return;
		}
		Class source_model = srouce_object.getClass();
		Class target_model = target_object.getClass();

		Field[] source_fs = source_model.getDeclaredFields();
		Field[] target_fs = target_model.getDeclaredFields();

		for (int i = 0; i < target_fs.length; i++) {

			Field target_field = target_fs[i];
			target_field.setAccessible(true);

			Field source_filed = GetField(source_fs, target_field.getName());
			if (source_filed != null) {

				source_filed.setAccessible(true);
				Object value = source_filed.get(srouce_object);// 寰楀埌姝ゅ睘鎬х殑鍊�

				if (value != null) {

					if ("Date".toLowerCase().equals(source_filed.getType().getSimpleName().toLowerCase())) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sDate = sdf.format(value);
						target_field.set(target_object, String.valueOf(sDate));
					} else {
						target_field.set(target_object, String.valueOf(value));

					}

					// Object val1 = target_field.get(target_object);//寰楀埌姝ゅ睘鎬х殑鍊�

					// System.out.println("sname:"+source_filed.getName()+"\t
					// value = "+val1);

				}

			}
		}
	}

	 
	public static void ModelReq_SetValue_Model(Object srouce_object, Object target_object) throws Exception {

		if (srouce_object == null) {
			return;
		}
		Class source_model = srouce_object.getClass();
		Class target_model = target_object.getClass();

		Field[] source_fs = source_model.getDeclaredFields();
		Field[] target_fs = target_model.getDeclaredFields();

		for (int i = 0; i < source_fs.length; i++) {

			Field source_field = source_fs[i];
			source_field.setAccessible(true);

			Field target_field = GetField(target_fs, source_field.getName());
			if (target_field != null) {

				target_field.setAccessible(true);
				Object value = source_field.get(srouce_object);// 寰楀埌姝ゅ睘鎬х殑鍊�
				if (null != value) {

					if (value.toString().length() > 0||"string".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {

						// System.out.println(String.format("%s---%s",target_field.getType().getSimpleName(),value));

						if ("boolean".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {

							target_field.set(target_object, Boolean.parseBoolean(value.toString()));
							continue;
						} else if ("BigInteger".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {

							target_field.set(target_object, BigInteger.valueOf(Long.parseLong(value.toString())));
							continue;
						} else if ("Integer".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {

							target_field.set(target_object, Integer.parseInt(value.toString()));
							continue;
						} else if ("BigDecimal".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {

							target_field.set(target_object, BigDecimal.valueOf(Double.parseDouble(value.toString())));
							continue;
						} else if ("long".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {

							target_field.set(target_object, Long.parseLong(value.toString()));
							continue;
						} else if ("float".trim().toLowerCase().equals(target_field.getType().getSimpleName().trim().toLowerCase())) {

							target_field.set(target_object, Float.parseFloat(value.toString()));
							continue;
						} else if ("string".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {
							target_field.set(target_object, value.toString());
							continue;
						} else if ("double".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {

							target_field.set(target_object, Double.parseDouble(value.toString()));
							continue;
						} else if ("int".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {

							target_field.set(target_object, Integer.parseInt(value.toString()));
							continue;
						} else if ("UUID".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {

							target_field.set(target_object, value);
							continue;
						}else if ("Time".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {
							target_field.set(target_object, value); 
						}else if ("Date".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {

							java.util.Date date = null;

							if (value.toString().indexOf(":") > -1) {
								date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString());
							} else {
								date = new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
							}

							target_field.set(target_object, date);
							continue;
						}else if("Timestamp".toLowerCase().equals(target_field.getType().getSimpleName().toLowerCase())) {
							target_field.set(target_object, value);
							continue;
						}

					}

				}

			} 

		}

	}
	
	private static Field GetField(Field[] source_fs, String target_field_name) {
		Field result = null;
		for (int i = 0; i < source_fs.length; i++) {
			Field f = source_fs[i];
			String source_col_name = f.getName().replace("_", "").toLowerCase().trim();
			String target_col_name = target_field_name.replace("_", "").toLowerCase().trim();
			if (source_col_name.equals(target_col_name)) {
				result = f;
				break;
			}
		}
		return result;
	}
	
}
