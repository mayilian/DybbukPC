#pragma once
#ifndef SERVER_H
#define SERVER_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>

void error(const char *msg)
{
	perror(msg);
	exit(1);
}

int server(const int port)
{
	struct sockaddr_in serv_addr, cli_addr;
	int sockfd;
        int newsockfd;
	int numberOfBytes;
	char buffer[1024];
	socklen_t clilen;

	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sockfd < 0) {
		error("ERROR: opening socket");
        }

	bzero((char *) &serv_addr, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = INADDR_ANY;
	serv_addr.sin_port = htons(port);

	if (bind(sockfd, (struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0) {
		error("ERROR: on binding");
	}

	while (1) {

		listen(sockfd,5);
		clilen = sizeof(cli_addr);
		newsockfd = accept(sockfd, 
				(struct sockaddr *) &cli_addr, 
				&clilen);

		if (newsockfd < 0) {
			error("ERROR: on accept");
		}

		bzero(buffer,1024);
		numberOfBytes = read(newsockfd,buffer,1023);

		if (numberOfBytes < 0) { 
			error("ERROR: reading from socket");
		}
                
                if (0 == strcmp(buffer,"mute")) {
		        system("amixer set Master mute");
                } else if (0 == strcmp(buffer,"unmute")) {
		        system("amixer set Master unmute");
                }

		if (numberOfBytes < 0)  { 
                         error("ERROR: writing to socket");
                }

		close(newsockfd);
	}

	close(sockfd);
	return 0; 
}

#endif
