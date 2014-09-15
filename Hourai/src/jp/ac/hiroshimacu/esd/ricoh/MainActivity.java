package jp.ac.hiroshimacu.esd.ricoh;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    AlertDialog.Builder alertDialogBuilder;
    String displayStr;    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button sendButton = (Button)findViewById(R.id.send_button);
		
	    alertDialogBuilder = new AlertDialog.Builder(this);
        // アラートダイアログのタイトルを設定します
        alertDialogBuilder.setTitle("確認");
        
        // アラートダイアログの中立ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
        alertDialogBuilder.setNeutralButton("了解",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        
        alertDialogBuilder.setCancelable(true);
        // アラートダイアログを表示します
        sendButton.setOnClickListener(this);
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
			//Toast.makeText(this, "ホストが見つかりません", Toast.LENGTH_SHORT).show();
	        alertDialogBuilder.setMessage("ホストが見つかりません");
	        AlertDialog alertDialog = alertDialogBuilder.create();
	        alertDialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				reader.close();
				connection.close();				
			} catch (UnknownHostException e) {
				Toast.makeText(this, "ソケットを閉じれせんでした。（ホストが見つかりません）", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			    //アラートダイアログのメッセージを設定します
		        alertDialogBuilder.setMessage("送信に成功しました。");
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
}
