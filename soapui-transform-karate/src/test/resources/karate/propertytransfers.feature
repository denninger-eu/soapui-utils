Scenario: case
	* def Context = Java.type('eu.k5.dread.soapui.SoapuiContext')
	* def ctx = new Context()

	# transfer
	* print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#Project#projectProperty")
	* print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#TestSuite#suiteProperty")
	* print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#TestCase#caseProperty")
	* print ctx.transfer("#Properties#dynamicScript").to("#Groovy2#script")