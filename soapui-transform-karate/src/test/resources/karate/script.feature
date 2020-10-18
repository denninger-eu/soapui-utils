Feature: case

Background:
	* def Context = Java.type('eu.k5.dread.soapui.SoapuiContext')
	* def ctx = new Context()

Scenario: case
	# Script Groovy2
	* def Groovy2Script = read("Groovy2Script.groovy")
	* def Groovy2 = ctx.groovyScript("Groovy2").script(Groovy2Script)


	# Script Groovy2
	* def t1 = Groovy2.execute()
	* print t1

