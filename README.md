2014ricoh
=========

2014年RICOH Javaチャレンジ用のリポジトリ
---------

###構成内容
 * "Hourai(ホウライ)" - 「送信アプリ」
 * "Fusang(フーソウ)" - 「クラウドサーバー」
 
###使い方（試験用）
####送信アプリ
h5. 実行までの手順
 1. 環境構築：JDK、ADT、MFPエミュレータをインストールする。
 2. クラウドサーバーをあらかじめ立ち上げておく。
 3. MFPのエミュレータを実行する。
 4. プロジェクト"Hourai"をビルド実行する（実行ターゲットをエミュレータに指定する）。
h5. ソフトの使い方
 1. 「教科名」「送信者の名前」を入力し、登録ボタンを押す。
 * ホストが見つからないなどのエラー処理はしていない。
 * 動作確認はクラウドサーバーのDBの該当テーブルをみる

####クラウドサーバー
#####実行までの手順
 1. ruby、mysqlをインストールする。
 2. ruby-mysqlをインストールする（"gem install ruby-mysql"）。
 2. mysqlで"ricoh_client"というユーザー名を作成し、パスワードを"pass1234"と登録する。
 3. mysqlで"ricoh"というデータベースを作成し、"test_table"というテーブルを作成する。
#####ソフトの使い方
 1. testshare_server.rbをバックグラウンド実行する。

###トラブルシューティング
####Eclipseでビルドエラーが出る場合
 * Andriodのライブラリを指定する（"libs/android-support-v4.jar"を外部jarに）

###ライセンス
The MIT License (MIT)

Copyright (c) 2014 Teni'muhou, Hiroshima City University

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
