package com.xz.elasticsearch.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.xz.elasticsearch.model.Doc;
import com.xz.elasticsearch.service.ElasticsearchService;
import com.xz.elasticsearch.utils.ElasticsearchUtil;
import com.xz.elasticsearch.utils.HbaseUtil;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

	@Override
	public void insert(String dataPath, String indexPath) {
		try {
			List<Doc> arrayList = new ArrayList<Doc>();
			//TODO 这里做测试写的数据  可以根据实际情况换成真实数据
			File file = new File("C:\\Users\\Administrator\\Desktop\\article.txt");
			List<String> list = FileUtils.readLines(file, "GBK");
			for (String line : list) {
				Doc Doc = new Doc();
				String[] split = line.split("\t");
				int parseInt = Integer.parseInt(split[0].trim());
				Doc.setId(parseInt);
				Doc.setTitle(split[1]);
				Doc.setAuthor(split[2]);
				Doc.setDescribe(split[3]);
				Doc.setContent(split[3]);
				arrayList.add(Doc);
			}
			HbaseUtil hbaseUtils = new HbaseUtil();
			for (Doc Doc : arrayList) {

				// 把数据插入hbase
				hbaseUtils.put(hbaseUtils.TABLE_NAME, Doc.getId() + "", hbaseUtils.COLUMNFAMILY_1,
						hbaseUtils.COLUMNFAMILY_1_TITLE, Doc.getTitle());
				hbaseUtils.put(hbaseUtils.TABLE_NAME, Doc.getId() + "", hbaseUtils.COLUMNFAMILY_1,
						hbaseUtils.COLUMNFAMILY_1_AUTHOR, Doc.getAuthor());
				hbaseUtils.put(hbaseUtils.TABLE_NAME, Doc.getId() + "", hbaseUtils.COLUMNFAMILY_1,
						hbaseUtils.COLUMNFAMILY_1_DESCRIBE, Doc.getDescribe());
				hbaseUtils.put(hbaseUtils.TABLE_NAME, Doc.getId() + "", hbaseUtils.COLUMNFAMILY_1,
						hbaseUtils.COLUMNFAMILY_1_CONTENT, Doc.getContent());
				// 把数据插入es
				ElasticsearchUtil.addIndex("bjsxt", "doc", Doc);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
