import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacob on 2018-03-17 (YYYY-MM-DD).
 */
public class GenerateVlans {


    public static void main(String[] args) throws IOException {

        generateVlans();
    }

    private static void generateVlans() throws IOException {
        List<String> result = new ArrayList<>();

        result.add("<vlans>");

        int total = 203;
        for (int i = 1; i < (total + 1); i++) {
            List<String> vlan = generateVlan(i);
            result.addAll(vlan);
        }

        result.add("</vlans>");


        File file = new File("/home/jacob/Andet/Temp/obelnet/new-vlan.xml");
        FileUtils.writeLines(file, result);
    }

    private static List<String> generateVlan(int i) {
        List<String> result = new ArrayList<>();
        String value = String.format("%03d", i);


        result.add("\t<vlan>");

        result.add("\t\t<if>vmx1</if>");
        result.add("\t\t<tag>1" + value + "</tag>");
        result.add("\t\t<pcp></pcp>");
        result.add("\t\t<descr><![CDATA[APT" + value + "]]></descr>");
        result.add("\t\t<vlanif>vmx1.vlan1" + value + "</vlanif>");

        result.add("\t</vlan>");

        return result;
    }

}
