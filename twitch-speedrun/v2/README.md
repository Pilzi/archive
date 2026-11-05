# Twitch Speedrun v2

A browser extension I built in 2023, the second version of
[v1](../v1/). You add Twitch streamers in the
popup, and every few seconds it checks whether one of them went live. If so, it
opens the stream in a new tab right away.

Unlike v1 it doesn't need the Twitch API. It reads the public Twitch page
instead, so it will break whenever Twitch changes its page.

`chrome/` and `firefox/` contain the same code, only `manifest.json` differs.

## Running it

No setup or build needed.

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
