### /polr
is an anonymous forum that is meant to be conducive to debate. Its main feature is that replies have replies, ad infinitum.

##### Navigation
There is a post at the top, before the "Create Post" element, which is called the "Main Post". The posts below 'Create Post' are the "Replies" to the main post. This vocabulary is important.  
**Replies per Page**  
20 replies are shown on each "Page". To view the other pages, the URL must be changed to include `?page=` with a number afterwards. The initial page is number 0, and there are 4 pages per main post, going up to the 4th which is page 3. To access the last one you would add `?page=3`. Although this means only 80 replies are available, there is no limit to how many may be made.  
**Sort**  
The default sorting method for replies is `subbump`. This relates to a date held in the database which represents the last time a reply was made either to the main post or one of its children. `bump` is the same but only with direct replies. `subresponses` is the amount of replies a post and all of its children have combined. `responses` is the amount of direct replies. `date` is the time at which the post was made. These sorting methods can be accessed by typing `?sort=` and whichever one you want. To get the replies with the most responses, you would put `?sort=responses`. To combine this with a page number and get the 21st through 40th replies sorted by responses, you would put `?page=1&sort=responses`. The order of the parameters don't matter, as long as they start with a `?` and have a `&` between them.  
**Flow**  
By default, sorting is done by most to least. For `date` that means newest first. In order to specify most first or least first, use `?flow=` + either `desc` or `asc`. `desc` will give you most first. `asc` will give you least first.  
**"Navigation" element**  
If you don't want to enter these manually, you can select these options from the navigation and click on "Custom". To view the next page, click "Next". To view the previous page, click "Prev".  
##### Create Post
This element allows user to submit posts to `/polr`. It has a form with 3 inputs: `title`, `content`, and `answers`. The title and content have full text formatting. See the wiki page for TextFormatter for more information. The `answers` input field is labeled as "Replying To:" and says which main post the reply is for. Users must be Authenticated before posting. See the wiki page for `/auth` for more information. Requests from this form go to `/polr/post`.
##### ID
Each and every post has a unique ID. This ID will be the the previously highest ID + 1. To view a certain post, you can enter its ID after `/polr/`. To view post 99, you would enter `/polr/99`.
##### View link
The "View" link will make the post on which it appears the main post, and will use the defaults for sort and flow. If JavaScript is enabled, this is achieved by making a request to `polr/updt` that includes the post's ID and replacing the HTML being shown. Otherwise, the link acts as normal.
##### Report link
The "Report" link will only show up on the main post. It allows a user to jump to a reporting form at `/flag` with the main post's ID autofilled.
