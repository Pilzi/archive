import praw
import discord
import datetime
import asyncio
import json
import os
import requests
import aiohttp, io


DC_CLIENT_SECRET = os.environ["DISCORD_TOKEN"]
last_datetime = datetime.datetime.utcnow().timestamp()


reddit = praw.Reddit(
    client_id=os.environ["REDDIT_CLIENT_ID"],
    client_secret=os.environ["REDDIT_CLIENT_SECRET"],
    user_agent="cat-bot",
    redirect_uri="http://localhost:8080",
)


async def find_memes_coroutine():
    while True:
        try:
            await asyncio.sleep(800)
            await client.change_presence(activity=discord.Game(
                "every " + str(int(604800 / (len(get_file.get_memes_file()) * 0.9))) + " seconds"))
            for meme in get_cat_memes_reddit(100, ["catmemes",
                                                  "cat",
                                                  "cats",
                                                  "CatsOnKeyboards",
                                                  "CatsAreAssholes",
                                                  "Catswithjobs",
                                                  "CatsBeingCats",
                                                  "SupermodelCats",
                                                  "CatsStandingUp",
                                                  "WhatsWrongWithYourCat"]):
                get_file.append_memes_file(meme)
        except:
            print("error")


async def send_memes_coroutine():
    while True:
        try:
            await asyncio.sleep(604800 / (len(get_file.get_memes_file()) * 0.9))

            meme_list = get_file.get_memes_file()

            try:
                while requests.get(meme_list[0]).status_code == 404:
                    meme_list.remove(meme_list[0])
            except:
                continue

            if len(meme_list) > 0 and len(get_file.get_user_id_list()) > 0:
                for user_id in get_file.get_user_id_list():
                    try:
                        user = await client.fetch_user(user_id)
                    except:
                        continue
                    async with aiohttp.ClientSession() as session:  # creates session
                        async with session.get(meme_list[0]) as resp:  # gets image from url
                            img = await resp.read()  # reads image from response
                            with io.BytesIO(img) as file:  # converts to file-like object
                                await user.send(file=discord.File(file, "cat.png"))
                meme_list.remove(meme_list[0])
                get_file.save_memes_file(meme_list)
        except:
            print("error")


class button_navigation(discord.ui.View):

    def __init__(self):
        super().__init__()
        color = discord.ButtonStyle.green
        for i in ["start", "stop"]:  # Add five buttons
            self.add_item(self.button_disable(str(i), color))
            color = discord.ButtonStyle.red

    class button_disable(discord.ui.Button):  # Button class
        def __init__(self, label, color):
            super().__init__(label=label, style=color)  # set label and super init class

        async def callback(self, interaction: discord.Interaction):

            if self.label == "start":
                await interaction.response.send_message("Congratulations, I'll send you my Memes",
                                                              ephemeral=True)
                get_file.append_user_id_list(interaction.user.id)
            else:
                await interaction.response.send_message("Nowwwwwww I'll stop sending you cat pictures meow:(",
                                                              ephemeral=True)
                get_file.delete_user_id_list(interaction.user.id)
            await interaction.message.edit(view=self.view)  # update message


async def create_edit_notification_button_loop():
    discord_channel = await client.fetch_channel(int(os.environ["DISCORD_CHANNEL_ID"]))
    messages = [message async for message in discord_channel.history(limit=10)]
    count = 0
    for message in messages:
        count = count + 1
        if count < len(messages):
            await message.delete()
        else:
            message_to_upgrade = message


    embed = discord.Embed(title="Cat Bot", color=0xff00c8)
    embed.add_field(name="",
                    value=f"press start to get a cat picture every {str(int(604800 / (len(get_file.get_memes_file()) * 0.9)))} seconds",
                    inline=False)
    if len(messages) <= 0:
        await discord_channel.send(view=button_navigation(), embed=embed)
    else:
        try:
            await message_to_upgrade.edit(view=button_navigation(), embed=embed)
        except:
            await message_to_upgrade.delete()
            await discord_channel.send(view=button_navigation(), embed=embed)

    await asyncio.sleep(1000)
    client.loop.create_task(create_edit_notification_button_loop())


