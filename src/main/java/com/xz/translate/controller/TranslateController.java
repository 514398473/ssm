package com.xz.translate.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xz.translate.utils.TransApi;

@Controller
@RequestMapping("/translate")
public class TranslateController {

	@RequestMapping("/goTranslate")
	public String goTranslate() throws Exception {
		return "translate/translate";
	}

	@ResponseBody
	@RequestMapping("/translate")
	public String translate(String info) {
		if (StringUtils.isNotBlank(info)) {
			// 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
			// SECURITY_KEY
			TransApi api = new TransApi("20170819000074866", "6S9xaYtWQ4OYXICsoFBJ");
			JSONObject parseObject = JSONObject.parseObject(api.getTransResult(info, "auto", "en"));
			JSONObject jsonObject = ((JSONArray) parseObject.get("trans_result")).getJSONObject(0);
			return jsonObject.getString("dst");
		}
		return null;
	}
}