# Twitch Speedrun v1

The first version of my Twitch speedrun extension (2021). You add Twitch
streamers in the popup, and every few seconds it asks the Twitch API whether one
of them went live. If so, it opens the stream in a new tab right away. Clicking a
streamer opens a small stats page.

The later version without the Twitch API is
[v2](../v2/).

`chrome/` and `firefox/` contain the same code, only `manifest.json` differs.

## Running it

The extension needs your own Twitch app:

1. Create an application in the Twitch Developer Console
   (dev.twitch.tv/console). Any redirect URL works, e.g. `http://localhost`.
2. Put its client ID and client secret into `background.js` and
   `stats/stats.js` in place of `YOUR_CLIENT_ID` and `YOUR_CLIENT_SECRET`.
   Don't commit your real secret.

### Chrome, Edge, Brave, Opera

1. Open `chrome://extensions` (`edge://extensions` in Edge).
2. Turn on developer mode.
3. Click "Load unpacked" and select the `chrome` folder.

Errors from the background script show up under "service worker" on that page.

### Firefox

1. Open `about:debugging#/runtime/this-firefox`.
2. Click "Load Temporary Add-on" and select `firefox/manifest.json`.
3. In `about:addons`, open the extension, go to "Permissions" and allow access
   to twitch.tv if it isn't allowed yet.

Temporary add-ons are removed when Firefox closes.
