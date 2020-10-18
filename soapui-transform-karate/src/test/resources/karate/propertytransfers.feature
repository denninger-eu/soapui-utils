Feature: case

Background:
	* def Context = Java.type('eu.k5.dread.soapui.SoapuiContext')
	* def ctx = new Context()

Scenario: case
	# transfer
	* if (true) ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#Project#projectProperty")
	* if (true) ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#TestSuite#suiteProperty")
	* if (true) ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#TestCase#caseProperty")
	* if (true) ctx.transfer("#Properties#dynamicScript").to("#Groovy2#script")

