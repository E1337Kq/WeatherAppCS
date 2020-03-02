package com.example.weatherappcs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

    public class JavaMailAPI extends AsyncTask<Void,Void,Void>  {

        //Definisanje varijabli
      private Context mContext;
        private Session mSession;

        private String mEmail;
        private String mSubject;
        private String mMessage;

        private ProgressDialog mProgressDialog;

        //Postavka konstruktora
        public JavaMailAPI(Context mContext, String mEmail, String mSubject, String mMessage) {
            this.mContext = mContext;
            this.mEmail = mEmail;
            this.mSubject = mSubject;
            this.mMessage = mMessage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Progress dijalog prilikom slanja
            mProgressDialog = ProgressDialog.show(mContext,"Your message is sending, our team will contact you soon", "Please wait...",false,false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Gasenje progress dijaloga kada se poruka posalje
            mProgressDialog.dismiss();

            //Uspjesni toast
            Toast.makeText(mContext,"Your message is sent, our team will contact you soon",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Kreiranje propertiesa
            Properties props = new Properties();

            //Konfiguracija propertiesa za gmail
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            //Kreiranje nove sesije
            mSession = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
                        }
                    });

            try {
                //Creating MimeMessage object
                MimeMessage mm = new MimeMessage(mSession);

                //Setting sender address
                mm.setFrom(new InternetAddress(Utils.EMAIL));
                //Adding receiver
                mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
                //Adding subject
                mm.setSubject(mSubject);
                //Adding message
                mm.setText(mMessage);
                //Sending email
                Transport.send(mm);

//            BodyPart messageBodyPart = new MimeBodyPart();
//
//            messageBodyPart.setText(message);
//
//            Multipart multipart = new MimeMultipart();
//
//            multipart.addBodyPart(messageBodyPart);
//
//            messageBodyPart = new MimeBodyPart();
//
//            DataSource source = new FileDataSource(filePath);
//
//            messageBodyPart.setDataHandler(new DataHandler(source));
//
//            messageBodyPart.setFileName(filePath);
//
//            multipart.addBodyPart(messageBodyPart);

//            mm.setContent(multipart);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


