github-comment-counter
======================

A [dropwizard](https://github.com/codahale/dropwizard)-based server displaying number of Pull Request Comments performed by an organization's GitHub users.
Can be used to identify and celebrate top reviewers:
![alt tag](https://raw.github.com/tzachz/github-comment-counter/master/leaderboard-sample-blurred.png)

Counts all recent in-code comments on organization's opened and closed Pull Requests, excluding comments on one's own Pull Requests.
Queries [GitHub API](http://developer.github.com/v3/) every X minutes and displays the result.


Build
=====
Some of the tests require a github user to activate the API, so you'll need to supply credentials:
```
./gradlew clean build -Dusername=<a github user> -Dpassword=<her password>
```

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
    username: my-user   # change these to any valid github credentials
    password: my-pass

organization: my-org    # organization to show stats for
refreshRateMinutes: 10  # interval between API activations
```

You're now ready to run the server:
```
java -jar <path to fat jar> server <path to yml file>
```

You should be able to see the results at http://localhost:8080/
