package com.utilities.dhananjayan.samplecontacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// Dhananjay Ankushrao
// hgjhg
// yfyugfuyu

public class ContactReadDemo extends Activity {

    private Button m_objBtnSkypeContact;
    private Button m_objBtnPhoneContact;
    private TextView m_objDisplayContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_read_main);

        m_objBtnSkypeContact = (Button)findViewById(R.id.btnSkypeContact);
        m_objBtnPhoneContact = (Button)findViewById(R.id.btnPhoneContact);
        m_objDisplayContact = (TextView)findViewById(R.id.txtDisplayContact);

        //skype phone contact button event
        m_objBtnSkypeContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try{
                    m_objDisplayContact.setText("");
                    getSkypeContactsList();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });


        //phone contact button event
        m_objBtnPhoneContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try{
                    m_objDisplayContact.setText("");
                    getPhoneContactsList();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     *
     * This function is used to display the list of Skype contacts
     */
    private void getSkypeContactsList() {
        StringBuffer strSkypeContactBuffer = new StringBuffer();
        Cursor objCursor = getContentResolver().query(
                Data.CONTENT_URI,
                new String[] { Data.CONTACT_ID, Data.DATA1 },
                Data.MIMETYPE + "= ?",
                new String[] { "vnd.android.cursor.item/com.skype.android.skypecall.action" },
                null);

        while (objCursor != null && objCursor.moveToNext()) {
            long contact = objCursor.getLong(0);
            String skype = objCursor.getString(1);

            Log.i("ContactsApp" , " " + "contact " + contact + " has skype username: " + skype);
            strSkypeContactBuffer.append("Contact " + contact + " has skype username: " + skype + "\n");

        }

        //display contact details
        m_objDisplayContact.setText(strSkypeContactBuffer.toString());
    }

    /**
     *
     *
     * This function is used to display the existing phone contacts
     */
    private void getPhoneContactsList() {
        StringBuffer strPhoneContactBuffer = new StringBuffer();
        ContentResolver objContentResolver = getContentResolver();
        Cursor objCursor = objContentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (objCursor.getCount() > 0) {
            while (objCursor.moveToNext()) {
                String id = objCursor.getString(objCursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = objCursor.getString(objCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(objCursor.getString(
                        objCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor objContactCursor = objContentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (objContactCursor.moveToNext()) {
                        String phoneNo = objContactCursor.getString(objContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("ContactsApp", "Name: " + name + ", Phone No: " + phoneNo);
                        strPhoneContactBuffer.append("Name: " + name + ", Phone No: " + phoneNo + "\n");

                    }
                    objContactCursor.close();
                }
            }
        }

        //display contact details
        m_objDisplayContact.setText(strPhoneContactBuffer.toString());
    }
}