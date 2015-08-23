package cn.hxex.struts.core.ognl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ognl.DynamicSubscript;

public class Root {

	public static final String SIZE_STRING = "size";
	private int[] array = { 1, 2, 3, 4 };
	private Map map = new HashMap(23);
	private List list = Arrays.asList(new Object[] { null, this, getArray() });

	public Root() {
		getMap().put("test", this);
		getMap().put("array", getArray());
		getMap().put("list", getList());
		getMap().put("size", new Integer(5000));
		getMap().put(DynamicSubscript.first, new Integer(99));
	}

	public int[] getArray() {
		return array;
	}

	public void setArray(int[] array) {
		this.array = array;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public int add(int arg1, int arg2) {
		return arg1 + arg2;
	}

}
