Feature: case

Background: 
	* def Context = Java.type('eu.k5.dread.soapui.SoapuiContext')
	* def ctx = new Context()

Scenario: case
	* def get_Resource = ctx.requestStep("get Resource").url("${#TestCase#baseUrl}/resource/${template}").property("template","${#Project#projectProperty}")
	
	# get Resource
	# GET /resource/${template}
	Given url get_Resource.url()
	  And header Accept = "application/json"
	When  method GET
	Then  if (true) get_Resource.response(response).status(responseStatus)
	  And if (true) get_Resource.assertJsonPathExists("$.id","true")
	
