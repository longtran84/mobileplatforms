package vn.bcy.vgca.simtoolkit;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class SSLUtilitiesMy {
    private static HostnameVerifier __hostnameVerifier;
    private static TrustManager[] __trustManagers;
    private static HostnameVerifier _hostnameVerifier;
    private static TrustManager[] _trustManagers;

    public static class FakeHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static class FakeX509TrustManager implements X509TrustManager {
        private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[0];

        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return _AcceptedIssuers;
        }
    }

    public static class _FakeHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static class _FakeX509TrustManager implements X509TrustManager {
        private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[0];

        public boolean isClientTrusted(X509Certificate[] chain) {
            return true;
        }

        public boolean isServerTrusted(X509Certificate[] chain) {
            return true;
        }

        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return _AcceptedIssuers;
        }
    }

    private static void __trustAllHostnames() {
        if (__hostnameVerifier == null) {
            __hostnameVerifier = new _FakeHostnameVerifier();
        }
        HttpsURLConnection.setDefaultHostnameVerifier(__hostnameVerifier);
    }

    private static void __trustAllHttpsCertificates() {
        if (__trustManagers == null) {
            __trustManagers = new TrustManager[]{new _FakeX509TrustManager()};
        }
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, __trustManagers, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (GeneralSecurityException gse) {
            throw new IllegalStateException(gse.getMessage());
        }
    }

    private static boolean isDeprecatedSSLProtocol() {
        return "com.sun.net.ssl.internal.www.protocol".equals(System.getProperty("java.protocol.handler.pkgs"));
    }

    private static void _trustAllHostnames() {
        if (_hostnameVerifier == null) {
            _hostnameVerifier = new FakeHostnameVerifier();
        }
        HttpsURLConnection.setDefaultHostnameVerifier(_hostnameVerifier);
    }

    private static void _trustAllHttpsCertificates() {
        if (_trustManagers == null) {
            _trustManagers = new TrustManager[]{new FakeX509TrustManager()};
        }
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, _trustManagers, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (GeneralSecurityException gse) {
            throw new IllegalStateException(gse.getMessage());
        }
    }

    public static void trustAllHostnames() {
        if (isDeprecatedSSLProtocol()) {
            __trustAllHostnames();
        } else {
            _trustAllHostnames();
        }
    }

    public static void trustAllHttpsCertificates() {
        if (isDeprecatedSSLProtocol()) {
            __trustAllHttpsCertificates();
        } else {
            _trustAllHttpsCertificates();
        }
    }
}
