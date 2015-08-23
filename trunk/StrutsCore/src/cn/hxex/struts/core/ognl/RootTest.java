package cn.hxex.struts.core.ognl;

import java.math.BigInteger;

import junit.framework.TestCase;
import ognl.DynamicSubscript;
import ognl.Ognl;

public class RootTest extends TestCase {
	private static Root root = new Root();

	public void test() throws Exception {
		// 使用常量表达式
		assertTrue(Ognl.getValue("true", root).equals(true));
		assertTrue(Ognl.getValue("11h", root).equals(new BigInteger("11")));
		assertTrue(!Ognl.getValue("11h", root).equals(11));

		// 定义变量
		assertTrue(Ognl.getValue("#var=99,#var", root).equals(99));
		// 调用方法
		assertTrue(Ognl.getValue("add(1,2)", root).equals(3));
		// 直接用Root中map属性的名字来访问
		assertTrue(Ognl.getValue("map", root) == root.getMap());
		// 访问Root中map属性的test属性
		assertTrue(Ognl.getValue("map.test", root).equals(root));
		// 上面用map.test来访问，现在用下标形式访问
		assertTrue(Ognl.getValue("map[\"test\"]", root).equals(root));
		// 跟上面的是一样的，这里演示了下标计算之后访问到的值
		assertTrue(Ognl.getValue("map[\"te\"+\"st\"]", root).equals(root));
		/*
		 * 对于Map的size通过不同访问方式得到不同的结果
		 */
		// 通过map["size"]得到以size为key在Map中的对应值
		assertTrue(Ognl.getValue(
				"map[@cn.hxex.struts.core.ognl.Root@SIZE_STRING]", root)
				.equals(root.getMap().get("size")));
		// 通过map.size得到Map的大小
		assertTrue(Ognl.getValue("map.size", root).equals(root.getMap().size()));
		// 通过下标访问数组中的对象
		assertTrue(Ognl.getValue("map.array[0]", root).equals(
				((Integer[]) root.getMap().get("array"))[0]));
		// 通过下标访问List中的对象
		assertTrue(Ognl.getValue("map.list[0]", root) == null);

		/**
		 * 得到集合中特殊位置的元素 注意：在Map中需要显式地使用DynamicSubscript. first等做为key才能取到值
		 */
		// 得到第一个元素
		assertTrue(Ognl.getValue("map[^]", root).equals(99));
		// 得到中间元素
		int[] array = (int[]) root.getMap().get("array");
		assertTrue(Ognl.getValue("map.array[|]", root).equals(
				array[array.length / 2]));
		// 得到最后一个元素
		assertTrue(Ognl.getValue("map.array[$]", root).equals(
				array[array.length - 1]));
		// 得到一个新的集合对象
		int[] target = (int[]) Ognl.getValue("map.array[*]", root);
		assertTrue(array != target);
		for (int i = 0; i < array.length; i++) {
			assertTrue(array[i] == target[i]);
		}
		// 测试数组或列表的伪属性
		assertTrue(Ognl.getValue("map.array.length", root).equals(array.length));
		/*
		 * 测试两个复杂的导航链
		 */
		// 对于任何表达式的解释都是基于当前对象的
		assertTrue(Ognl.getValue("map.(array[2]+size())", root).equals(8));
		// map.(#this),this是对自身的引用，另外注意在变量名前加#符号，这个变量在
		// 这个表达式里是全局的
		assertTrue(Ognl.getValue("map.(#this)", root) == root.getMap());
		// 测试map的第一个元素是否为空，如果为空则返回empth，否则返回该对象
		assertTrue(Ognl.getValue("map.[^].(#this==null ? 'empty' : #this)",
				root) == root.getMap().get(DynamicSubscript.first));
		assertTrue(Ognl.getValue("map.[$].(#this==null ? 'empty' : #this)",
				root).equals("empty"));
	}
}
