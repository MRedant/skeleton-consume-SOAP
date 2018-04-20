package be.diher81.soap;

import javax.xml.soap.*;

public class SOAPClientSAAJ {

    // SAAJ - SOAP Client Testing
    public static void main(String args[]) {
        /*
            The example below requests from the Web Service at:
             http://api.frenoy.net/TabTAPI


            To call other WS, change the parameters below, which are:
             - the SOAP Endpoint URL (that is, where the service is responding from)
             - the SOAP Action

            Also change the contents of the method createSoapEnvelope() in this class. It constructs
             the inner part of the SOAP envelope that is actually sent.
         */
        String soapEndpointUrl = "http://api.vttl.be/0.7/index.php?s=vttl";
        String soapAction = "Test";

        callSoapWebService(soapEndpointUrl, soapAction);
    }

    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "tab";
        String myNamespaceURI = "http://api.frenoy.net/TabTAPI";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            /*
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tab="http://api.frenoy.net/TabTAPI">
               <soapenv:Header/>
               <soapenv:Body>
                  <tab:TestRequest>
                     <!--Optional:-->
                     <tab:Credentials>
                        <!--You may enter the following 2 items in any order-->
                        <tab:Account>myAccountName</tab:Account>
                        <tab:Password>myPassword</tab:Password>
                     </tab:Credentials>
                  </tab:TestRequest>
               </soapenv:Body>
            </soapenv:Envelope>
            */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElemTest = soapBody.addChildElement("TestRequest", myNamespace);
        SOAPElement soapBodyElemCred = soapBodyElemTest.addChildElement("Credentials", myNamespace);
        SOAPElement soapBodyElemAcc = soapBodyElemCred.addChildElement("Account", myNamespace);
        SOAPElement soapBodyElemPass = soapBodyElemCred.addChildElement("Password", myNamespace);
        soapBodyElemAcc.addTextNode("myAccountName");
        soapBodyElemPass.addTextNode("myPassword");
    }

    private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }
}
