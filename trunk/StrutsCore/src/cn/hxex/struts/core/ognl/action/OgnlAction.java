package cn.hxex.struts.core.ognl.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hxex.struts.core.ognl.bean.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OgnlAction extends ActionSupport {
	public String execute() {
		Map sessionMap = ActionContext.getContext().getSession();
		sessionMap.put("userName", "David");

		List<Student> students = new ArrayList<Student>();
		students.add(new Student("Mike", 20));
		students.add(new Student("Tom", 18));
		students.add(new Student("Jack", 16));
		students.add(new Student("White", 15));
		setStudents(students);

		return SUCCESS;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	private List<Student> students;

}
