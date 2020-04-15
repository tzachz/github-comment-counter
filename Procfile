---
default_process_types:
  web: export LEADERBOARD_SERVER_OPTS="-Ddw.http.port=$PORT -Ddw.gitHubCredentials.token=$GH_TOKEN -Ddw.gitHubCredentials.username=$GH_USER -Ddw.gitHubCredentials.password=$GH_PASS -Ddw.organization=$ORG_NAME" && ./leaderboard-server/build/install/leaderboard-server/bin/leaderboard-server server leaderboard-server.yml
