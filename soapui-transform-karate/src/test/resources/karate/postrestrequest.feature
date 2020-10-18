Feature: case

Background:
	* def Context = Java.type('eu.k5.dread.soapui.SoapuiContext')
	* def ctx = new Context()

Scenario: case
	* def createResourceRequest = read("createResourceRequest.txt")
	* def createResource = ctx.requestStep("createResource").url("${#TestCase#baseUrl}/resource").request(createResourceRequest)

	# createResource
	# POST /resource
	Given url createResource.url()
	  And request createResource.request()
	  And param queryParam = ctx.expand("${#Project#value}")
	  And header headerP = "headerV"
	  And header Accept = "application/json"
	  And header Content-Type = "application/json"
	When  method POST
	Then  if (true) createResource.response(response).status(responseStatus)
	  And status 200
	  And if (true) createResource.assertInvalidStatus("200")
	  And if (true) createResource.assertJsonPathExists("$.id","true")

