Scenario: case
	* def Context = Java.type('eu.k5.dread.soapui.SoapuiContext')
	* def ctx = new Context()

	* def get_Resource = ctx.requestStep("get Resource").url("${#TestCase#baseUrl}/resource/${#Project#projectProperty}")

	# get Resource
	Given url get_Resource.url()
	  And header Accept = "application/json"
	When  method GET
	Then  print get_Resource.response(response)
	  And match get_Resource.assertJsonExists("$.id") == true