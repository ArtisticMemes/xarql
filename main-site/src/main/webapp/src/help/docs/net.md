### xarql.net  
xarql.net is a sister site to xarql.com and provides image hosting. Any one can post anonymously, and the service is separate from xarql.com
##### Uploading  
To upload a file, go to `xarql.net/-/upload`. First, solve the Recaptcha. Then, click "browse" to select a file. Lastly, click the "Sumbit" button. The server should redirect you to your image once its upload is complete.
##### Backend  
The links correlate to a file type and folder. The first digit is the file type. `0` is for `.jpg` and `1` is for `.png`. The following digits are the name of the folder in which a file named `raw` is kept. Example: If the link is `xarql.net/01` the server pulls an image from `file-store/1/raw.jpg`.
##### Viewer  
The viewer allows for short links for users and analytics collection. We track visits to xarql.net with Google Analytics. The viewer will shrink any image to fit within the client's view port without distortion.
