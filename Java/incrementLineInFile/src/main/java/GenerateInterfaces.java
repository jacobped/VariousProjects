import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacob on 2018-03-30 (YYYY-MM-DD).
 */
public class GenerateInterfaces {

    public static void main(String[] args) throws IOException {

        generateInterfaces(203, 3);
    }

    private static void generateInterfaces(int amount, int startinterfaceNumber) throws IOException {
        List<String> result = new ArrayList<>();

        for (int i = 1; i < (amount + 1); i++) {
            List<String> opt = generateInterface(i, startinterfaceNumber);
            result.addAll(opt);
            startinterfaceNumber++;
        }


        File file = new File("/home/jacob/Andet/Temp/obelnet/new-InterfacesSubList.xml");
        FileUtils.writeLines(file, result);
    }

    private static List<String> generateInterface(int vlanNumber, int value) {
        List<String> result = new ArrayList<>();
        String vlanValue = String.format("%03d", vlanNumber);

        result.add("\t<opt" + value + ">");
        result.add("\t\t<descr><![CDATA[APT" + vlanValue + "]]></descr>");
        result.add("\t\t<if>vmx1.vlan1" + vlanValue + "</if>");
        result.add("\t\t<enable></enable>");
        result.add("\t\t<ipaddr>10.10." + vlanNumber + ".1</ipaddr>");
        result.add("\t\t<subnet>24</subnet>");
        result.add("\t\t<spoofmac></spoofmac>");
        result.add("\t</opt" + value + ">");

        return result;
    }
}
