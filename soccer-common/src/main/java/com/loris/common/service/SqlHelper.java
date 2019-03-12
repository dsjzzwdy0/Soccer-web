/**  
 * All rights Reserved, Designed By www.loris.com
 * @Title:  League.java   
 * @Package com.loris.soccer.model   
 * @Description: 本项目用于天津东方足彩数据的存储、共享、处理等   
 * @author: 东方足彩    
 * @date:   2019年1月28日 下午8:59:32   
 * @version V1.0.0
 * @Copyright: 2019 www.loris.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津东方足彩有限公司传阅，禁止外泄以及用于其他的商业目
 */
package com.loris.common.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loris.common.bean.Entity;
import com.loris.common.filter.Filter;
import com.loris.common.util.ArraysUtil;
import com.loris.common.util.ReflectUtil;

/**
 * @ClassName: League
 * @Description: 基本的SQL操作类，主要用于批量进行数据插入和更新
 * @author: 东方足彩
 * @date: 2019年1月28日 下午8:59:32
 * 
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于天津东方足彩有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
public class SqlHelper
{
	private static Logger logger = Logger.getLogger(SqlHelper.class);

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	/** 批量数据 */
	protected int batchSize = 1000;

	/**
	 * 批量插入数据
	 * 
	 * @param entities
	 * @return 是否成功的标志
	 */
	public boolean insertBatch(List<? extends Entity> entities) throws SQLException
	{
		Class<? extends Entity> clazz = entities.get(0).getClass();
		TableName tableName = clazz.getAnnotation(TableName.class);
		if (tableName == null)
		{
			throw new SQLException("The " + clazz.getName() + " has no table name defined.");
		}

		List<Field> fields = new ArrayList<>();
		getEntitySQLFields(clazz, fields);
		if (fields.size() <= 0)
		{
			throw new SQLException("The " + clazz.getName() + " has no field defined.");
		}

		String sql = createSQL(tableName, fields);
		//logger.info(sql);

		Connection connection = null;
		PreparedStatement ps = null;

		try
		{
			connection = jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);

			int i = 0;
			for (Entity entity : entities)
			{
				try
				{
					if (setFieldValue(entity, fields, ps))
					{
						ps.addBatch();
					}
					i++;
				}
				catch (IllegalAccessException e)
				{
				}
				if (i % batchSize == 0)
				{
					ps.executeBatch();
					connection.commit();
				}
			}

			if (i % batchSize != 0)
			{
				ps.executeBatch();
				connection.commit();
			}
			return true;
		}
		catch (SQLException e)
		{
			// e.printStackTrace();
			logger.warn(e.toString());
			try
			{
				if (connection != null)
				{
					connection.rollback();
				}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			throw e;
		}
		finally
		{
			if (ps != null)
			{
				ps.close();
			}
			if (connection != null)
			{
				connection.close();
			}
		}
	}

	/**
	 * 
	 * @param entity
	 * @param fields
	 * @param ps
	 */
	protected boolean setFieldValue(Entity entity, List<Field> fields, PreparedStatement ps)
			throws IllegalAccessException, SQLException
	{
		int index = 1;

		for (Field field : fields)
		{
			String typeName = field.getType().getName();
			switch (typeName)
			{
			case "java.lang.Boolean":
			case "boolean":
				// Boolean b = field.getBoolean(entity);
				Boolean b = getObject(field, entity);
				ps.setBoolean(index, b);
				break;
			case "bytes":
				byte[] bytes = (byte[]) field.get(entity);
				ps.setBytes(index, bytes);
				break;
			case "java.lang.Character":
			case "char":
				break;
			case "java.lang.Byte":
			case "byte":
				Byte b2 = getObject(field, entity);
				ps.setByte(index, b2);
				break;
			case "java.lang.Short":
			case "short":
				Short readShort = getObject(field, entity);
				ps.setShort(index, readShort);
				break;
			case "java.lang.Integer":
			case "int":
				Integer readInt = getObject(field, entity);
				ps.setInt(index, readInt);
				break;
			case "java.lang.Long":
			case "long":
				long l = getObject(field, entity);
				ps.setLong(index, l);
				break;
			case "java.lang.Float":
			case "float":
				Float readFloat = getObject(field, entity);
				ps.setFloat(index, readFloat);
				break;
			case "java.lang.Double":
			case "double":
				Double readDouble = getObject(field, entity);
				ps.setDouble(index, readDouble);
				break;
			case "java.lang.String":
				Object object = getObject(field, entity);
				String string = null;
				if (object != null)
				{
					string = object.toString();
				}
				ps.setString(index, string);
				break;
			case "java.util.Date":
				Date date = getObject(field, entity);
				if (date != null)
					ps.setTimestamp(index, new Timestamp(date.getTime()));
				else
				{
					ps.setDate(index, null);
				}
				break;
			default:
				throw new RuntimeException(typeName + "不支持，bug");
			}
			index++;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	protected <T> T getObject(Field field, Entity entity)
	{
		try
		{
			return (T) field.get(entity);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			return null;
		}
	}

	/**
	 * 创建拼接插入数据的SQL语句
	 * 
	 * @param tableName
	 * @param fields
	 * @return
	 */
	protected String createSQL(TableName tableName, List<Field> fields)
	{
		String sql = "insert into " + tableName.value() + " (";
		String values = " values(";

		int fieldIndex = 0;
		for (Field field : fields)
		{
			fieldIndex++;
			sql += field.getName();
			values += "?";

			if (fieldIndex != fields.size())
			{
				sql += ", ";
				values += ", ";
			}
		}
		sql += ") " + values + ")";
		return sql;
	}

	/**
	 * 获得实体所有的字段
	 * 
	 * @param clazz
	 * @param fields
	 */
	protected void getEntitySQLFields(Class<? extends Entity> clazz, List<Field> fields)
	{
		List<Field> allFields = ReflectUtil.getAllFields(clazz, false);
		for (Field field : allFields)
		{
			if (Modifier.isStatic(field.getModifiers()))
			{
				continue;
			}
			TableField tableField = field.getAnnotation(TableField.class);
			if (tableField == null || tableField.exist())
			{
				field.setAccessible(true);
				fields.add(field);
			}
		}
	}
	
	/**
	 * 插入数据列表
	 * @param values 列表值
	 * @param key 关键字段
	 * @param mapper 数据接口
	 * @param helper 数据通用接口
	 * @return 是否成功的标志
	 */
	public static<T extends Entity> boolean insertList(List<T> values, Class<T> clazz, BaseMapper<T> mapper,
			Filter<T> filter, String key,  SqlHelper helper, boolean overwrite)
	{
		if(values == null || values.size() == 0)
		{
			logger.warn("Warn: No " + clazz.getName() + " need to be updated.");
			return false;
		}
		List<String> tids = ArraysUtil.getObjectFieldValue(values, clazz, key);
		QueryWrapper<T> queryWrapper = new QueryWrapper<T>().in(key, tids);
		if(overwrite)
		{
			mapper.delete(queryWrapper);
		}
		else
		{
			List<T> existValues = mapper.selectList(queryWrapper);
			List<T> newValues = new ArrayList<>();
			
			for (T team : values)
			{
				filter.setValue(team);
				if (!ArraysUtil.hasSameObject(existValues, filter))
				{
					newValues.add(team);
				}
			}
			
			if (newValues.size() == 0)
			{
				logger.warn("Warn: No " + clazz.getName() + " need to be updated.");
				return true;
			}
			values = newValues;			
		}
		try
		{
			return helper.insertBatch(values);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
}
