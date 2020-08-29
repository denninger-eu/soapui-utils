Scenario: case
	* def Context = Java.type('eu.k5.dread.soapui.SoapuiContext')
	* def ctx = new Context()

	* def createResourceRequest = read("createResourceRequest.txt")
	* def createResource = ctx.requestStep("createResource").url("${#TestCase#baseUrl}/resource").request(createResourceRequest)

	# createResource
	Given url createResource.url()
	  And request createResource.request()
	  And header headerP = "headerV"
	  And header Accept = "application/json"
	  And header Content-Type = "application/json"
	When  method POST
	Then  print createResource.response(response)
	  And status 200
	  And match createResource.assertJsonExists("$.id") == true

