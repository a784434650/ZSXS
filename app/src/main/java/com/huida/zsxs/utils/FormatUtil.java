package com.huida.zsxs.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;



public class FormatUtil {
	//正则表达式（合法的电话号码）
	public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	//正则表达式（合法的密码格式）
	public static boolean isPwdLegal(String str) throws PatternSyntaxException {
		String pwdExp ="(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{4,16}$";
		Pattern pattern = Pattern.compile(pwdExp);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	//将搜索到的关键字变成蓝色
	public static SpannableStringBuilder getSearchIndex(String title,String searchStr) {

		int index = (title.toLowerCase()).indexOf(searchStr.toLowerCase());
		SpannableStringBuilder builder = new SpannableStringBuilder(title);
		if(index!=-1){

			ForegroundColorSpan span = new ForegroundColorSpan(0xff759dda);
			builder.setSpan(span,index,index+searchStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		}
		return builder;
	}

}
