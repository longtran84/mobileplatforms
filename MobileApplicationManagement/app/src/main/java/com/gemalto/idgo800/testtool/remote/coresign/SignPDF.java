package com.gemalto.idgo800.testtool.remote.coresign;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;
import com.gemalto.idgo800.testtool.common.Constants;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.CrlClient;
import com.itextpdf.text.pdf.security.CrlClientOnline;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.OcspClient;
import com.itextpdf.text.pdf.security.OcspClientBouncyCastle;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import vn.bcy.vgca.simtoolkit.SSLUtilitiesMy;

public class SignPDF {
    private int signatureSize = 12000;

//    public String mySign(String fileIn, Certificate[] chain, ExternalSignature signature, CryptoStandard subfilter, int pageNumber, float x, float y, float width, float height, float fontSize, Bitmap bgSign, Context context) throws IOException, GeneralSecurityException, DocumentException {
//        TSAClientBouncyCastle tsaClient;
//        float x1 = x;
//        float y1 = y;
//        float x2 = x + width;
//        float y2 = y + height;
//        PdfReader pdfReader = new PdfReader(fileIn);
//        String file_in_name = new File(fileIn).getName();
//        String nameOfFile = file_in_name.substring(0, file_in_name.length() - 4);
//        String fileOut = Environment.getExternalStorageDirectory().getPath() + "/VGCASign/documents/" + nameOfFile + "_Signed" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
//        File file = new File(fileOut);
//        file.getParentFile().mkdirs();
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        PdfStamper stamper = PdfStamper.createSignature(pdfReader, new FileOutputStream(fileOut), '\u0000');
//        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
//        appearance.setCertificationLevel(0);
//        appearance.setCryptoDictionary(pdfReader.getPageN(pageNumber));
//        OutputStream stream = new ByteArrayOutputStream();
//        bgSign.compress(CompressFormat.PNG, 80, stream);
//        appearance.setImage(Image.getInstance(stream.toByteArray()));
//        String name = appearance.getNewSigName();
//        Log.d("SignPDF", "mySign name: " + name);
//        appearance.setVisibleSignature(new Rectangle(x1, y1, x2, y2), pageNumber, name);
//        ExternalDigest digest = new BouncyCastleDigest();
//        List<CrlClient> crlList = new ArrayList();
//        crlList.add(new CrlClientOnline(chain));
//        OcspClient ocspClient = new OcspClientBouncyCastle();
//        if (SharefreferentUtils.isSignTsa(context)) {
//            tsaClient = new TSAClientBouncyCastle(SharefreferentUtils.getTsa(context), null, null, 4096, "SHA-1");
//        } else {
//            tsaClient = null;
//        }
//        MakeSignature.signDetached(appearance, digest, signature, chain, crlList, ocspClient, tsaClient, 0, subfilter);
//        stamper.close();
//        return fileOut;
//    }
//
//    public String mySignSim(String fileIn, Certificate[] chain, ExternalSignature signature, CryptoStandard subfilter, int pageNumber, Rectangle rectangle, Bitmap bgSign, Context context) throws IOException, GeneralSecurityException, DocumentException {
//        TSAClientBouncyCastle tsaClient;
//        PdfReader pdfReader = new PdfReader(fileIn);
//        String file_in_name = new File(fileIn).getName();
//        String nameOfFile = file_in_name.substring(0, file_in_name.length() - 4);
//        String fileOut = Environment.getExternalStorageDirectory().getPath() + "/VGCASign/documents/" + nameOfFile + "_Signed" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
//        File file = new File(fileOut);
//        file.getParentFile().mkdirs();
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        PdfStamper stamper = PdfStamper.createSignature(pdfReader, new FileOutputStream(fileOut), '\u0000');
//        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
//        appearance.setCertificationLevel(0);
//        appearance.setCryptoDictionary(pdfReader.getPageN(pageNumber));
//        appearance.setVisibleSignature(rectangle, pageNumber, appearance.getNewSigName());
//        OutputStream stream = new ByteArrayOutputStream();
//        bgSign.compress(CompressFormat.PNG, 80, stream);
//        appearance.setReasonCaption(" ");
//        appearance.setReason(" ");
//        appearance.setReasonCaption(" ");
//        appearance.setReason(" ");
//        appearance.setLayer2Text(" ");
//        appearance.setAcro6Layers(true);
//        appearance.setImage(Image.getInstance(stream.toByteArray()));
//        ExternalDigest digest = new BouncyCastleDigest();
//        List<CrlClient> crlList = new ArrayList();
//        crlList.add(new CrlClientOnline(chain));
//        OcspClient ocspClient = new OcspClientBouncyCastle();
//        if (SharefreferentUtils.isSignTsa(context)) {
//            tsaClient = new TSAClientBouncyCastle(SharefreferentUtils.getTsa(context), null, null, 4096, "SHA-1");
//        } else {
//            tsaClient = null;
//        }
//        MakeSignature.signDetached(appearance, digest, signature, chain, crlList, ocspClient, tsaClient, 0, subfilter);
//        stamper.close();
//        return fileOut;
//    }
//
//    private PdfStamper prepareStamper(InputStream pdfData, OutputStream output, Date signingTime) throws IOException, DocumentException {
//        PdfStamper stp = PdfStamper.createSignature(new PdfReader(pdfData), output, '\u0000', null, true);
//        PdfSignatureAppearance sap = stp.getSignatureAppearance();
//        sap.setAcro6Layers(true);
//        PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKMS, PdfName.ADBE_PKCS7_SHA1);
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(signingTime);
//        sap.setSignDate(cal);
//        dic.setDate(new PdfDate(cal));
//        sap.setCryptoDictionary(dic);
//        int csize = getSignatureSize();
//        HashMap exc = new HashMap();
//        exc.put(PdfName.CONTENTS, new Integer((csize * 2) + 2));
//        sap.preClose(exc);
//        return stp;
//    }
//
//    public byte[] digest(InputStream pdfData, Date signingDate) throws IOException, DocumentException {
//        PdfSignatureAppearance sap = prepareStamper(pdfData, new ByteArrayOutputStream(), signingDate).getSignatureAppearance();
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-1");
//            InputStream s = sap.getRangeStream();
//            byte[] buff = new byte[8192];
//            while (true) {
//                int read = s.read(buff, 0, 8192);
//                if (read <= 0) {
//                    return md.digest();
//                }
//                md.update(buff, 0, read);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("On wich JRE we don't even get the SHA1 algorithm ?!");
//        }
//    }

