package com.xz.elasticsearch.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xz.base.controller.BaseController;
import com.xz.elasticsearch.model.Doc;
import com.xz.elasticsearch.service.ElasticsearchService;
import com.xz.elasticsearch.utils.ElasticsearchUtil;
import com.xz.elasticsearch.utils.HbaseUtil;
import com.xz.solr.utils.PageUtil;

@Controller
@RequestMapping("/elasticsearch")
public class ElasticsearchController extends BaseController {

	@Autowired
	private ElasticsearchService elasticsearchService;

	@RequestMapping("/create")
	public String createIndex() throws Exception {
		elasticsearchService.insert("", "");
		return "elasticsearch/create";
	}

	@RequestMapping("/search")
	public String serachArticle(Model model, @RequestParam(value = "keyWords", required = false) String keyWords,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {

		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		try {
			map = ElasticsearchUtil.search(keyWords, "bjsxt", "doc", (pageNum - 1) * pageSize, pageSize);
			count = Integer.parseInt(((Long) map.get("count")).toString());
		} catch (Exception e) {
			logger.error("查询索引错误!{}", e);
			e.printStackTrace();
		}
		PageUtil<Map<String, Object>> page = new PageUtil<Map<String, Object>>(String.valueOf(pageNum),
				String.valueOf(pageSize), count);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> articleList = (List<Map<String, Object>>) map.get("dataList");
		page.setList(articleList);
		model.addAttribute("total", count);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("page", page);
		model.addAttribute("kw", keyWords);
		return "elasticsearch/index";
	}

	/**
	 * 查看文章详细信息
	 * 
	 * @return
	 */
	@RequestMapping("/detailDocById/{id}")
	public String detailArticleById(@PathVariable(value = "id") String id, Model modelMap) throws IOException {
		// 这里用的查询是直接从hbase中查询一条字符串出来做拆分封装，这里要求同学们用protobuffer
		HbaseUtil hbaseUtil = new HbaseUtil();
		Doc Doc = hbaseUtil.get(hbaseUtil.TABLE_NAME, id);
		modelMap.addAttribute("Doc", Doc);
		return "elasticsearch/detail";
	}

}
