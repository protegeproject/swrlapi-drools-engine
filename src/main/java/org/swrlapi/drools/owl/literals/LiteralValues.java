package org.swrlapi.drools.owl.literals;

import java.util.Optional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/** Value-space operations for RL rules; L retains structural OWL literal identity. */
public final class LiteralValues
{
  private static final Object UNKNOWN = new Object();
  private static final Object INVALID = new Object();
  private LiteralValues() { }

  public static boolean isInvalid(L literal) { return value(literal) == INVALID; }

  public static boolean sameValue(L left, L right) {
    Object a = value(left), b = value(right);
    if (a == INVALID || b == INVALID) return false;
    if (a == UNKNOWN || b == UNKNOWN) return left.equals(right);
    if (a instanceof BigDecimal && b instanceof BigDecimal) return ((BigDecimal)a).compareTo((BigDecimal)b) == 0;
    if (a instanceof byte[] && b instanceof byte[]) return left.datatypeName.equals(right.datatypeName) && Arrays.equals((byte[])a, (byte[])b);
    if (a instanceof XMLGregorianCalendar && b instanceof XMLGregorianCalendar)
      return ((XMLGregorianCalendar)a).compare((XMLGregorianCalendar)b) == DatatypeConstants.EQUAL;
    return a.equals(b);
  }

  /** Failure to prove equality is not proof of inequality for unknown or partially ordered values. */
  public static boolean differentValue(L left, L right) {
    Object a = value(left), b = value(right);
    if (a == INVALID || b == INVALID || a == UNKNOWN || b == UNKNOWN) return false;
    if (a instanceof XMLGregorianCalendar && b instanceof XMLGregorianCalendar) {
      XMLGregorianCalendar first = (XMLGregorianCalendar)a, second = (XMLGregorianCalendar)b;
      if (!first.getXMLSchemaType().equals(second.getXMLSchemaType())) return true;
      int comparison = first.compare(second);
      return comparison == DatatypeConstants.LESSER || comparison == DatatypeConstants.GREATER;
    }
    return !sameValue(left, right);
  }

  /** Positive membership is restricted to understood value spaces. */
  public static boolean inside(L literal, String datatype) {
    Object value = value(literal);
    if (value == INVALID) return false;
    if (datatype.equals("rdfs:Literal")) return true;
    if (value == UNKNOWN) return false;
    return (isDecimal(datatype) || isStringType(datatype) || Arrays.asList("xsd:string", "xsd:boolean",
      "xsd:float", "xsd:double", "xsd:hexBinary", "xsd:base64Binary", "xsd:anyURI", "xsd:dateTime",
      "xsd:dateTimeStamp", "xsd:date", "xsd:time", "rdf:langString", "rdf:PlainLiteral", "rdf:XMLLiteral").contains(datatype))
      && !outside(literal, datatype);
  }

  /** Unknown/custom datatypes are left undecided rather than declared inconsistent. */
  public static boolean outside(L literal, String datatype) {
    Object value = value(literal);
    if (value == INVALID) return true;
    if (value == UNKNOWN || datatype.equals("rdfs:Literal")) return false;
    if (literal.datatypeName.equals(datatype)) return false;
    if (isDecimal(datatype)) {
      if (!(value instanceof BigDecimal)) return true;
      BigDecimal number = (BigDecimal)value;
      if (datatype.equals("xsd:decimal")) return false;
      try { return !inIntegerRange(number.toBigIntegerExact(), datatype); }
      catch (ArithmeticException e) { return true; }
    }
    if (datatype.equals("xsd:string")) return !(value instanceof String);
    if (datatype.equals("xsd:boolean")) return !(value instanceof Boolean);
    if (datatype.equals("xsd:dateTime") || datatype.equals("xsd:dateTimeStamp") || datatype.equals("xsd:date") || datatype.equals("xsd:time")) {
      if (!(value instanceof XMLGregorianCalendar)) return true;
      XMLGregorianCalendar calendar = (XMLGregorianCalendar)value;
      return !calendar.getXMLSchemaType().equals(datatype.equals("xsd:date") ? DatatypeConstants.DATE :
        datatype.equals("xsd:time") ? DatatypeConstants.TIME : DatatypeConstants.DATETIME)
        || datatype.equals("xsd:dateTimeStamp") && calendar.getTimezone() == DatatypeConstants.FIELD_UNDEFINED;
    }
    if (datatype.equals("xsd:hexBinary") || datatype.equals("xsd:base64Binary")) return !literal.datatypeName.equals(datatype);
    if (datatype.equals("xsd:float")) return !(value instanceof Float);
    if (datatype.equals("xsd:double")) return !(value instanceof Double);
    if (datatype.equals("rdf:langString")) return !(value instanceof L) || !((L)value).datatypeName.equals("rdf:langString");
    if (datatype.equals("rdf:PlainLiteral")) return !(value instanceof String)
      && (!(value instanceof L) || !((L)value).datatypeName.equals("rdf:langString"));
    if (datatype.equals("xsd:anyURI") || datatype.equals("rdf:XMLLiteral")) return !literal.datatypeName.equals(datatype);
    // Check the value, not its original lexical representation, against string facets.
    if (isStringType(datatype)) return !(value instanceof String) || value(new L((String)value, datatype)) == INVALID
      || !value.equals(value(new L((String)value, datatype)));
    return false;
  }

