# dc_cat_bot

A Discord bot I built in Python in early 2024. It collects new cat pictures from
a few subreddits and sends them as DMs to everyone who pressed "start" in the
bot's channel.

Commands: `!len` shows how many pictures are left, `!del <message id>` deletes a
message.

## Running it

Needs Python 3, a Discord bot with the Message Content intent turned on, and a
Reddit app.

```
pip install -r requirements.txt
```

Set these environment variables, then run `python main.py`:

- `DISCORD_TOKEN`
- `DISCORD_CHANNEL_ID` (the channel for the start/stop message)
- `REDDIT_CLIENT_ID`
- `REDDIT_CLIENT_SECRET`
