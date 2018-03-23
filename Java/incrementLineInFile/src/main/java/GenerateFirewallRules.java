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
        generate();
    }

    private static void generate() throws IOException {
        List<String> result = new ArrayList<>();
        Stuff stuff = new Stuff();

//        result.add("<dhcpd>");

        int total = 203;
        for (int i = 2; i < (total + 1); i++) {
            String vlan = generateRule(i, stuff);
            result.add(vlan);
        }

//        result.add("</dhcpd>");


        File file = new File("/home/jacob/Andet/Temp/testxml/backup/FraNyServerSetup/generated/rules.xml");
        FileUtils.writeLines(file, result);
    }

    private static String generateRule(int i, Stuff stuff) {

        String s = "\t<rule>\n" +
                "\t\t<id></id>\n" +
                "\t\t<tracker>" + stuff.getUniqueValue() + "</tracker>\n" +
                "\t\t<type>pass</type>\n" +
                "\t\t<interface>opt" + i + "</interface>\n" +
                "\t\t<ipprotocol>inet46</ipprotocol>\n" +
                "\t\t<tag></tag>\n" +
                "\t\t<tagged></tagged>\n" +
                "\t\t<max></max>\n" +
                "\t\t<max-src-nodes></max-src-nodes>\n" +
                "\t\t<max-src-conn></max-src-conn>\n" +
                "\t\t<max-src-states></max-src-states>\n" +
                "\t\t<statetimeout></statetimeout>\n" +
                "\t\t<statetype>keep state</statetype>\n" +
                "\t\t<os></os>\n" +
                "\t\t<source>\n" +
                "\t\t\t<network>opt" + i + "</network>\n" +
                "\t\t</source>\n" +
                "\t\t<destination>\n" +
                "\t\t\t<network>opt" + i + "</network>\n" +
                "\t\t</destination>\n" +
                "\t\t<descr><![CDATA[Allow self]]></descr>\n" +
                "\t\t<created>\n" +
                "\t\t\t<time>"+ stuff.getUniqueValue() + "</time>\n" +
                "\t\t\t<username>Bjarke@10.10.0.152</username>\n" +
                "\t\t</created>\n" +
                "\t\t<updated>\n" +
                "\t\t\t<time>" + stuff.getUniqueValue() + "</time>\n" +
                "\t\t\t<username>Bjarke@10.10.0.152</username>\n" +
                "\t\t</updated>\n" +
                "\t</rule>\n" +
                "\t<rule>\n" +
                "\t\t<id></id>\n" +
                "\t\t<tracker>" + stuff.getUniqueValue() + "</tracker>\n" +
                "\t\t<type>pass</type>\n" +
                "\t\t<interface>opt" + i + "</interface>\n" +
                "\t\t<ipprotocol>inet46</ipprotocol>\n" +
                "\t\t<tag></tag>\n" +
                "\t\t<tagged></tagged>\n" +
                "\t\t<max></max>\n" +
                "\t\t<max-src-nodes></max-src-nodes>\n" +
                "\t\t<max-src-conn></max-src-conn>\n" +
                "\t\t<max-src-states></max-src-states>\n" +
                "\t\t<statetimeout></statetimeout>\n" +
                "\t\t<statetype>keep state</statetype>\n" +
                "\t\t<os></os>\n" +
                "\t\t<source>\n" +
                "\t\t\t<network>opt" + i + "</network>\n" +
                "\t\t</source>\n" +
                "\t\t<destination>\n" +
                "\t\t\t<address>ApartmentsAllowedDefault</address>\n" +
                "\t\t</destination>\n" +
                "\t\t<descr><![CDATA[Allow internal]]></descr>\n" +
                "\t\t<created>\n" +
                "\t\t\t<time>" + stuff.getUniqueValue() + "</time>\n" +
                "\t\t\t<username>Bjarke@10.10.0.152</username>\n" +
                "\t\t</created>\n" +
                "\t</rule>\n" +
                "\t<rule>\n" +
                "\t\t<id></id>\n" +
                "\t\t<tracker>" + stuff.getUniqueValue() + "</tracker>\n" +
                "\t\t<type>pass</type>\n" +
                "\t\t<interface>opt" + i + "</interface>\n" +
                "\t\t<ipprotocol>inet46</ipprotocol>\n" +
                "\t\t<tag></tag>\n" +
                "\t\t<tagged></tagged>\n" +
                "\t\t<max></max>\n" +
                "\t\t<max-src-nodes></max-src-nodes>\n" +
                "\t\t<max-src-conn></max-src-conn>\n" +
                "\t\t<max-src-states></max-src-states>\n" +
                "\t\t<statetimeout></statetimeout>\n" +
                "\t\t<statetype>keep state</statetype>\n" +
                "\t\t<os></os>\n" +
                "\t\t<source>\n" +
                "\t\t\t<network>opt" + i + "</network>\n" +
                "\t\t</source>\n" +
                "\t\t<destination>\n" +
                "\t\t\t<address>ApartmentsBlockedDefault</address>\n" +
                "\t\t\t<not></not>\n" +
                "\t\t</destination>\n" +
                "\t\t<descr><![CDATA[Allow external]]></descr>\n" +
                "\t\t<created>\n" +
                "\t\t\t<time>" + stuff.getUniqueValue() + "</time>\n" +
                "\t\t\t<username>Bjarke@10.10.0.152</username>\n" +
                "\t\t</created>\n" +
                "\t</rule>";

        return s;
    }

    static class Stuff {
        private long uniqueValue = 1521329230;

        public long getUniqueValue() {
//        long currentValue = uniqueValue;
//        uniqueValue = currentValue++;
            return uniqueValue++;
        }
    }
}
