Scenario: case
	* def Context = Java.type('eu.k5.dread.soapui.SoapuiContext')
	* def ctx = new Context()

	* def Properties = ctx.propertiesStep("Properties")
	* if (true)  Properties.setProperty("key","keyValue")
	* if (true)  Properties.setProperty("date","${=java.time.LocalDateTime.now()}")
	* if (true)  Properties.setProperty("dynamicScript","\"test\"")

