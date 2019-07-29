import javax.xml.soap.*;
import java.net.URL;
import java.util.Random;

public class Application {

    private URL url = null;
    private SOAPConnectionFactory soapConnectionFactory = null;
    private SOAPConnection soapConnection = null;
    private SOAPMessage soapRequest = null;
    private SOAPMessage soapResponse = null;

    public static void main(String[] args){


        try {
            while(true) {
                URL url = new URL("http://localhost:8083/flowershop/ws/FlowersStockWebService?wsdl");
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                SOAPConnection soapConnection = soapConnectionFactory.createConnection();
                SOAPMessage soapRequest = createSoapRequest();
                SOAPMessage soapResponse = soapConnection.call(soapRequest, url);
                soapConnection.close();
                Thread.sleep(10*1000);//**10 минут
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /** Создание SOAP запроса **/
    private static SOAPMessage createSoapRequest() throws Exception{
        Random rand = new Random();
        Integer number = rand.nextInt(20)+10;
        MessageFactory messageFactory = MessageFactory.newInstance(); //**фабрика для создания экземпляра SOAPMessage
        SOAPMessage soapMessage = messageFactory.createMessage(); //**Документ XML или сообщение MIME
        SOAPPart soapPart = soapMessage.getSOAPPart();  //**Контейнер для SOAP-определенной части SOAPMessage
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();  //**Контейнер для частей SOAPHeader и SOAPBody
        soapEnvelope.addNamespaceDeclaration("web", "http://webservice.fe/"); //**Декларация веб-сервиса
        SOAPBody soapBody = soapEnvelope.getBody(); //**Объект для тела SOAP для сообщения SOAP
        SOAPElement soapElement = soapBody.addChildElement("increaseFlowersStockSize", "web"); //**Добавление в тело Вызываемый метод
        SOAPElement element1 = soapElement.addChildElement("request"); //**создание параметра с именем request
        element1.addTextNode(number.toString()); //**Добавление значения в element1
        soapMessage.saveChanges(); //**
        return soapMessage;
    }
}
