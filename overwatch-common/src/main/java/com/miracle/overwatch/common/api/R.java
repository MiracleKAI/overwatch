package com.miracle.overwatch.common.api;


import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * @author qiukai
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", ResultCode.SUCCESS.getCode());
		put("msg", ResultCode.SUCCESS.getMessage());
	}

	public static R error() {

		return error(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
	}

	public static R error(String msg) {
		return error(ResultCode.FAILED.getCode(), msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
