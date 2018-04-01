import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacob on 2018-03-18 (YYYY-MM-DD).
 */
public class GenerateFirewallRules {
//    private static long uniqueValue = 1521329230;

    public static void main(String[] args) throws IOException {
        generate(203, 3);
    }

    private static void generate(int amount, int startInterfaceNumber) throws IOException {
        List<String> result = new ArrayList<>();
        Stuff stuff = new Stuff();

        for (int i = 1; i < (amount + 1); i++) {
            String vlan = generateRule(i, startInterfaceNumber, stuff);
            result.add(vlan);
            startInterfaceNumber++;
        }

        File file = new File("/home/jacob/Andet/Temp/obelnet/new-FilterSubList.xml");
        FileUtils.writeLines(file, result);
    }

    private static String generateRule(int i, int interfaceNumber, Stuff stuff) {

        long unique1 = stuff.getUniqueValue();
        long unique2 = stuff.getUniqueValue();
        long unique3 = stuff.getUniqueValue();

        String s = "\t<rule>\n" +
                "\t\t<id></id>\n" +
                "\t\t<tracker>" + unique1 + "</tracker>\n" +
                "\t\t<type>pass</type>\n" +
                "\t\t<interface>opt" + interfaceNumber + "</interface>\n" +
                "\t\t<ipprotocol>inet46</ipprotocol>\n" +
                "\t\t<tag></tag>\n" +
                "\t\t<tagged></tagged>\n" +
                "\t\t<max></max>\n" +
                "\t\t<max-src-nodes></max-src-nodes>\n" +
                "\t\t<max-src-conn></max-src-conn>\n" +
                "\t\t<max-src-states></max-src-states>\n" +
                "\t\t<statetimeout></statetimeout>\n" +
                "\t\t<statetype><![CDATA[keep state]]></statetype>\n" +
                "\t\t<os></os>\n" +
                "\t\t<source>\n" +
                "\t\t\t<network>opt" + interfaceNumber + "</network>\n" +
                "\t\t</source>\n" +
                "\t\t<destination>\n" +
                "\t\t\t<network>opt" + interfaceNumber + "</network>\n" +
                "\t\t</destination>\n" +
                "\t\t<descr><![CDATA[Allow Self]]></descr>\n" +
                "\t\t<created>\n" +
                "\t\t\t<time>" + unique1 + "</time>\n" +
                "\t\t\t<username>admin@10.10.127.3</username>\n" +
                "\t\t</created>\n" +
                "\t</rule>\n" +
                "\t<rule>\n" +
                "\t\t<id></id>\n" +
                "\t\t<tracker>" + unique2 + "</tracker>\n" +
                "\t\t<type>pass</type>\n" +
                "\t\t<interface>opt" + interfaceNumber + "</interface>\n" +
                "\t\t<ipprotocol>inet46</ipprotocol>\n" +
                "\t\t<tag></tag>\n" +
                "\t\t<tagged></tagged>\n" +
                "\t\t<max></max>\n" +
                "\t\t<max-src-nodes></max-src-nodes>\n" +
                "\t\t<max-src-conn></max-src-conn>\n" +
                "\t\t<max-src-states></max-src-states>\n" +
                "\t\t<statetimeout></statetimeout>\n" +
                "\t\t<statetype><![CDATA[keep state]]></statetype>\n" +
                "\t\t<os></os>\n" +
                "\t\t<source>\n" +
                "\t\t\t<network>opt" + interfaceNumber + "</network>\n" +
                "\t\t</source>\n" +
                "\t\t<destination>\n" +
                "\t\t\t<address>ApartmentsAllowedDefault</address>\n" +
                "\t\t</destination>\n" +
                "\t\t<descr><![CDATA[Allow internal]]></descr>\n" +
                "\t\t<created>\n" +
                "\t\t\t<time>" + unique2 + "</time>\n" +
                "\t\t\t<username>admin@10.10.127.3</username>\n" +
                "\t\t</created>\n" +
                "\t</rule>\n" +
                "\t<rule>\n" +
                "\t\t<id></id>\n" +
                "\t\t<tracker>" + unique3 + "</tracker>\n" +
                "\t\t<type>pass</type>\n" +
                "\t\t<interface>opt" + interfaceNumber + "</interface>\n" +
                "\t\t<ipprotocol>inet46</ipprotocol>\n" +
                "\t\t<tag></tag>\n" +
                "\t\t<tagged></tagged>\n" +
                "\t\t<max></max>\n" +
                "\t\t<max-src-nodes></max-src-nodes>\n" +
                "\t\t<max-src-conn></max-src-conn>\n" +
                "\t\t<max-src-states></max-src-states>\n" +
                "\t\t<statetimeout></statetimeout>\n" +
                "\t\t<statetype><![CDATA[keep state]]></statetype>\n" +
                "\t\t<os></os>\n" +
                "\t\t<source>\n" +
                "\t\t\t<network>opt" + interfaceNumber + "</network>\n" +
                "\t\t</source>\n" +
                "\t\t<destination>\n" +
                "\t\t\t<address>ApartmentsBlockedDefault</address>\n" +
                "\t\t\t<not></not>\n" +
                "\t\t</destination>\n" +
                "\t\t<descr><![CDATA[Allow external]]></descr>\n" +
                "\t\t<created>\n" +
                "\t\t\t<time>" + unique3 + "</time>\n" +
                "\t\t\t<username>admin@10.10.127.3</username>\n" +
                "\t\t</created>\n" +
                "\t</rule>";

        return s;
    }

    static class Stuff {
        private long uniqueValue = 1522439956;

        public long getUniqueValue() {
//        long currentValue = uniqueValue;
//        uniqueValue = currentValue++;
            return uniqueValue++;
        }
    }
}
