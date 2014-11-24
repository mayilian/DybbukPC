dybbukPCd: server.c server.h
	gcc server.c -o $@
	sudo mv $@ /etc/init.d/
	sudo update-rc.d $@ defaults 
clean:
	rm -rf dybbukPCd 
