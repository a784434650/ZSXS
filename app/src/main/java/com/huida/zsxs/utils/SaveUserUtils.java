package com.huida.zsxs.utils;

import android.content.Context;

import com.huida.zsxs.bean.User;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

//利用流将用户存取到本地
public class SaveUserUtils {
	private static final String FILENAME = "userinfo.json";

	public static void saveUserList(Context context, ArrayList<User> users) throws Exception{
		Writer writer = null;
		OutputStream out = null;
		JSONArray array = new JSONArray();
		for (User user : users) {
			array.put(user.toJson());
		}
		try {
			out = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			if (writer != null)
				writer.close();
		}
	}
	//将本地保存的用户json串装换为数组传回去
	public static ArrayList<User> getUserList(Context context){
		FileInputStream in = null;
		ArrayList<User> users = new ArrayList<User>();
		try {
			in = context.openFileInput(FILENAME);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			JSONArray jsonArray = new JSONArray();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
				}
			jsonArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			for (int i = 0; i < jsonArray.length(); i++) {
				User user = new User(jsonArray.getJSONObject(i));
				users.add(user);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
}
