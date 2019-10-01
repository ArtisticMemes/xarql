### xarql.net  
xarql.net is a sister site to xarql.com and provides media hosting. Any one can post anonymously, and the service is separate from xarql.com
##### Supported Media Types (in order)
0. jpg
1. png
2. mp3
3. webp
4. webm
5. mp4

##### Uploading  
To upload a file, go to `xarql.net/-/upload`. First, solve the Recaptcha. Then, click "browse" to select a file. Lastly, click the "Sumbit" button. The server should redirect you to your media file's viewer once its upload is complete.
##### Backend  
The links correlate to a media type and ID. The first character represents the media's type, in accordance with the _Supported Media Types_ list. A file's ID is determined per media type, and each media type has a folder. The ID is the name of a folder, within a media type folder, in which a file named `raw` is kept. Links are then provided to the client as such:  
/01a **[** media type + id **]** 👉👉 /-/static/jpg/1a/raw.jpg **[** "/-/static/" + media type + "/" + id + "/raw." + media type **]**  
##### Viewer  
The viewer allows for short links for users and analytics collection. We track visits to xarql.net with Google Analytics. The viewer should provide a consistent and satisfactory experience for all media types.
