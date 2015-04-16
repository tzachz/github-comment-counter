github-comment-counter
======================

A [dropwizard](https://github.com/codahale/dropwizard)-based server displaying number of Pull Request Comments performed by an organization's GitHub users.
Can be used to identify and celebrate top reviewers:
![alt tag](https://raw.githubusercontent.com/tzachz/github-comment-counter/master/leaderboard-sample-blurred.png)

Counts all recent in-code comments on organization's opened and closed Pull Requests, excluding comments on one's own Pull Requests.
Queries [GitHub API](http://developer.github.com/v3/) every X minutes and displays the result.


Build
=====
Some of the tests require a github user to activate the API, so you'll need to supply credentials.
Here and elsewhere, you can either pass your GitHub username and password as credentials
or pass a single [OAuth token](https://github.com/settings/applications) associated with your account.

```
./gradlew clean build -Dusername=<a github user> -Dpassword=<her password> -Dtoken=<an OAuth token>
```

(If your user account has [two-factor authentication](https://help.github.com/articles/about-two-factor-authentication/) enabled, only token-based access will work.)

Alternatively, you can build without tests:
```
./gradlew clean build -x test
```

Both options will create a fat jar ready to run under ``` leaderboard-server/build/libs/ ```


Usage
=====
Create a yml file with the following configuration:
```yml
gitHubCredentials:
    # change these to any valid github credentials; requires
    # either a username/password combination or a token
    username: my-user
    password: my-pass
    token: my-oauth-token

organization: my-org    # organization to show stats for
refreshRateMinutes: 10  # interval between API activations
```
As above, specify either a token or a username and password.

You're now ready to run the server:
```
java -jar <path to fat jar> server <path to yml file>
```

You should be able to see the results at http://localhost:8080/
