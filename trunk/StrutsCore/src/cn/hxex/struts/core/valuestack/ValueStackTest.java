package cn.hxex.struts.core.valuestack;

import junit.framework.TestCase;
import com.opensymphony.xwork2.util.OgnlValueStack;
import com.opensymphony.xwork2.util.ValueStack;

public class ValueStackTest extends TestCase {
	private ValueStack valueStack;

	protected void setUp() throws Exception {
		valueStack = new OgnlValueStack();
		// ����һ���µ�Employee���󣬲��R��ValueStack��
		valueStack.push(new Employee());
	}

	public void test() {
		// ����Employee��name����ֵΪTom
		valueStack.setValue("name", "Tom");
		assertTrue("Tom".equals(valueStack.findValue("name")));
		// ����Company����
		Company company = new Company();
		// ����Employee��company����Ϊ�´�����Company����
		valueStack.setValue("company", company);
		assertTrue(company == valueStack.findValue("company"));
		// ��Company����R��ValueStack��
		valueStack.push(company);
		// ����Company��name����ΪIBM
		valueStack.setValue("name", "IBM");
		assertTrue("IBM".equals(valueStack.findValue("name")));
		assertTrue("IBM".equals(valueStack.findValue("Company.name")));
		valueStack.pop();
		assertTrue("Tom".equals(valueStack.findValue("name")));
		assertTrue("IBM".equals(valueStack.findValue("Company.name")));
	}
}
