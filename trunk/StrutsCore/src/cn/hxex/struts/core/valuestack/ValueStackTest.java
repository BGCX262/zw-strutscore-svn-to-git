package cn.hxex.struts.core.valuestack;

import junit.framework.TestCase;
import com.opensymphony.xwork2.util.OgnlValueStack;
import com.opensymphony.xwork2.util.ValueStack;

public class ValueStackTest extends TestCase {
	private ValueStack valueStack;

	protected void setUp() throws Exception {
		valueStack = new OgnlValueStack();
		// 创建一个新的Employee对象，并圧入ValueStack中
		valueStack.push(new Employee());
	}

	public void test() {
		// 设置Employee的name属性值为Tom
		valueStack.setValue("name", "Tom");
		assertTrue("Tom".equals(valueStack.findValue("name")));
		// 创建Company对象
		Company company = new Company();
		// 设置Employee的company属性为新创建的Company对象
		valueStack.setValue("company", company);
		assertTrue(company == valueStack.findValue("company"));
		// 将Company对象圧入ValueStack中
		valueStack.push(company);
		// 设置Company的name属性为IBM
		valueStack.setValue("name", "IBM");
		assertTrue("IBM".equals(valueStack.findValue("name")));
		assertTrue("IBM".equals(valueStack.findValue("Company.name")));
		valueStack.pop();
		assertTrue("Tom".equals(valueStack.findValue("name")));
		assertTrue("IBM".equals(valueStack.findValue("Company.name")));
	}
}