    public static String Sign(String base64Binary, String messageToDisplay, String phonenumber) throws Exception {
        System.out.println("in toolkit - sign");
        SSLUtilitiesMy.trustAllHostnames();
        SSLUtilitiesMy.trustAllHttpsCertificates();
        String requestString = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\"  xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"  xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\"  xmlns:m0=\"http://tempuri.org/\"  xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:urn=\"http://ca.gov.vn/\">\n     <SOAP-ENV:Header/>\n     <SOAP-ENV:Body>\n        <urn:Sign>\n           <urn:phone>" + phonenumber + "</urn:phone>\n           <urn:messageToBeDisplayed>" + messageToDisplay + "</urn:messageToBeDisplayed>\n           <urn:message>" + base64Binary + "</urn:message>\n        </urn:Sign>\n     </SOAP-ENV:Body>\n</SOAP-ENV:Envelope>\n";
        HttpURLConnection con = (HttpURLConnection) new URL(Constants.URL_SIGN).openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("SOAPAction", "\"http://ca.gov.vn/Sign\"");
        con.setRequestProperty("Content-type", "text/xml");
        con.setDoOutput(true);
        con.setDoInput(true);
        OutputStream reqStream = con.getOutputStream();
        reqStream.write(requestString.getBytes());
        reqStream.flush();
        System.out.println("Request length: " + requestString.length() + "\n");
        InputStream resStream = con.getInputStream();
        NodeList nodes = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(resStream).getDocumentElement().getChildNodes();
        if (0 < nodes.getLength()) {
            System.out.print(nodes.item(0).getTextContent());
            return nodes.item(0).getTextContent();
        }
        resStream.close();
        reqStream.close();
        resStream.close();
        return null;
    }

//    public void sign123(InputStream pdfData, byte[] signatureValue, OutputStream signedStream, Date signingDate) throws IOException, DocumentException {
//        PdfSignatureAppearance sap = prepareStamper(pdfData, signedStream, signingDate).getSignatureAppearance();
//        byte[] pk = signatureValue;
//        byte[] outc = new byte[getSignatureSize()];
//        PdfDictionary dic2 = new PdfDictionary();
//        System.arraycopy(pk, 0, outc, 0, pk.length);
//        dic2.put(PdfName.CONTENTS, new PdfString(outc).setHexWriting(true));
//        sap.close(dic2);
//    }

    public void setSignatureSize(int signatureSize) {
        this.signatureSize = signatureSize;
    }

    public int getSignatureSize() {
        return this.signatureSize;
    }
}
