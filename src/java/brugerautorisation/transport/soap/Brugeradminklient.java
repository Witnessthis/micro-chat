package brugerautorisation.transport.soap;

import brugerautorisation.Diverse;
import brugerautorisation.data.Bruger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author j
 */
public class Brugeradminklient {
	public static void main(String[] args) throws MalformedURLException {
//		URL url = new URL("http://localhost:9901/brugeradmin?wsdl");
		URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
		QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
		Service service = Service.create(url, qname);
		Brugeradmin ba = service.getPort(Brugeradmin.class);

    //ba.sendGlemtAdgangskodeEmail("jacno", "Dette er en test, husk at skifte kode");
		//ba.ændrAdgangskode("jacno", "kode3stljl", "xxx");

		// ba.sendEmail("jacno", "xxx", "Hurra det virker!", "Jeg er så glad");

                
                Bruger b = ba.hentBruger("s133175", "ThisIsMyPsw");
		System.out.println("Fik bruger = " + b);
		System.out.println("Data: " + Diverse.toString(b));
		// ba.sendEmail("jacno", "xxx", "Hurra det virker!", "Jeg er så glad");

		Object ekstraFelt = ba.getEkstraFelt("s133175", "ThisIsMyPsw", "s123456_testfelt");
		System.out.println("Fik ekstraFelt = " + ekstraFelt);

		ba.setEkstraFelt("s133175", "ThisIsMyPsw", "s123456_testfelt", "Hej fram Jacob"); // Skriv noget andet her
                ba.ændrAdgangskode("s133175", "ThisIsMyPsw", "ThisIsMyPsw");
                
	}
}
