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

        generateDHCPFile();
    }

    private static void generateDHCPFile() throws IOException {
        List<String> result = new ArrayList<>();

        result.add("<dhcpd>");

        int total = 203;
        for (int i = 1; i < (total + 1); i++) {
            List<String> vlan = generateDHCP(i);
            result.addAll(vlan);
        }

        result.add("</dhcpd>");


        File file = new File("/home/jacob/Andet/Temp/testxml/backup/FraNyServerSetup/generated/dhcp.xml");
        FileUtils.writeLines(file, result);
    }

    private static List<String> generateDHCP(int i) {
        List<String> result = new ArrayList<>();
        String value = String.format("%03d", i);

        result.add("\t<opt" + (i + 1) + "> ");
        result.add("\t\t<range>");
        result.add("\t\t\t<from>10.10." + i + ".2</from>");
        result.add("\t\t\t<to>10.10." + i + ".254</to>");
        result.add("\t\t</range>");
        result.add(
                "\t\t<enable></enable>\n" +
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
                "\t\t<ddnsdomainkey></ddnsdomainkey>\n" +
                "\t\t<mac_allow></mac_allow>\n" +
                "\t\t<mac_deny></mac_deny>\n" +
                "\t\t<tftp></tftp>\n" +
                "\t\t<ldap></ldap>\n" +
                "\t\t<nextserver></nextserver>\n" +
                "\t\t<filename></filename>\n" +
                "\t\t<filename32></filename32>\n" +
                "\t\t<filename64></filename64>\n" +
                "\t\t<rootpath></rootpath>\n" +
                "\t\t<numberoptions></numberoptions>\n" +
                "\t\t<dhcpleaseinlocaltime>yes</dhcpleaseinlocaltime>");
        result.add("\t</opt" + (i + 1) + "> ");

        return result;
    }

}
