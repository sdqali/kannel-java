
StringBuffer gs = new StringBuffer();
String group = "";
List<String> props = new ArrayList<String>();
List<String> mprops = new ArrayList<String>();

inputFile = new File(args[0]);
inputFile.eachLine{ line ->
    def tokens = line.trim().split("\t");
    def prop = tokens[0];
    def mandatory = "n";
    if (prop.indexOf(" (")>0) {
        mandatory = prop.substring(prop.indexOf(" (")+2, prop.indexOf(" (")+3);
        prop = prop.substring(0, prop.indexOf(" ("));
    }

    def type = tokens[1];
    def comment = "";
    if (tokens.length>2) comment = tokens[2];
    if (comment.toLowerCase().contains("deprecated")) {
        //continue;
    } else {
        def default1 = "";
        if (tokens.length>3) default1 = tokens[3];
        if (prop.equals("group")) group = prop;
        if (!prop.equals("group")) {
            try {
                gs.append("${writeGetSet(prop, mapType(type), comment)}\n");
                //props.add(toCamelCase(prop));
                //if (mandatory.equals("m")) mprops.add(toCamelCase(prop));
                props.add(prop);
                if (mandatory.equals("m")) mprops.add(prop);
            } catch (Exception e) {
                println "error at ${prop}: ${e.message}";
            }
        }
        //println prop;
        //println mandatory;
    }
}
println writePropertyOrder(props);
println writeMandatoryProps(mprops);
println gs.toString();

def writePropertyOrder(List<String> props)
{
    StringBuffer o = new StringBuffer();
    props.each{ prop->
            o.append("\"${prop}\",");
    }
    String full = o.toString();
    return """\tprivate static final String[] PROP_ORDER = { ${full.substring(0, full.length()-1)} };

\tpublic String[] getPropertyOrder() {
\t\treturn PROP_ORDER;
\t}
""";
} 

def writeMandatoryProps(List<String> props)
{
    StringBuffer o = new StringBuffer();
    props.each{ prop->
            o.append("\"${prop}\",");
    }
    String full = o.toString();
    return """\tprivate static final String[] MANDATORY_PROPS = { ${full.substring(0, full.length()-1)} };

\tpublic String[] getMandatoryProps() {
\t\treturn MANDATORY_PROPS;
\t}
""";
} 


def writeGetSet(String prop, String type, String comment)
{
    return """${writeGetSetComment(prop, type, comment)}
\tprivate ${type} ${toCamelCase(prop)};
\tpublic ${type} ${toCamelCase("get-"+prop)}() { return this.${toCamelCase(prop)}; }
\tpublic void ${toCamelCase("set-"+prop)}(${type} ${toCamelCase(prop)}) { this.${toCamelCase(prop)} = ${toCamelCase(prop)}; }
""";
}

def writeGetSetComment(String prop, String type, String comment)
{
    StringBuffer o = new StringBuffer();
    o.append("\t/**\n\t * $prop\n");
    splitComment(comment, 80).each{ c->
            o.append("\t * ${c}\n");
    }
    o.append("\t */");
    return o.toString();
}


def mapType(String type)
{
    type = type.trim().toLowerCase();
    if (type.equals("string")) return "String";
    if (type.equals("port-number")) return "Integer";
    if (type.equals("bool")) return "Boolean";
    if (type.equals("boolean")) return "Boolean";
    if (type.equals("ip-list")) return "String[]"; //
    if (type.equals("ip or '*'")) return "String"; //
    if (type.equals("filename")) return "String"; //
    if (type.equals("number 0..5")) return "Integer";
    if (type.equals("prefix-list")) return "String[]";
    if (type.equals("url")) return "URL";
    if (type.equals("seconds")) return "Integer";
    if (type.equals("hostname")) return "String"; //
    if (type.equals("url-list")) return "String[]"; //
    if (type.equals("unix regular expression")) return "String";
    if (type.equals("username")) return "String";
    if (type.equals("password")) return "String";
    if (type.equals("type")) return "String";
    if (type.equals("number of messages")) return "Integer";
    if (type.equals("number (messages)")) return "Integer";
    if (type.equals("posix regular expression")) return "String";
    if (type.equals("number")) return "Integer";
    if (type.equals("value-in-seconds")) return "Integer";
    if (type.equals("url-pair")) return "String[]"; //
    if (type.equals("ip address")) return "String"; //
    if (type.equals("fqdn or ip address")) return "String"; //
    if (type.equals("float (messages/sec)")) return "Integer";
    if (type.equals("id-list")) return "String[]";
    if (type.equals("device-name")) return "String";
    if (type.equals("number (seconds)")) return "Integer";
    if (type.equals("4 num char.")) return "Integer";
    if (type.equals("string (max 16)")) return "String";
    if (type.equals("integer")) return "Integer";
    if (type.equals("ip")) return "String"; //
    if (type.equals("port number")) return "Integer";
    if (type.equals("serial speed in bps")) return "Integer";
    if (type.equals("number (milliseconds)")) return "Integer";
    if (type.equals("hostname or ip")) return "String"; //
    if (type.equals("phone-number")) return "String";
    if (type.equals("word-list")) return "String[]";
    if (type.equals("number-list")) return "String[]";
    if (type.equals("word")) return "String";
    if (type.equals("on or off")) return "Boolean";
    if (type.equals("phone number list")) return "String[]";
    if (type.equals("number (minutes)")) return "Integer";
    if (type.equals("number (bit mask)")) return "Integer";
    if (type.equals("string (url)")) return "URL";
    if (type.equals("string (url)")) return "URL";
    if (type.equals("xml document")) return "String";
    if (type.equals("hex")) return "String";
    if (type.equals("posix regular expression")) return "String";
    //    if (type.equals("")) return "";
    //    return "String";
    throw new Exception("unknown type: "+type);
}

def toCamelCase(String s)
{
    String[] sp = s.toLowerCase().split("-");
    StringBuilder o = new StringBuilder(sp[0]);
    for (int i=1;i<sp.length;i++) {
        String e = sp[i];
        o.append(e.substring(0, 1).toUpperCase());
        if (e.length() > 1) o.append(e.substring(1).toLowerCase());
    }
    return o.toString();
}

def splitComment(String s, int len) {
    String[] words = s.split(" ");
    List<String> lines = new ArrayList<String>();
    StringBuffer o = new StringBuffer();
    for (String word:words) {
        o.append(word).append(" ");
        if (o.length() >= len) {
            lines.add(o.toString());
            o = new StringBuffer();
        }
    }
    if (o.length() > 0) lines.add(o.toString());
    return lines;
}
