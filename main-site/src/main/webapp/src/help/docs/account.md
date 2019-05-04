### xarql.com native accounts
xarql.com has an account system with usernames and passwords stored on its server. The passwords are secured with Bcrypt hashing that includes automatic salt generation.
##### Logging out
Logging out of your account will cause the server to immediately destroy its temporary record that connects your session cookie with your username. These records are destroyed automatically after 60 minutes without posting. You will have to either use a recpatcha or log in again to post.
##### Changing Passwords
You should avoid changing passwords because it increases the likelyhood that you'll forget your password. We can't see your password and can't hand control over to you, the best we could do is to delete the account from the database _but we won't_. In the future, you'll be able to attach an email to your account to reduce the risk involved in remembering a password.
##### Deleting Your Account
You should never delete your account. /polr records will retain your username on them _for now_. Deleting your account means that anyone can claim your username and impersonate you, because there will be no record of your account existing. Please simply abandon your account when you stop using xarql, as there are plenty of usernames out there.
##### Attaching An Email
You can attach an email to your account for future account recovery.
