import com.usrmngr.client.models.ADConnector;
import org.junit.Test;
public class ADConnectorTest {
    @Test
    public void testToString()
    {
        ADConnector adConnector = new ADConnector();
        adConnector.setConfigs("192.168.1.2", 389, "DC=lab,DC=net",
                "cn=Administrator,ou=Users,ou=Company,dc=lab,dc=net", "xxxxxxxx");
        String string = adConnector.toString().trim();
        System.out.println(string.equals("server:192.168.1.2\n" +
                "port:389\n" +
                "baseDN:DC=lab,DC=net\n" +
                "bindCN:CN=cn=Administrator,ou=Users,ou=Company,dc=lab,dc=net,DC=lab,DC=net\n" +
                "password:xxxxxxxx"));
    }
}
