#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

int main()
{					
    int clientSocket, sendS;
    struct sockaddr_in serverAddr;
    socklen_t addr_size;
    struct sockaddr_storage Storage;
    char eingabe[100]; 
    char get[2];

    // Verbindung aufbauen

    clientSocket = socket(PF_INET, SOCK_STREAM, 0);
	  
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(8080);

    serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");

    memset(serverAddr.sin_zero, '\0', sizeof serverAddr.sin_zero);  

    addr_size = sizeof serverAddr;
    connect(clientSocket, (struct sockaddr *) &serverAddr, addr_size);

    // Ortschaft eingeben und an Server senden

    printf("Ortschaft eingeben: ");     
    scanf("%s", &eingabe);

    send(clientSocket, eingabe, 100, 0);

    // Antwort von server holen und ausgeben

    recv(clientSocket, get, 2, 0);
    printf("Gefahr = %i", get[0]);
   
    return 0;
}
