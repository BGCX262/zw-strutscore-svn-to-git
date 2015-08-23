<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Ognl</title>
</head>
<body>
	<h2>Access Action Context:</h2>
	session.userName:
	<s:property value="#session.userName" />
	<h2>Projection And Selection Operators:</h2>
	Mike age is:
	<s:property value="students.{?#this.name=='Mike'}.{age}[0]" />
	<br /> Students that larger than 17:
	<s:iterator value="students.{?#this.age > 17}">
		<li><s:property value="name" /> - <s:property value="age" /></li>
	</s:iterator>
	<h2>Construction Map:</h2>
	<s:set name="foobar" value="#{'foo':'foo value','bar':'bar value'}" />
	The value of key "foo" is
	<s:property value="#foobar['foo']" />
</body>
</html>