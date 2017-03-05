#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

int main()
{
    int Socket, newSocket;
    char buffer[1024];
    struct sockaddr_in serverAddr;
    struct sockaddr_storage serverStorage;
    socklen_t addr_size;
	
    Socket = socket(PF_INET, SOCK_STREAM, 0);
  
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(8080);
    serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
  
    memset(serverAddr.sin_zero, '\0', sizeof serverAddr.sin_zero);  

    while(1)
    {
	bind(Socket, (struct sockaddr *) &serverAddr, sizeof(serverAddr));

	listen(Socket, 2);

        addr_size = sizeof serverStorage;
	newSocket = accept(Socket, (struct sockaddr *) &serverStorage, &addr_size);
	
        recv(newSocket, buffer, 10, 0);
	
        // Zufallszahl berechnen

	srand( (unsigned) time(NULL));
	int gefahr = rand () %5+1;

	char reply[2] = "";
	reply[0] = (char) gefahr;	
		
        // Gefahr an Client zurücksenden

	send(newSocket, reply, 10, 0);
    }		

    return 0;
}
