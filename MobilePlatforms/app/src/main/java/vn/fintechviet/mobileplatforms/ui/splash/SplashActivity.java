/*
 *  Copyright (C) 2018 - 2019 FINTECHVIET
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package vn.fintechviet.mobileplatforms.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gemalto.idgo800.testtool.common.GetCertResponse;
import com.gemalto.idgo800.testtool.remote.coresign.SignPDF;
import com.google.gson.Gson;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;

import javax.inject.Inject;
import java.io.File;
import vn.bcy.vgca.simtoolkit.VGCAToolkit;
import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.system.DeviceInfoPayload;
import vn.fintechviet.mobileplatforms.databinding.ActivitySplashBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseActivity;
import vn.fintechviet.mobileplatforms.ui.login.LoginActivity;
import vn.fintechviet.mobileplatforms.ui.main.MainActivity;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseField;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.SecurityConstants;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import vn.bcy.vgca.simtoolkit.VGCAToolkit;

/**
 * Created by long_tran on 08/07/17.
 */

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Inject
    SplashViewModel mSplashViewModel;

    /**
     *
     */
    private void registerDevice() {

    }

    /**
     * Display FCM services error
     */
    private void displayFCMServicesError() {
        SplashActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel getViewModel() {
        return mSplashViewModel;
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void accountSuspension() {
        getMaterialDialogAlert(this, getString(R.string.warning), getString(R.string.account_suspended_message), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                finish();
                System.exit(0);
            }
        }).show();
    }

    @Override
    public void accountActive() {
        mSplashViewModel.startSeeding(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashViewModel.setNavigator(this);
        DeviceInfoPayload deviceInfoPayload = new DeviceInfoPayload(this);
        mSplashViewModel.dataFetching(deviceInfoPayload);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        new MyTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private static final String TSA_DEFAULT = "http://ca.gov.vn/tsa";

    private String signSimPSS(String pathPdf, int currentPage, Rectangle rectangle, Bitmap bm) throws Exception {
        TSAClientBouncyCastle tsaClient;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 80, stream);
        String fileOut =
                Environment.getExternalStorageDirectory().getPath() + "/VGCASign/documents/" + new File(pathPdf).getName() + "_Signed" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
        new File(fileOut).getParentFile().mkdirs();
        tsaClient = new TSAClientBouncyCastle(TSA_DEFAULT, null, null, 4096, SecurityConstants.SHA1);
        VGCAToolkit.signPSS(pathPdf, fileOut, "841682927581", rectangle, currentPage, stream.toByteArray(), null, this.certificates, tsaClient);
        return fileOut;
    }

    public byte[] sign(byte[] dataToSign) throws GeneralSecurityException {
        try {
            String content = new String(Base64.encode(new DigestInfo(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256), MessageDigest.getInstance("SHA256").digest(dataToSign)).getEncoded("DER"), 0));
            Log.d(SplashActivity.TAG, "sign cotent: " + content);
            String sign = SignPDF.Sign(content, "Ky?", "841682927581");
            Log.d(SplashActivity.TAG, "signSim: " + sign);
            return Base64.decode(sign, 0);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GeneralSecurityException("Ahihi");
        } catch (Exception e2) {
            e2.printStackTrace();
            return new byte[0];
        }
    }

    private Certificate[] certificates;

    private void updateUISignInfo(String certStr) {
        try {

            X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(
                    new ByteArrayInputStream(com.itextpdf.text.pdf.codec.Base64.decode(certStr)));
            this.certificates = new Certificate[1];
            this.certificates[0] = cert;
            String[] arrContent = cert.getSubjectDN().getName().split(",");
            String signerName = "";
            String companySign = "";
            if (arrContent != null) {
                for (String s : arrContent) {
                    if (s.startsWith("CN=")) {
                        signerName = s.substring("CN=".length());
                    }
                    if (s.startsWith("O=")) {
                        companySign = s.substring("O=".length());
                    }
                }
            }
            String email = "";
            try {
                email = cert.getSubjectAlternativeNames().toString().replace("[", "").replace("]", "");
                String[] temp = email.split(",");
                for (int j = 0; j < temp.length; j++) {
                    if (temp[j].contains("@")) {
                        email = temp[j];
                    }
                }
                email = email.trim();
            } catch (CertificateParsingException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            System.err.println(certificates +"------------");
        } catch (CertificateException e3) {
            e3.printStackTrace();
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        String textResult;

        @Override
        protected Void doInBackground(Void... params) {


            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

            try {
                String sourceCert = VGCAToolkit.GetCert("841682927581");
                GetCertResponse response = (GetCertResponse) new Gson().fromJson(sourceCert, GetCertResponse.class);
                updateUISignInfo(response.getValue());
                System.err.println(new String(sign("AAAAAAAAAAAAAAAAAAAAAAAAAAA".getBytes())) +"----------------");
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            super.onPostExecute(result);
        }

    }
}
