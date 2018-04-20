Starting point to create Maven applications consuming SOAP services.

The example requests from the Web Service at: http://api.frenoy.net/TabTAPI


 To call other WS, change the parameters below, which are:
 - the SOAP Endpoint URL (that is, where the service is responding from)
 - the SOAP Action

Also change the contents of the method createSoapEnvelope() in this class. It constructs
 the inner part of the SOAP envelope that is actually sent.