class Get_File():

    def get_user_id_list(self):
        with open('created_at.json', "r") as f:
            json_data = json.load(f)
            try:
                return json_data["user_list"]
            except KeyError:
                return " "

    def append_user_id_list(self, id):
        with open('created_at.json', "r+") as f:
            json_data = json.load(f)
            if id not in json_data["user_list"]:
                json_data["user_list"].append(id)
            f.seek(0)
            json.dump(json_data, f, indent=4)
            f.truncate()

    def delete_user_id_list(self, id):
        with open('created_at.json', "r+") as f:
            json_data = json.load(f)
            if id in json_data["user_list"]:
                json_data["user_list"].remove(id)
            f.seek(0)
            json.dump(json_data, f, indent=4)
            f.truncate()

    def save_created_at(self, time, subreddit_name):
        with open('created_at.json', "r+") as f:
            json_data = json.load(f)
            json_data["subreddit_datetime"][subreddit_name] = time
            f.seek(0)
            json.dump(json_data, f, indent=4)
            f.truncate()

    def get_created_at(self, subreddit_name):
        with open('created_at.json', "r") as f:
            json_data = json.load(f)
            try:
                return json_data["subreddit_datetime"][subreddit_name]
            except KeyError:
                return 0

    def append_memes_file(self, meme):
        with open('created_at.json', "r+") as f:
            json_data = json.load(f)
            json_data["post_list"].append(meme)
            f.seek(0)
            json.dump(json_data, f, indent=4)
            f.truncate()

    def get_memes_file(self):
        with open('created_at.json', "r") as f:
            json_data = json.load(f)
            print(f"{len(json_data['post_list'])} cat pics left")
            return json_data["post_list"]

    def save_memes_file(self, meme_list):
        with open('created_at.json', "r+") as f:
            json_data = json.load(f)
            json_data["post_list"] = (meme_list)
            f.seek(0)
            json.dump(json_data, f, indent=4)
            f.truncate()


def get_cat_memes_reddit(limit, subreddit_names):
    new_pics = []

    for subreddit_name in subreddit_names:

        created_at = get_file.get_created_at(subreddit_name)
        last_post = 0
        subreddit = reddit.subreddit(subreddit_name)

        for new_subreddit in subreddit.new(limit=limit):

            if (new_subreddit.created_utc) > last_post:
                last_post = new_subreddit.created_utc

            if new_subreddit.created_utc > float(created_at):

                if (
                        "png" in new_subreddit.url or "jpg" in new_subreddit.url or "jpeg" in new_subreddit.url) and "i.redd.it" in new_subreddit.url:
                    new_pics.append(new_subreddit.url)

        get_file.save_created_at(last_post, subreddit_name)

    return new_pics


class MyClient(discord.Client):
    ready = False

    async def on_ready(self):
        print('Logged on as', self.user)

        if not self.ready:
            client.loop.create_task(find_memes_coroutine())
            client.loop.create_task(send_memes_coroutine())
            client.loop.create_task(create_edit_notification_button_loop())
            self.ready = True

    async def on_message(self, message):
        # don't respond to ourselves
        if message.author == self.user:
            return

        if message.content.startswith("!len"):
            await message.author.send(f"cat pics left: {len(get_file.get_memes_file())}")

        if message.content.startswith("!del"):

            try:
                channel = await client.fetch_channel(message.channel.id)
                msg = await channel.fetch_message(message.content.split(" ")[1])
                await msg.delete()
            except:
                return


get_file = Get_File()

intents = discord.Intents.default()
intents.message_content = True
client = MyClient(intents=intents)
client.run(DC_CLIENT_SECRET)
