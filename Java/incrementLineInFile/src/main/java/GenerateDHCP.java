import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacob on 2018-03-17 (YYYY-MM-DD).
 */
public class GenerateDHCP {

    public static void main(String[] args) throws IOException {

        generateDHCPFile(203, 3);
    }

    private static void generateDHCPFile(int amount, int startinterfaceNumber) throws IOException {
        List<String> result = new ArrayList<>();

        for (int i = 1; i < (amount + 1); i++) {
            List<String> vlan = generateDHCP(i, startinterfaceNumber);
            result.addAll(vlan);
            startinterfaceNumber++;
        }

        File file = new File("/home/jacob/Andet/Temp/obelnet/new-DHCPDSubList.xml");
        FileUtils.writeLines(file, result);
    }

    private static List<String> generateDHCP(int value, int interfaceNumber) {
        List<String> result = new ArrayList<>();

        result.add("\t<opt" + interfaceNumber + ">");
        result.add("\t\t<range>");
        result.add("\t\t\t<from>10.10." + value + ".1</from>");
        result.add("\t\t\t<to>10.10." + value + ".254</to>");
        result.add("\t\t</range>");
        result.add("\t\t<enable></enable>\n" +
                "\t\t<failover_peerip></failover_peerip>\n" +
                "\t\t<defaultleasetime></defaultleasetime>\n" +
                "\t\t<maxleasetime></maxleasetime>\n" +
                "\t\t<netmask></netmask>\n" +
                "\t\t<gateway></gateway>\n" +
                "\t\t<domain></domain>\n" +
                "\t\t<domainsearchlist></domainsearchlist>\n" +
                "\t\t<ddnsdomain></ddnsdomain>\n" +
                "\t\t<ddnsdomainprimary></ddnsdomainprimary>\n" +
                "\t\t<ddnsdomainkeyname></ddnsdomainkeyname>\n" +
                "\t\t<ddnsdomainkeyalgorithm>hmac-md5</ddnsdomainkeyalgorithm>\n" +
                "\t\t<ddnsdomainkey></ddnsdomainkey>\n" +
                "\t\t<mac_allow></mac_allow>\n" +
                "\t\t<mac_deny></mac_deny>\n" +
                "\t\t<ddnsclientupdates>allow</ddnsclientupdates>\n" +
                "\t\t<tftp></tftp>\n" +
                "\t\t<ldap></ldap>\n" +
                "\t\t<nextserver></nextserver>\n" +
                "\t\t<filename></filename>\n" +
                "\t\t<filename32></filename32>\n" +
                "\t\t<filename64></filename64>\n" +
                "\t\t<rootpath></rootpath>\n" +
                "\t\t<numberoptions></numberoptions>\n" +
                "\t\t<dhcpleaseinlocaltime>yes</dhcpleaseinlocaltime>");
        result.add("\t</opt" + interfaceNumber + ">");

        return result;
    }
}
