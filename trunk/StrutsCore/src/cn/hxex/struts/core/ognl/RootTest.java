package cn.hxex.struts.core.ognl;

import java.math.BigInteger;

import junit.framework.TestCase;
import ognl.DynamicSubscript;
import ognl.Ognl;

public class RootTest extends TestCase {
	private static Root root = new Root();

	public void test() throws Exception {
		// ʹ�ó������ʽ
		assertTrue(Ognl.getValue("true", root).equals(true));
		assertTrue(Ognl.getValue("11h", root).equals(new BigInteger("11")));
		assertTrue(!Ognl.getValue("11h", root).equals(11));

		// �������
		assertTrue(Ognl.getValue("#var=99,#var", root).equals(99));
		// ���÷���
		assertTrue(Ognl.getValue("add(1,2)", root).equals(3));
		// ֱ����Root��map���Ե�����������
		assertTrue(Ognl.getValue("map", root) == root.getMap());
		// ����Root��map���Ե�test����
		assertTrue(Ognl.getValue("map.test", root).equals(root));
		// ������map.test�����ʣ��������±���ʽ����
		assertTrue(Ognl.getValue("map[\"test\"]", root).equals(root));
		// ���������һ���ģ�������ʾ���±����֮����ʵ���ֵ
		assertTrue(Ognl.getValue("map[\"te\"+\"st\"]", root).equals(root));
		/*
		 * ����Map��sizeͨ����ͬ���ʷ�ʽ�õ���ͬ�Ľ��
		 */
		// ͨ��map["size"]�õ���sizeΪkey��Map�еĶ�Ӧֵ
		assertTrue(Ognl.getValue(
				"map[@cn.hxex.struts.core.ognl.Root@SIZE_STRING]", root)
				.equals(root.getMap().get("size")));
		// ͨ��map.size�õ�Map�Ĵ�С
		assertTrue(Ognl.getValue("map.size", root).equals(root.getMap().size()));
		// ͨ���±���������еĶ���
		assertTrue(Ognl.getValue("map.array[0]", root).equals(
				((Integer[]) root.getMap().get("array"))[0]));
		// ͨ���±����List�еĶ���
		assertTrue(Ognl.getValue("map.list[0]", root) == null);

		/**
		 * �õ�����������λ�õ�Ԫ�� ע�⣺��Map����Ҫ��ʽ��ʹ��DynamicSubscript. first����Ϊkey����ȡ��ֵ
		 */
		// �õ���һ��Ԫ��
		assertTrue(Ognl.getValue("map[^]", root).equals(99));
		// �õ��м�Ԫ��
		int[] array = (int[]) root.getMap().get("array");
		assertTrue(Ognl.getValue("map.array[|]", root).equals(
				array[array.length / 2]));
		// �õ����һ��Ԫ��
		assertTrue(Ognl.getValue("map.array[$]", root).equals(
				array[array.length - 1]));
		// �õ�һ���µļ��϶���
		int[] target = (int[]) Ognl.getValue("map.array[*]", root);
		assertTrue(array != target);
		for (int i = 0; i < array.length; i++) {
			assertTrue(array[i] == target[i]);
		}
		// ����������б��α����
		assertTrue(Ognl.getValue("map.array.length", root).equals(array.length));
		/*
		 * �����������ӵĵ�����
		 */
		// �����κα��ʽ�Ľ��Ͷ��ǻ��ڵ�ǰ�����
		assertTrue(Ognl.getValue("map.(array[2]+size())", root).equals(8));
		// map.(#this),this�Ƕ���������ã�����ע���ڱ�����ǰ��#���ţ����������
		// ������ʽ����ȫ�ֵ�
		assertTrue(Ognl.getValue("map.(#this)", root) == root.getMap());
		// ����map�ĵ�һ��Ԫ���Ƿ�Ϊ�գ����Ϊ���򷵻�empth�����򷵻ظö���
		assertTrue(Ognl.getValue("map.[^].(#this==null ? 'empty' : #this)",
				root) == root.getMap().get(DynamicSubscript.first));
		assertTrue(Ognl.getValue("map.[$].(#this==null ? 'empty' : #this)",
				root).equals("empty"));
	}
}
