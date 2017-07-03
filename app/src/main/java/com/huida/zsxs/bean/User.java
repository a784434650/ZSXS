package com.huida.zsxs.bean;

import org.json.JSONObject;

public class User {
	private String mId;
	private String mPwd;
	private static final String JSON_ID = "user_id";
	private static final String JSON_PWD = "user_pwd";

	public User(String mId, String mPwd) {
		this.mId = mId;
		this.mPwd = mPwd;
	}

	public User(JSONObject json) throws Exception{
		if(json.has(JSON_ID)){
			mId=json.getString(JSON_ID);
			mPwd=json.getString(JSON_PWD);
		}
	}

	public JSONObject toJson() throws Exception{

		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId);
		json.put(JSON_PWD, mPwd);
		return json;
	}

	public String getmId() {
		return mId;
	}

	public String getmPwd() {
		return mPwd;
	}

}
