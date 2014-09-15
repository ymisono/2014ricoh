#!/usr/bin/ruby
require 'socket'
require 'mysql'
require 'json'

server = TCPServer.new(23627)
db = Mysql.connect('localhost','ricoh_client','pass1234','ricoh')
stmt = db.prepare('INSERT INTO test_table (subject,sendername) VALUES(?,?)')

loop do
	socket = server.accept
	p "Connection establised!"
	#p socket.peeraddr
	while buffer = socket.gets
		#p buffer
		receivedData = JSON.parse(buffer)
	end
	p receivedData
	stmt.execute receivedData["subject"],receivedData["sendername"]
	p "Terminating connection."
	socket.close
end

#should not come here
server.close