  private static Object value(L literal) {
    String type = literal.datatypeName, raw = literal.value, text = collapseWhitespace(raw);
    if (type.equals("rdf:XMLLiteral")) return canonicalXML(raw);
    try {
      if (isDecimal(type)) {
        if (type.equals("xsd:decimal")) {
          if (!text.matches("[+-]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)")) return INVALID;
          return new BigDecimal(text);
        }
        if (!text.matches("[+-]?[0-9]+")) return INVALID;
        BigInteger integer = new BigInteger(text);
        return inIntegerRange(integer, type) ? new BigDecimal(integer) : INVALID;
      }
      if (type.equals("xsd:boolean")) {
        if (text.equals("true") || text.equals("1")) return Boolean.TRUE;
        if (text.equals("false") || text.equals("0")) return Boolean.FALSE;
        return INVALID;
      }
      if (type.equals("xsd:string")) return raw;
      if (isStringType(type)) {
        String normalized = raw.replace('\t', ' ').replace('\r', ' ').replace('\n', ' ');
        if (type.equals("xsd:normalizedString")) return normalized;
        normalized = collapseWhitespace(normalized);
        if (type.equals("xsd:token")) return normalized;
        if (type.equals("xsd:language")) return normalized.matches("[a-zA-Z]{1,8}(?:-[a-zA-Z0-9]{1,8})*") ? normalized : INVALID;
        if (type.equals("xsd:Name")) return validName(normalized, type) ? normalized : INVALID;
        if (type.equals("xsd:NCName")) return validName(normalized, type) ? normalized : INVALID;
        if (type.equals("xsd:NMTOKEN")) return validName(normalized, type) ? normalized : INVALID;
      }
      if (type.equals("rdf:langString")) {
        if (literal.languageTag.isEmpty()) return INVALID;
        new java.util.Locale.Builder().setLanguageTag(literal.languageTag);
        return literal;
      }
      if (type.equals("rdf:PlainLiteral")) {
        int at = raw.lastIndexOf('@');
        if (at < 0) return INVALID;
        String language = raw.substring(at + 1);
        return language.isEmpty() ? raw.substring(0, at) : value(new L(raw.substring(0, at), "rdf:langString", language));
      }
      if (type.equals("xsd:anyURI")) return new L(text, type);
      if (type.equals("xsd:dateTime") || type.equals("xsd:dateTimeStamp") || type.equals("xsd:date") || type.equals("xsd:time")) {
        XMLGregorianCalendar calendar = DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(text);
        if (!calendar.isValid()) return INVALID;
        if (!calendar.getXMLSchemaType().equals(type.equals("xsd:date") ? DatatypeConstants.DATE :
          type.equals("xsd:time") ? DatatypeConstants.TIME : DatatypeConstants.DATETIME)) return INVALID;
        if (type.equals("xsd:dateTimeStamp") && calendar.getTimezone() == DatatypeConstants.FIELD_UNDEFINED) return INVALID;
        return calendar;
      }
      if (type.equals("xsd:hexBinary")) {
        if (!text.matches("(?:[0-9a-fA-F]{2})*")) return INVALID;
        byte[] bytes = new byte[text.length()/2];
        for (int n = 0; n < bytes.length; n++) bytes[n] = (byte)Integer.parseInt(text.substring(2*n, 2*n+2), 16);
        return bytes;
      }
      if (type.equals("xsd:base64Binary")) {
        String base64 = text.replaceAll("[\\t\\r\\n ]", "");
        if (base64.length() % 4 != 0) return INVALID;
        byte[] bytes = Base64.getDecoder().decode(base64);
        return Base64.getEncoder().encodeToString(bytes).equals(base64) ? bytes : INVALID;
      }
      if (type.equals("xsd:float") || type.equals("xsd:double")) {
        if (!text.matches("[+-]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][+-]?[0-9]+)?|INF|-INF|NaN")) return INVALID;
        // Preserve the separate floating-point value spaces.
        String number = text.replace("INF", "Infinity");
        if (type.equals("xsd:float")) return Float.valueOf(number);
        return Double.valueOf(number);
      }
      return UNKNOWN;
    } catch (IllegalArgumentException | IllegalStateException | java.util.IllformedLocaleException e) { return INVALID; }
  }

