package jp.ac.hiroshima_cu.testshare;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    AlertDialog.Builder alertDialogBuilder;
    String displayStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//���X���������
		setContentView(R.layout.activity_main);
		
		Button sendButton = (Button)findViewById(R.id.send_button);
		
	    alertDialogBuilder = new AlertDialog.Builder(this);
        // �A���[�g�_�C�A���O�̃^�C�g����ݒ肵�܂�
        alertDialogBuilder.setTitle("�m�F");

        /*// �A���[�g�_�C�A���O�̍m��{�^�����N���b�N���ꂽ���ɌĂяo�����R�[���o�b�N���X�i�[��o�^���܂�
        alertDialogBuilder.setPositiveButton("�m��",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                */
        // �A���[�g�_�C�A���O�̒����{�^�����N���b�N���ꂽ���ɌĂяo�����R�[���o�b�N���X�i�[��o�^���܂�
        alertDialogBuilder.setNeutralButton("����",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        /*
        // �A���[�g�_�C�A���O�̔ے�{�^�����N���b�N���ꂽ���ɌĂяo�����R�[���o�b�N���X�i�[��o�^���܂�
        alertDialogBuilder.setNegativeButton("�ے�",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        */
        alertDialogBuilder.setCancelable(true);
        // �A���[�g�_�C�A���O��\�����܂�
        sendButton.setOnClickListener(this);

	    
		/*
		LinearLayout linearLayout = new LinearLayout(this); 
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		setContentView(linearLayout);
		
		TextView tv = new TextView(this);
		tv.setText("�t�@�C��������͂��Ă�������");
		linearLayout.addView(tv,new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT)
		);
		
		EditText edit = new EditText(this);
		linearLayout.addView(edit,
				new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				*/
		
	}
	
	public void onClick(View v){
		RegisterDTO dto = new RegisterDTO();
		EditText ui_subject = (EditText)findViewById(R.id.subject_edittext);
		SpannableStringBuilder sb = (SpannableStringBuilder)ui_subject.getText();
	    dto.Subject = sb.toString();

		EditText ui_sender = (EditText)findViewById(R.id.sender_edittext);
		sb = (SpannableStringBuilder)ui_sender.getText();
		dto.SenderName = sb.toString();
		
	    RegisterToDB("eros.sos.info.hiroshima-cu.ac.jp",23627,dto);	    
	}
	
	private void RegisterToDB(String hostname,int port,RegisterDTO dto) {
		Socket connection = null;
		DataOutputStream writer = null;
		BufferedReader reader = null;
		try {
			connection = new Socket(hostname,port);
			
			writer = new DataOutputStream(connection.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			writer.writeBytes(MakeJSONForDB(dto));
			//displayStr = reader.readLine();
		} catch (UnknownHostException e) {
			Toast.makeText(this, "�z�X�g��������܂���", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				reader.close();
				connection.close();				
			} catch (UnknownHostException e) {
				Toast.makeText(this, "�\�P�b�g����ꂹ��ł����B�i�z�X�g��������܂���j", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			    //�A���[�g�_�C�A���O�̃��b�Z�[�W��ݒ肵�܂�
		        alertDialogBuilder.setMessage("���M�ɐ������܂����B");
		        AlertDialog alertDialog = alertDialogBuilder.create();
		        alertDialog.show();
			}
		}
	}

	public String MakeJSONForDB(RegisterDTO dto){
		String result = null;
		result = String.format("{\"subject\":\"%s\",\"sendername\":\"%s\"}",dto.Subject,dto.SenderName);

		return result;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
