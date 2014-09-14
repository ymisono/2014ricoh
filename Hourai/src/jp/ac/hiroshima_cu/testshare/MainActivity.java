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
		
		//元々あったやつ
		setContentView(R.layout.activity_main);
		
		Button sendButton = (Button)findViewById(R.id.send_button);
		
	    alertDialogBuilder = new AlertDialog.Builder(this);
        // アラートダイアログのタイトルを設定します
        alertDialogBuilder.setTitle("確認");

        /*// アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
        alertDialogBuilder.setPositiveButton("肯定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                */
        // アラートダイアログの中立ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
        alertDialogBuilder.setNeutralButton("了解",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        /*
        // アラートダイアログの否定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
        alertDialogBuilder.setNegativeButton("否定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        */
        alertDialogBuilder.setCancelable(true);
        // アラートダイアログを表示します
        sendButton.setOnClickListener(this);

	    
		/*
		LinearLayout linearLayout = new LinearLayout(this); 
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		setContentView(linearLayout);
		
		TextView tv = new TextView(this);
		tv.setText("ファイル名を入力してください");
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
			Toast.makeText(this, "ホストが見つかりません", Toast.LENGTH_SHORT).show();
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
