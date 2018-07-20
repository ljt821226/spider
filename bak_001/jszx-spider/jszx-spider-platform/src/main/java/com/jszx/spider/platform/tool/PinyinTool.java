package com.jszx.spider.platform.tool;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinTool {

	private static final Logger logger = LoggerFactory.getLogger(PinyinTool.class);

	private static Map<String, String> numMap = new HashMap<String, String>();

	static {
		numMap.put("0", "L");
		numMap.put("1", "Y");
		numMap.put("2", "E");
		numMap.put("3", "S");
		numMap.put("4", "S");
		numMap.put("5", "W");
		numMap.put("6", "L");
		numMap.put("7", "Q");
		numMap.put("8", "B");
		numMap.put("9", "J");
	}
	// 将中文转换为英文

	public static String[] getEname(char str) {
		HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
		pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

		try {
			return PinyinHelper.toHanyuPinyinStringArray(str, pyFormat);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	// 判断字符串是否是英文字母
	public static boolean isEnglish(String charaString) {
		return charaString.matches("^[a-zA-Z]*");
	}

	// 姓的第一个字母需要为大写
	public static String getUpEname(String name) {
		if (name == null || "".equals(name)) {
			return "Q";
		}
		if (numMap.containsKey(name.substring(0, 1))) {
			return numMap.get(name.substring(0, 1));
		}

		if (isEnglish(name.substring(0, 1))) {
			return name.substring(0, 1).toUpperCase();
		}

		char[] strs = name.toCharArray();
		String[] newname = getEname(strs[0]);
		if (newname != null) {
			return newname[0].substring(0, 1).toUpperCase();
		}

		return "Q";
	}

	/**
	 * 将汉字转换为全拼
	 * 
	 * @param src
	 * @return
	 */
	public static String getPingYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

		t3.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		/*
		 * 声调显示 HanyuPinyinToneType.WITH_TONE_NUMBER 用数字表示声调，例如：liu2
		 * HanyuPinyinToneType.WITHOUT_TONE 无声调表示，例如：liu
		 * HanyuPinyinToneType.WITH_TONE_MARK 用声调符号表示，例如：liú
		 */
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		/*
		 * 拼音中ü处理 HanyuPinyinVCharType.WITH_U_AND_COLON 以U和一个冒号表示该拼音，例如：lu:
		 * HanyuPinyinVCharType.WITH_V 以V表示该字符，例如：lv
		 * HanyuPinyinVCharType.WITH_U_UNICODE 以ü表示
		 */
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);

		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else
					t4 += java.lang.Character.toString(t1[i]);
			}
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			logger.error(e.getMessage());
		}
		return t4;
	}

}