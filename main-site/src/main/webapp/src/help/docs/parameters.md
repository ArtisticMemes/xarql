### HTML Parameters
HTML parameters are pieces of info that are given to the server in a request to make a more specific request at the same location.  

##### Use
HTML parameters can be used in a URL by attaching them using `?` followed by a mapping consisting of the parameter name, a `=` as a separator and the parameter value. More than one mapping can be used by separating them with `&`.

Sending a message with the `content` of `hello` would look like `https://xarql.com/chat/send?content=hello`. You might also attach a `username` of `dog` like `https://xarql.com/chat/send?content=hello&username=dog`.

##### Specials
Special characters and codes are used to represent symbols that can't be included in a URL. `+` or `%20` can be used for spaces. A value might say `hello%20world` and mean `hello world`. There are many other codes like `%20`.
