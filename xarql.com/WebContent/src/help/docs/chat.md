### /chat
/chat is a real time chat page where users can have a conversation anonymously. Each user is identified by a random color. Messages disappear after 1 hour.  
Active users will be represented by a little colored icon above the message input field. The box will turn to a circle if they have text in their field (buffer). The box will have a shadow underneath it and look raised if they are typing (typing).  
/chat uses a WebSocket connection to make enable fast 2 way communication between clients and the server. The protocol isn't as easy to work with, so you should expect more errors than on /polr. The browser will automatically reload if you are disconnected from the WebSocket, which can happen from errors.
