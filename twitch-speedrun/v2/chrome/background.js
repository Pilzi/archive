function checkStreamStatus() {
  chrome.storage.local.get('streamer', function (streamer) {
    if (!streamer.streamer) {
      return;
    }

    streamer.streamer.map((streamerContainer, index) => {

      streamer.streamer[index].key = index;

      chrome.storage.local.set(streamer);
    });

    try {
      streamer.streamer.map((streamerContainer) => {
        if (streamerContainer.notification) {
          fetch(`https://www.twitch.tv/${streamerContainer.name}`).then(res => {
            return res.text()
          }).then(res => {
            // extract start date
            const startDate = res.split('"startDate":"')[1].split('"')[0]

            if (res.includes('"isLiveBroadcast":true') && streamerContainer.startedAt !== startDate){
              chrome.tabs.create({
                url: `https://www.twitch.tv/${streamerContainer.name}`
              });
              streamer.streamer[streamerContainer.key].startedAt = startDate;
              chrome.storage.local.set(streamer);

            }
          })
        }
      });
    } catch (TypeError) {
      console.log("empty list");
    }

  });

}

// send an alarm every 5 seconds
chrome.alarms.create("checkStreamAlarm", { periodInMinutes: 0.0833 });

// Listen for the alarm and execute the function
chrome.alarms.onAlarm.addListener((alarm) => {
  if (alarm.name === "checkStreamAlarm") {
    checkStreamStatus();
  }
});

chrome.runtime.onMessage.addListener(function (message, sender, senderResponse) {
  if (message.type === "fetch") {
    fetch(message.url).then(res => {
      return res.text()
    }).then(res => {
      senderResponse(res);
    })
  }
  return true
});