  private static boolean validName(String value, String type) {
    if (value.isEmpty()) return false;
    if (type.equals("xsd:NCName")) return org.semanticweb.owlapi.io.XMLUtils.isNCName(value);
    int offset = 0;
    if (type.equals("xsd:Name")) {
      int first = value.codePointAt(0);
      if (!org.semanticweb.owlapi.io.XMLUtils.isXMLNameStartCharacter(first)) return false;
      offset = Character.charCount(first);
    }
    while (offset < value.length()) {
      int cp = value.codePointAt(offset);
      if (!org.semanticweb.owlapi.io.XMLUtils.isXMLNameChar(cp)) return false;
      offset += Character.charCount(cp);
    }
    return true;
  }

  private static String collapseWhitespace(String value) {
    return value.replaceAll("[\\t\\r\\n ]+", " ").replaceAll("^ | $", "");
  }

  private static Object canonicalXML(String value) {
    try {
      javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newDefaultInstance();
      factory.setNamespaceAware(true);
      factory.setFeature(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, true);
      factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      factory.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD, "");
      factory.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      javax.xml.parsers.DocumentBuilder parser = factory.newDocumentBuilder();
      parser.setErrorHandler(new org.xml.sax.helpers.DefaultHandler() {
        @Override public void error(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException { throw e; }
        @Override public void fatalError(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException { throw e; }
      });
      org.w3c.dom.Document document = parser.parse(new org.xml.sax.InputSource(new java.io.StringReader("<root>" + value + "</root>")));
      List<Node> nodes = new ArrayList<>();
      for (Node child = document.getDocumentElement().getFirstChild(); child != null; child = child.getNextSibling())
        collectNodes(child, nodes);
      if (nodes.isEmpty()) return new L("", "rdf:XMLLiteral");
      javax.xml.crypto.dsig.TransformService canonicalizer = javax.xml.crypto.dsig.TransformService.getInstance(
        javax.xml.crypto.dsig.CanonicalizationMethod.EXCLUSIVE_WITH_COMMENTS, "DOM");
      canonicalizer.init((javax.xml.crypto.dsig.spec.TransformParameterSpec)null);
      javax.xml.crypto.dom.DOMCryptoContext context = new javax.xml.crypto.dom.DOMCryptoContext() { };
      canonicalizer.marshalParams(new javax.xml.crypto.dom.DOMStructure(document.createElementNS(
        javax.xml.crypto.dsig.XMLSignature.XMLNS, "Transform")), context);
      java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
      canonicalizer.transform((javax.xml.crypto.NodeSetData<Node>)nodes::iterator, context, output);
      return new L(output.toString(java.nio.charset.StandardCharsets.UTF_8), "rdf:XMLLiteral");
    } catch (javax.xml.parsers.ParserConfigurationException | org.xml.sax.SAXException | java.io.IOException
      | javax.xml.crypto.dsig.TransformException e) {
      return INVALID;
    } catch (java.security.GeneralSecurityException | javax.xml.crypto.MarshalException e) {
      throw new IllegalStateException("XML canonicalization is unavailable", e);
    }
  }

  private static void collectNodes(Node node, List<Node> nodes) {
    nodes.add(node);
    org.w3c.dom.NamedNodeMap attributes = node.getAttributes();
    if (attributes != null)
      for (int i = 0; i < attributes.getLength(); i++)
        nodes.add(Optional.ofNullable(attributes.item(i))
          .orElseThrow(() -> new IllegalStateException("Missing DOM attribute")));
    for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) collectNodes(child, nodes);
  }

  private static boolean isStringType(String type) {
    return Arrays.asList("xsd:normalizedString", "xsd:token", "xsd:language", "xsd:Name", "xsd:NCName", "xsd:NMTOKEN").contains(type);
  }
  private static boolean isDecimal(String type) {
    return type.equals("xsd:decimal") || Arrays.asList("xsd:integer", "xsd:long", "xsd:int", "xsd:short", "xsd:byte",
      "xsd:nonNegativeInteger", "xsd:positiveInteger", "xsd:nonPositiveInteger", "xsd:negativeInteger",
      "xsd:unsignedLong", "xsd:unsignedInt", "xsd:unsignedShort", "xsd:unsignedByte").contains(type);
  }
  private static boolean inIntegerRange(BigInteger value, String type) {
    switch (type) {
      case "xsd:byte": return value.bitLength() < 8;
      case "xsd:short": return value.bitLength() < 16;
      case "xsd:int": return value.bitLength() < 32;
      case "xsd:long": return value.bitLength() < 64;
      case "xsd:unsignedByte": return value.signum() >= 0 && value.bitLength() <= 8;
      case "xsd:unsignedShort": return value.signum() >= 0 && value.bitLength() <= 16;
      case "xsd:unsignedInt": return value.signum() >= 0 && value.bitLength() <= 32;
      case "xsd:unsignedLong": return value.signum() >= 0 && value.bitLength() <= 64;
      case "xsd:nonNegativeInteger": return value.signum() >= 0;
      case "xsd:positiveInteger": return value.signum() > 0;
      case "xsd:nonPositiveInteger": return value.signum() <= 0;
      case "xsd:negativeInteger": return value.signum() < 0;
      default: return true;
    }
  }
}
