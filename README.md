github-comment-counter
======================

A [dropwizard](https://github.com/codahale/dropwizard)-based server displaying number of Pull Request Comments performed by an organization's GitHub users.

Can be used to identify and celebrate top reviewers.

Queries [GitHub API](http://developer.github.com/v3/) every X minutes and displays the result.

Build
=====
Some of the tests require a github user to activate the API, so you'll need to supply credentials:
```
./gradlew clean build -Dusername=<a github user> -Dpassword=<her password>
```

Alternatively, you can buid without tests:
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
daysBack: 7             # comments from the last week will be counted
refreshRateMinutes: 10  # interval between API activations
```

You're now ready to run the server:
```
java -jar <path to fat jar> server <path to yml file>
```

You should be able to see the results at http://localhost:8080/leaderboard

Result should look something like:
    <h2>GitHub Reviewers Leader Board:</h2>
    <ul>
        <li><b>36</b> comments by <a href="https://github.com/user1" target="_blank">user1</a></li>
        <li><b>20</b> comments by <a href="https://github.com/user2" target="_blank">user2</a></li>
        <li><b>12</b> comments by <a href="https://github.com/user3" target="_blank">user3</a></li>
    </ul>
    
Yeah, I know, some styling can do it justice, and of course there's much more interesting data to show (latest comments, number of repos etc.). Stay tuned or contribute!
    
