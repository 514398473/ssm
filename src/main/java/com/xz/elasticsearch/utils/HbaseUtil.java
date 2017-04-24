package com.xz.elasticsearch.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xz.elasticsearch.model.Doc;

public class HbaseUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HbaseUtil.class);
	
	/**
	 * HBASE 表名称
	 */
	public final String TABLE_NAME = "doc";
	/**
	 * 列簇1 文章信息
	 */
	public final String COLUMNFAMILY_1 = "cf1";
	/**
	 * 列簇1中的列
	 */
	public final String COLUMNFAMILY_1_TITLE = "title";
	public final String COLUMNFAMILY_1_AUTHOR = "author";
	public final String COLUMNFAMILY_1_CONTENT = "content";
	public final String COLUMNFAMILY_1_DESCRIBE = "describe";

	public static Admin admin = null;
	public static Configuration conf = null;
	public static Connection conn = null;

	/**
	 * 构造函数加载配置
	 */
	public HbaseUtil() {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "node1,node2,node3");
		try {
			conn = ConnectionFactory.createConnection(conf);
			admin = conn.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * rowFilter的使用
	 * 
	 * @param tableName
	 * @param reg
	 * @throws Exception
	 */
	public void getRowFilter(String tableName, String reg) throws Exception {
		Table table = conn.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		// Filter
		RowFilter rowFilter = new RowFilter(CompareOp.NOT_EQUAL, new RegexStringComparator(reg));
		scan.setFilter(rowFilter);
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			LOGGER.info(new String(result.getRow()));
		}
	}

	@SuppressWarnings("deprecation")
	public void getScanData(String tableName, String family, String qualifier) throws Exception {
		Table table = conn.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		scan.addColumn(family.getBytes(), qualifier.getBytes());
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			if (result.raw().length == 0) {
				LOGGER.warn(tableName + " 表数据为空！");
			} else {
				for (KeyValue kv : result.raw()) {
					LOGGER.info(new String(kv.getKey()) + "\t" + new String(kv.getValue()));
				}
			}
		}
	}

	/**
	 * 删除表
	 * 
	 * @param tableName
	 */
	public void deleteTable(String tableName) {
		try {
			if (admin.tableExists(TableName.valueOf(tableName))) {
				admin.disableTable(TableName.valueOf(tableName));
				admin.deleteTable(TableName.valueOf(tableName));
				LOGGER.info(tableName + "表删除成功！");
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(tableName + "表删除失败！");
		}

	}

	/**
	 * 删除一条记录
	 * 
	 * @param tableName
	 * @param rowKey
	 */
	public void deleteOneRecord(String tableName, String rowKey) throws IOException {
		Table table = conn.getTable(TableName.valueOf(tableName));
		Delete delete = new Delete(rowKey.getBytes());
		try {
			table.delete(delete);
			LOGGER.info(rowKey + "记录删除成功！");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(rowKey + "记录删除失败！");
		}
	}

	/**
	 * 获取表的所有数据
	 * 
	 * @param tableName
	 */
	@SuppressWarnings("deprecation")
	public void getALLData(String tableName) {
		try {
			Table table = conn.getTable(TableName.valueOf(tableName));
			Scan scan = new Scan();
			ResultScanner scanner = table.getScanner(scan);
			for (Result result : scanner) {
				if (result.raw().length == 0) {
					LOGGER.warn(tableName + " 表数据为空！");
				} else {
					for (KeyValue kv : result.raw()) {
						LOGGER.info(new String(kv.getKey()) + "\t" + new String(kv.getValue()));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 读取一条记录 
	 * @param tableName
	 * @param row
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "deprecation" })
	public Doc get(String tableName, String row) throws IOException {
		Table table = conn.getTable(TableName.valueOf(tableName));
		Get get = new Get(row.getBytes());
		Doc Doc = null;
		try {

			Result result = table.get(get);
			KeyValue[] raw = result.raw();
			if (raw.length == 4) {
				Doc = new Doc();
				Doc.setId(Integer.parseInt(row));
				Doc.setTitle(new String(raw[3].getValue()));
				Doc.setAuthor(new String(raw[0].getValue()));
				Doc.setContent(new String(raw[1].getValue()));
				Doc.setDescribe(new String(raw[2].getValue()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Doc;
	}

	/**
	 * 添加一条记录
	 * @param tableName
	 * @param row
	 * @param columnFamily
	 * @param column
	 * @param data
	 * @throws IOException
	 */
	public void put(String tableName, String row, String columnFamily, String column, String data) throws IOException {
		Table table = conn.getTable(TableName.valueOf(tableName));
		Put p1 = new Put(Bytes.toBytes(row));
		p1.addColumn(columnFamily.getBytes(), column.getBytes(), data.getBytes());
		table.put(p1);
		LOGGER.info("put'" + row + "'," + columnFamily + ":" + column + "','" + data + "'");
	}

	/**
	 * 查询所有表名
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<String> getALLTable() throws Exception {
		ArrayList<String> tables = new ArrayList<String>();
		if (admin != null) {
			HTableDescriptor[] listTables = admin.listTables();
			if (listTables.length > 0) {
				for (HTableDescriptor tableDesc : listTables) {
					tables.add(tableDesc.getNameAsString());
					LOGGER.info(tableDesc.getNameAsString());
				}
			}
		}
		return tables;
	}

	/**
	 * 创建一张表
	 * 
	 * @param tableName
	 * @param column
	 * @throws Exception
	 */
	public void createTable(String tableName, String column) throws Exception {
		if (admin.tableExists(TableName.valueOf(tableName))) {
			LOGGER.warn(tableName + "表已经存在！");
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
			tableDesc.addFamily(new HColumnDescriptor(column.getBytes()));
			admin.createTable(tableDesc);
			LOGGER.info(tableName + "表创建成功！");
		}
	}
}
