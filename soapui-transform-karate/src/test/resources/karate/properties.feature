Scenario: case
	* def Context = Java.type('eu.k5.dread.soapui.SoapuiContext')
	* def ctx = new Context()

	* def Properties = ctx.propertiesStep("Properties")
	* print Properties.setProperty("key","keyValue")
	* print Properties.setProperty("date","${=java.time.LocalDateTime.now()}")
	* print Properties.setProperty("dynamicScript","\"test\"")

